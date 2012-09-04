/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.commands;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;

/**
 * Custom command for adding rules to a modules.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class AddRuleCommand extends AddCommand implements Command {
	
	public AddRuleCommand(EditingDomain domain, Module module, Rule rule, int index) {
		this(domain, module, Collections.singleton(rule), index);
	}
	
	public AddRuleCommand(EditingDomain domain, Module module,
			Collection<Rule> rules, int index) {
		super(domain, module, HenshinPackage.eINSTANCE.getModule_Rules(), rules,
				index);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.edit.command.AddCommand#doExecute()
	 */
	@Override
	public void doExecute() {
		for (Object object : getCollection()) {
			Rule rule = (Rule) object;
			// Initialize LHS and RHS:
			if (rule.getLhs() == null) {
				Graph lhs = HenshinFactory.eINSTANCE.createGraph();
				lhs.setName("LHS");
				rule.setLhs(lhs);
			}
			if (rule.getRhs() == null) {
				Graph rhs = HenshinFactory.eINSTANCE.createGraph();
				rhs.setName("RHS");
				rule.setRhs(rhs);
			}
			// Set the name:
			if (rule.getName() == null) {
				rule.setName(HenshinModelUtils.generateNewRuleName((Module) owner));
			}
		}
		super.doExecute();
	}
	
}
