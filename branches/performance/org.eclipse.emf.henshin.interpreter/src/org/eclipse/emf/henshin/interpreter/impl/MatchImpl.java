/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin
 *     Hasso Plattner Institute
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Default {@link Match} implementation. For result matches, use {@link ResultMatchImpl}.
 */
public class MatchImpl extends AssignmentImpl implements Match {
	
	// Nodes to be matched:
	protected final List<Node> nodes;
	
	// Flag indicating whether this is a result match:
	protected final boolean isResultMatch;
	
	/**
	 * Default constructor.
	 * @param rule The rule that this match is used for.
	 */
	public MatchImpl(Rule rule) {
		this(rule, false);
	}

	/**
	 * Constructor which copies an assignment or a match.
	 * @param assignment Assignment or match to be copied.
	 */
	public MatchImpl(Assignment assignment) {
		this(assignment, false);
	}

	/*
	 * Internal constructor.
	 * @param rule The rule that this match is used for.
	 */
	protected MatchImpl(Rule rule, boolean isResultMatch) {
		super(rule);
		this.isResultMatch = isResultMatch;
		this.nodes = isResultMatch ? rule.getRhs().getNodes() : rule.getLhs().getNodes(); 
	}

	/*
	 * Internal constructor.
	 * @param assignment The assignment or match to be copied.
	 */
	protected MatchImpl(Assignment assignment, boolean isResultMatch) {
		super(assignment);
		if (!(unit instanceof Rule)) {
			throw new IllegalArgumentException("Argument assignment does not refer to a rule");
		}
		this.isResultMatch = isResultMatch;
		this.nodes = isResultMatch ? ((Rule) unit).getRhs().getNodes() : ((Rule) unit).getLhs().getNodes(); 
		if (assignment instanceof Match) {
			Match match = (Match) assignment;
			for (Node node : nodes) {
				setNodeTarget(node, match.getNodeTarget(node));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#getRule()
	 */
	@Override
	public Rule getRule() {
		return (Rule) unit;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#getNodeTarget(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public EObject getNodeTarget(Node node) {
		return (EObject) values.get(node);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#setNodeTarget(org.eclipse.emf.henshin.model.Node, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void setNodeTarget(Node node, EObject target) {
		setValue(node, target);
		// Check whether there is a parameter with the same name:
		if (unit!=null && node.getName()!=null) {
			Parameter param = unit.getParameterByName(node.getName());
			if (param!=null) {
				setValue(param, target);
			}
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.impl.AssignmentImpl#setParameterValue(org.eclipse.emf.henshin.model.Parameter, java.lang.Object)
	 */
	@Override
	public void setParameterValue(Parameter param, Object value) {
		setValue(param, value);
		// Check whether there are nodes with the same name as the parameter:
		String name = param.getName();
		if (name!=null && nodes!=null && (value==null || value instanceof EObject)) {
			for (Node node : nodes) {
				if (name.equals(node.getName())) {
					setValue(node, value);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#getAllNodeTargets()
	 */
	@Override
	public List<EObject> getNodeTargets() {
		List<EObject> targets = new ArrayList<EObject>();
		if (nodes!=null) {
			for (Node node : nodes) {
				EObject tar = (EObject) values.get(node);
				if (tar!=null) {
					targets.add(tar);
				}
			}
		}
		return targets;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#getNestedMatches(org.eclipse.emf.henshin.model.Rule)
	 */
	@Override
	public List<Match> getNestedMatches(Rule multiRule) {
		@SuppressWarnings("unchecked")
		List<Match> nested = (List<Match>) values.get(multiRule);
		if (nested==null) {
			nested = new ArrayList<Match>();
			values.put(multiRule, nested);
		}
		return nested;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#overlapsWith(org.eclipse.emf.henshin.interpreter.Match)
	 */
	@Override
	public boolean overlapsWith(Match match) {
		List<EObject> common = getNodeTargets();
		common.retainAll(match.getNodeTargets());
		return !common.isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#isComplete()
	 */
	@Override
	public boolean isComplete() {
		if (nodes==null || getRule()==null) {
			return false;
		}
		if (!values.keySet().containsAll(nodes)) {
			return false;
		}
		for (Rule multiRule : getRule().getMultiRules()) {
			for (Match nestedMatch : getNestedMatches(multiRule)) {
				if (!nestedMatch.isComplete()) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#isValid()
	 */
	@Override
	public boolean isValid() {
		// Check completeness:
		if (!isComplete()) {
			return false;
		}
		// Check object types:
		for (Node node : nodes) {
			if (node.getType().isSuperTypeOf(((EObject) values.get(node)).eClass())) {
				return false;
			}
		}		
		// Check if all edges are present:
		for (Node node : nodes) {
			EObject source = (EObject) values.get(node);
			for (Edge edge : node.getOutgoing()) {
				EReference edgeType = edge.getType();
				EObject target = (EObject) values.get(edge.getTarget());
				if (edgeType.isMany()) {
					@SuppressWarnings("unchecked")
					List<EObject> targetObjects = (List<EObject>) source.eGet(edgeType);
					if (!targetObjects.contains(target)) {
						return false;
					}
				} else {
					if (source.eGet(edgeType)!=target) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj) || !(obj instanceof Match)) {
			return false;
		}
		Match match = (Match) obj;
		if (isResultMatch!=match.isResultMatch()) {
			return false;
		}
		if (nodes!=null) {
			for (Node node : nodes) {
				if (values.get(node)!=match.getNodeTarget(node)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.Match#isResultMatch()
	 */
	@Override
	public boolean isResultMatch() {
		return isResultMatch;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isResultMatch) {
			return "Result match for rule '" + unit.getName() + "':\n" + toStringWithIndent("");
		} else {
			return "Match for rule '" + unit.getName() + "':\n" + toStringWithIndent("");			
		}
	}

	/*
	 * toString helper.
	 */
	@Override
	protected String toStringWithIndent(String indent) {
		if (nodes.isEmpty()) {
			return indent + "- no nodes";
		}
		String result = "";
		int index = 1;
		for (Node node : nodes) {
			String name = node.getName()!=null ? "'" + node.getName() + "'" : "#" + index;
			result = result + indent + "- node " + name + " => " + getNodeTarget(node) + "\n";
			index++;
		}
		for (Rule multiRule : ((Rule) unit).getMultiRules()) {
			result = result + "\n" + indent + "  Multi-rule '" + multiRule.getName() + "':\n";
			List<Match> matches = getNestedMatches(multiRule);
			for (int i=0; i<matches.size(); i++) {
				result = result + "\n" + indent + "  Match #" + i + ":\n";
				Match match = matches.get(i);
				if (match instanceof MatchImpl) {
					result = result + ((MatchImpl) match).toStringWithIndent(indent + "  "); 
				} else {
					result = result + indent + "  " + match.toString();
				}
			}
		}
		return result;
		
	}

}
