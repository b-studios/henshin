/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.actions.impl;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import static org.eclipse.emf.henshin.model.Action.Type.*;

public class EdgeActionHelper extends GenericActionHelper<Edge,Rule> {
	
	/**
	 * Static instance.
	 */
	public static final EdgeActionHelper INSTANCE = new EdgeActionHelper();
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.ActionHelper#getActionElements(java.lang.Object, org.eclipse.emf.henshin.diagram.edit.actions.Action)
	 */
	public List<Edge> getActionElements(Rule rule, Action action) {
		List<Edge> candidates =  ActionElementFinder.getRuleElementCandidates(rule, action, HenshinPackage.eINSTANCE.getGraph_Edges());
		return filterElementsByAction(candidates, action);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.AbstractActionHelper#getMapEditor(org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	protected MapEditor<Edge> getMapEditor(Graph target) {
		return new EdgeMapEditor(target);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.AbstractActionHelper#getMapEditor(org.eclipse.emf.henshin.model.Graph, org.eclipse.emf.henshin.model.Graph, java.util.List)
	 */
	@Override
	protected MapEditor<Edge> getMapEditor(Graph source, Graph target, MappingList mappings) {
		return new EdgeMapEditor(source, target, mappings);
	}

	/**
	 * For an arbitrary edge in a rule graph, find the corresponding action edge.
	 * @param edge Some edge.
	 * @return The corresponding action edge.
	 */
	public Edge getActionEdge(Edge edge) {
		return ActionElementFinder.getActionElement(edge, INSTANCE);
	}

	/**
	 * Create an edge between two action nodes.
	 */
	public Edge createEdge(Node source, Node target, EReference type) {

		if (!canCreateEdge(source, target, type)) {
			return null;
		}
		
		Rule rule = source.getGraph().getRule();
		Edge edge = null;

		// Get the node actions:
		Action srcAction = NodeActionHelper.INSTANCE.getAction(source);
		Action trgAction = NodeActionHelper.INSTANCE.getAction(target);

		// Check if the actions are the same:
		if (srcAction.equals(trgAction)) {

			// Create the new edge (we know the nodes are in the same graph):
			edge = HenshinFactory.eINSTANCE.createEdge(source, target, type);

			// For PRESERVE actions we need to create an image in the RHS as well:
			if (srcAction.getType() == PRESERVE) {
				Node srcImage = rule.getMappings().getImage(source, rule.getRhs());
				Node trgImage = rule.getMappings().getImage(target, rule.getRhs());
				HenshinFactory.eINSTANCE.createEdge(srcImage, trgImage, type);
			}

		} else {

			/* 
			 * We know one of the action is of type PRESERVE, the other one is not.
			 * We look for the image of the PRESERVE node and use it to create the edge.
			 * If the image does not exist yet (for a NAC for instance), we copy the node.
			 */
			boolean copyToRhs;
			Graph multiRhs;
			
			if (isSimplePreserve(srcAction)) {
				
				if (trgAction.isMulti()) {
					Rule multiRule = target.getGraph().getRule();
					Node realSource = multiRule.getMultiMappings().getImage(source, multiRule.getLhs());
					if (realSource!=null) {
						source = realSource;
					}
				}
				
				if (trgAction.getType() == CREATE
						|| trgAction.getType() == FORBID
						|| trgAction.getType() == REQUIRE) {
					source = new NodeMapEditor(target.getGraph()).copy(source);
				}
				
				// Do we need to copy the edge to the Rhs of the multi-rule?
				copyToRhs = (trgAction.isMulti() && trgAction.getType()==PRESERVE);
				multiRhs = target.getGraph().getRule().getRhs();
				
			} else {
				
				if (srcAction.isMulti()) {
					Rule multiRule = source.getGraph().getRule();
					Node realTarget = multiRule.getMultiMappings().getImage(target, multiRule.getLhs());
					if (realTarget!=null) {
						target = realTarget;
					}
				}

				if (srcAction.getType() == CREATE
						|| srcAction.getType() == FORBID) {
					target = new NodeMapEditor(source.getGraph()).copy(target);
				}
				
				// Do we need to copy the edge to the Rhs of the multi-rule?
				copyToRhs = (srcAction.isMulti() && srcAction.getType()==PRESERVE);
				multiRhs = source.getGraph().getRule().getRhs();

			}

			// Now we can safely create the edge:
			edge = HenshinFactory.eINSTANCE.createEdge(source, target, type);
			if (copyToRhs) {
				new EdgeMapEditor(multiRhs).copy(edge);
			}
			
		}
		
		return edge;

	}

	/**
	 * Check if an edge can be created.
	 */
	public boolean canCreateEdge(Node source, Node target, EReference edgeType) {

		// Get the source and target type.
		EClass targetType = target.getType();
		EClass sourceType = source.getType();

		// Everything must be set.
		if (source == null || target == null || sourceType == null
				|| targetType == null || edgeType == null) {
			return false;
		}

		// Reference must be owned by source.
		if (!sourceType.getEAllReferences().contains(edgeType)) {
			return false;
		}

		// Target type must be ok. Extra check for EObjects!!!
		if (!edgeType.getEReferenceType().isSuperTypeOf(targetType) &&
			!targetType.isSuperTypeOf(edgeType.getEReferenceType())
				&& edgeType.getEReferenceType() != EcorePackage.eINSTANCE
						.getEObject()) {
			return false;
		}

		// Check for source/target consistency.
		Graph sourceGraph = source.getGraph();
		Graph targetGraph = target.getGraph();
		if (sourceGraph==null || targetGraph==null) {
			return false;
		}

		// Make sure the rules are found and compatible:
		Rule sourceRule = sourceGraph.getRule();
		Rule targetRule = targetGraph.getRule();
		if (sourceRule == null || targetRule == null) {
			return false;
		}
		if (sourceRule!=targetRule && 
			sourceRule!=targetRule.getKernelRule() &&
			sourceRule.getKernelRule()!=targetRule) {
			return false;
		}
		
		// Get the node actions:
		Action action1 = NodeActionHelper.INSTANCE.getAction(source);
		Action action2 = NodeActionHelper.INSTANCE.getAction(target);

		// Sanity check:
		if (action1 == null || action2 == null) {
			return false;
		}

		// Different actions are only allowed if one is a preserve action:
		if (!action1.equals(action2)
				&& !isSimplePreserve(action1)
				&& !isSimplePreserve(action2)) {
			return false;
		}

		// Ok.
		return true;

	}
	
	private static boolean isSimplePreserve(Action action) {
		return action.getType()==PRESERVE && 
				!action.isMulti() && 
				action.getArguments().length==0;
	}

}