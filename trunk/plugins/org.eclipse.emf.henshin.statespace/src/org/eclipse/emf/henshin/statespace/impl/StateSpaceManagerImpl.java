/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;

/**
 * Default state space manager implementation.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManagerImpl extends AbstractStateSpaceManager {
	
	// State model cache:
	private final StateModelCache cache = new StateModelCache();
	
	// Transformation engines:
	private final Stack<EmfEngine> engines = new Stack<EmfEngine>();
	
	// A lock used when exploring states:
	private final Object explorerLock = new Object();
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public StateSpaceManagerImpl(StateSpace stateSpace) {
		super(stateSpace);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#isOpen(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	protected boolean isOpen(State state) throws StateSpaceException {
		
		// Do a dry exploration of the state:
		List<Transition> transitions = doExplore(state);
		Set<Transition> matched = new HashSet<Transition>();
		
		for (Transition transition : transitions) {
			
			// Find the corresponding transition in the state space:
			Resource transformed = transition.getTarget().getModel();
			Transition existing = findTransition(state, transition.getRule(), transformed);
			if (existing==null) return true;
			matched.add(existing);
			
		}
		
		// Check if there are extra transitions (illegal):
		if (!matched.containsAll(state.getOutgoing())) {
			throw new StateSpaceException("Illegal transition in state " + state.getIndex());
		}
		
		// State is not open:
		return false;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getModel(org.eclipse.emf.henshin.statespace.State)
	 */
	public Resource getModel(State state) throws StateSpaceException {
		
		// Model already set?
		if (state.getModel()!=null) {
			return state.getModel();
		}
		
		// Cached?
		Resource cached = cache.get(state);
		if (cached!=null) {
			return cached;
		}
		
		// Find a predecessor state that has a model:
		StateSpaceSearch search = new StateSpaceSearch() {
			@Override
			protected boolean shouldStop(State current, Trace trace) {
				return current.getModel()!=null || cache.get(current)!=null;
			}
		};
		boolean found = search.depthFirst(state, true);
		if (!found) {
			throw new StateSpaceException("Unable to derive state model for state " + state.getIndex());
		}
		
		// Derive the current model:
		Resource start = search.getCurrentState().getModel();
		if (start==null) start = cache.get(search.getCurrentState());
		Resource model = deriveModel(start, search.getTrace());
		
		// Always add it to the cache (is maintained automatically):
		cache.put(state, model);
		
		// Done.
		return model;
		
	}
	
	/*
	 * Store a model in a state.
	 */
	private void storeModel(State state, Resource model) {
		
		// Do not override initial state models!!!
		if (state.isInitial()) return;
		
		// Number of states: rounded up for more stability:
		int states = getStateSpace().getStates().size();
		states = states - (states % 1000) + 1000;			// always greater than 1000
		
		// Decide whether the current model should be kept in memory.
		// The natural logarithm seems to be a good choice here.
		int stored = (int) Math.log(states) - 3;	// always greater or equal 3
		int index = state.getIndex() + 1;			// always greater than 1
		
		//System.out.println(stored);
		
		if (index % stored != 0) {
			model = null;
		}
		state.setModel(model);

	}
	
	/*
	 * Derive a model. The path is assumed to be non-empty.
	 */
	private Resource deriveModel(Resource start, Trace path) throws StateSpaceException {

		// We need a transformation engine first:
		EmfEngine engine = acquireEngine();

		// We copy the model:
		Resource model = copyModel(start, null);
		
		// Derive the model for the current state:
		for (Transition transition : path) {
			
			Rule rule = transition.getRule();
			if (rule==null || !getStateSpace().getRules().contains(rule)) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());
			}
			
			RuleApplication application = createRuleApplication(model, rule, engine);
			List<Match> matches = application.findAllMatches();
			if (matches.size()<=transition.getMatch()) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());				
			}
			
			// Apply the rule with the found match:
			Match match = matches.get(transition.getMatch());
			application.setMatch(match);
			application.apply();
			
			// Store model:
			storeModel(transition.getTarget(),copyModel(model,null));
			
		}
		
		// We can release the engine already:
		releaseEngine(engine);
		
		// Decide whether the model in the start state should be kept:
		storeModel(path.getSource(),start);
		
		return model;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#explore(org.eclipse.emf.henshin.statespace.State)
	 */
	public List<Transition> exploreState(State state, boolean generateLocation) throws StateSpaceException {
		
		// Explore the state without changing the state space:
		List<Transition> transitions = doExplore(state);
		List<Transition> result = new ArrayList<Transition>(transitions.size());
		
		int newStates = 0;
		for (Transition transition : transitions) {
			
			// Get the hash and model of the new target state:
			int hashCode = transition.getTarget().getHashCode();
			Resource transformed = transition.getTarget().getModel();
			
			// We need to test whether a state exists already and if not
			// create a new one. And this atomically.
			synchronized (explorerLock) {
				
				// Find existing state / transition:
				Transition existingTransition = findTransition(state, transition.getRule(), transformed);
				State targetState = getState(transformed, hashCode);

				if (existingTransition!=null) {

					// Check if the transition points to the correct state:
					Resource existingModel = getModel(existingTransition.getTarget());
					if (!equals(existingModel,transformed)) {
						markTainted();
						throw new StateSpaceException("Illegal transition in state " + state);
					}

				} else {

					// Create a new transition and state if required:
					if (targetState==null) {
						int[] location = generateLocation ? shiftedLocation(state, newStates++) : null;
						targetState = createOpenState(transformed, hashCode, location);
						storeModel(targetState, transformed);
					}
					Transition newTransition = createTransition(state, targetState, transition.getRule(), transition.getMatch());
					result.add(newTransition);
				}
			}
		}
		
		// Mark the state as closed:
		setOpen(state, false);
		
		// Done: return the new transitions.
		return result;
		
	}
	
	/**
	 * Explore a state without actually changing the state space.
	 * This method does not check if the state is explored already
	 * or whether any of the transitions or states exists already.
	 * @param state State to be explored.
	 * @return List of outgoing transition.
	 * @throws StateSpaceException On explore errors.
	 */
	private List<Transition> doExplore(State state) throws StateSpaceException {
		
		// We need a transformation engine first:
		EmfEngine engine = acquireEngine();
		
		// Get the state model:
		Resource model = getModel(state);
		
		// List of explored transitions.
		List<Transition> transitions = new ArrayList<Transition>();
		
		// Apply all rules:
		for (Rule rule : getStateSpace().getRules()) {
			
			RuleApplication application = createRuleApplication(model, rule, engine);
			List<Match> matches = application.findAllMatches();
			
			for (int i=0; i<matches.size(); i++) {
				
				// Transform the model:
				Match match = matches.get(i);
				Resource transformed = copyModel(model, match);
				application = createRuleApplication(transformed, rule, engine);
				application.setMatch(match);
				application.apply();
				
				// Create a new transition and state:								
				State newState = StateSpaceFactory.eINSTANCE.createState();
				newState.setHashCode(hashCode(transformed));
				newState.setModel(transformed);
				
				Transition newTransition = StateSpaceFactory.eINSTANCE.createTransition();
				newTransition.setRule(rule);
				newTransition.setMatch(i);
				newTransition.setTarget(newState);
				
				transitions.add(newTransition);
				
			}
		}
		
		// Now we can release the engine again:
		releaseEngine(engine);
		
		// And we are done:
		return transitions;

	}
	
	/*
	 * Find an outgoing transition.
	 */
	private Transition findTransition(State state, Rule rule, Resource targetModel) throws StateSpaceException {
		for (Transition transition : state.getOutgoing()) {
			if (rule==transition.getRule() && equals(getModel(transition.getTarget()),targetModel)) {
				return transition;
			}
		}
		return null;
	}
	
	/*
	 * Create a new RuleApplication
	 */
	private static RuleApplication createRuleApplication(Resource model, Rule rule, EmfEngine engine) {
		EmfGraph graph = new EmfGraph();
		for (EObject root : model.getContents()) {
			graph.addRoot(root);
		}
		engine.setEmfGraph(graph);
		return new RuleApplication(engine,rule);
	}
	
	/*
	 * Acquire a transformation engine.
	 */
	private EmfEngine acquireEngine() {
		synchronized (engines) {
			if (!engines.isEmpty()) {
				return engines.pop();
			} else {
				return new EmfEngine();
			}
		}
	}

	/*
	 * Release a transformation engine again.
	 */
	private void releaseEngine(EmfEngine engine) {
		synchronized (engines) {
			engines.push(engine);
		}
	}

	/*
	 * Copy a state model.
	 */
	private static Resource copyModel(Resource model, Match match) {
		Resource copy = new ResourceImpl();
		Copier copier = new Copier();
		copy.getContents().addAll(copier.copyAll(model.getContents()));
		copier.copyReferences();
		if (match!=null) {
			List<Node> nodes = new ArrayList<Node>(match.getNodeMapping().keySet());
			for (Node node : nodes) {
				EObject newImage = copier.get(match.getNodeMapping().get(node));
				match.getNodeMapping().put(node, newImage);
			}
		}
		return copy;
	}
	
	/*
	 * Create a shifted location.
	 */
	private static int[] shiftedLocation(State base, int index) {
		int[] location = base.getLocation();
		double angle = (Math.PI * index * 0.17d);
		location[0] += 60 * Math.cos(angle);
		location[1] += 60 * Math.sin(angle);
		return location;
	}
	
}
