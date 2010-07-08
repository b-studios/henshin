/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.change.ModelChange;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

/**
 * An implementation of an executable rule application. It must be initialized
 * with an instance of <code>org.eclipse.emf.henshin.model.Rule</code>.
 */
public class RuleApplication {
	private InterpreterEngine interpreterEngine;

	private Rule rule;

	private Match match;
	private Match comatch;
	
	private ModelChange modelChange;

	// flags for execution status of the rule
	private boolean isExecuted = false;
	private boolean isUndone = false;

	/**
	 * Creates a new RuleApplication.
	 * 
	 * @param engine
	 *            The InterpreterEngine used for matchfinding
	 * @param rule
	 *            A Henshin rule
	 */
	public RuleApplication(InterpreterEngine engine, Rule rule) {
		this.rule = rule;
		this.interpreterEngine = engine;

		this.match = new Match(rule, new HashMap<Parameter, Object>(),
				new HashMap<Node, EObject>());
	}

	/**
	 * Returns a single match for this rule.
	 * 
	 * @return One match for this rule.
	 */
	public Match findMatch() {
		return interpreterEngine.findMatch(this);
	}

	/**
	 * Returns all possible matches for this rule.
	 * 
	 * @return A list of all matches.
	 */
	public List<Match> findAllMatches() {
		return interpreterEngine.findAllMatches(this);
	}

	/**
	 * Executes this rule. First a match must be found and checked if the rule
	 * can applied to it.
	 */
	public boolean apply() {
		if (!isExecuted) {
			boolean result = interpreterEngine.applyRule(this);

			isExecuted = result;
			return result;
		}

		return false;
	}

	/**
	 * Restores instance before rule application.
	 */
	public void undo() {
		if (isExecuted && !isUndone) {
			interpreterEngine.undoChanges(this);

			isUndone = true;
		}
	}

	/**
	 * Reapplies rule after its application was undone.
	 */
	public void redo() {
		if (isExecuted && isUndone) {
			interpreterEngine.redoChanges(this);
		}
	}

	/**
	 * Gives the rule descriuption read from the model rule.
	 * 
	 * @return Description as String
	 */
	public String getDescription() {
		return rule.getDescription();
	}

	/**
	 * Adds a value for an input parameter or input object to the current rule.
	 * 
	 * @param name
	 *            Name of the input
	 * @param value
	 *            Value of the input
	 */
	public void addAssignment(String name, Object value) {
		Parameter parameter = rule.getParameterByName(name);
		match.getParameterValues().put(parameter, value);
	}

	public void setAssignments(Map<Parameter, Object> assignments) {
		match.getParameterValues().clear();
		match.getParameterValues().putAll(assignments);
	}

	/**
	 * Sets a partial or complete match for the current rule. This match will be
	 * part of all completions.
	 * 
	 * @param match
	 */
	public void setMatch(Match match) {
		this.match = match;
	}

	/**
	 * Adds a single object mapping the rule.
	 * 
	 * @param node
	 *            An LHS node.
	 * @param value
	 *            An EObject in the instance the rule should be applied to
	 */
	public void addMatch(Node node, EObject value) {
		match.getNodeMapping().put(node, value);
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @return the comatch
	 */
	public Match getComatch() {
		return comatch;
	}

	public void setComatch(Match comatch) {
		this.comatch = comatch;
	}

	public ModelChange getModelChange() {
		return modelChange;
	}

	public void setModelChange(ModelChange modelChange) {
		this.modelChange = modelChange;
	}

	@Override
	public String toString() {
		return rule.getName();
	}

	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}
}