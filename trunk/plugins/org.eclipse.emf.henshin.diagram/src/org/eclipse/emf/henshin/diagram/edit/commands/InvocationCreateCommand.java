/*
 * Copyright (c) 2010 HPI Potsdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     HPI Potsdam - initial API and implementation
 */
package org.eclipse.emf.henshin.diagram.edit.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class InvocationCreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	public InvocationCreateCommand(CreateElementRequest req) {
		super(req.getLabel(), null, req);
	}

	/**
	 * FIXME: replace with setElementToEdit()
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest())
				.getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated NOT
	 */
	public boolean canExecute() {
		// We need at least one target candidate:
		return !getTargetCandidates().isEmpty();
	}
	
	/*
	 * Helper method: get the current transformation system.
	 */
	private TransformationSystem getTransformationSystem() {
		EObject object = getElementToEdit();
		while (object!=null) {
			if (object instanceof TransformationSystem) {
				return (TransformationSystem) object;
			}
			object = object.eContainer();
		}
		return null;
	}
	
	/*
	 * Helper method: get a list of possible target candidate units.
	 */
	private List<TransformationUnit> getTargetCandidates() {
		List<TransformationUnit> candidates = new ArrayList<TransformationUnit>();
		TransformationSystem system = getTransformationSystem();
		if (system!=null) {
			candidates.addAll(system.getRules());
			candidates.addAll(system.getTransformationUnits());
		}
		return candidates;
	}
	
	/**
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		
		// Get the owner unit and the target candidate units:
		TransformationUnit owner = (TransformationUnit) getElementToEdit();
		List<TransformationUnit> candidates = getTargetCandidates();
		
		// Try to be smart: in most cases we don't want duplicate invocations:
		for (TransformationUnit used : owner.getSubUnits(false)) {
			if (candidates.size()>1) {
				candidates.remove(used);
			} else break;
		}
		
		// Now we just take the first candidate:
		TransformationUnit target = candidates.get(0);

		// Add it to the parent unit:
		if (owner instanceof SequentialUnit) {
			((SequentialUnit) owner).getSubUnits().add(target);
		}
		else if (owner instanceof PriorityUnit) {
			((PriorityUnit) owner).getSubUnits().add(target);
		}
		else if (owner instanceof IndependentUnit) {
			((IndependentUnit) owner).getSubUnits().add(target);
		}
		else if (owner instanceof ConditionalUnit) {
			ConditionalUnit cond = (ConditionalUnit) owner;
			if (cond.getIf()==null) {
				cond.setIf(target);
			}
			else if (cond.getThen()==null) {
				cond.setThen(target);
			}
			else if (cond.getElse()==null) {
				cond.setElse(target);
			}
		}
		else if (owner instanceof CountedUnit) {
			if (((CountedUnit) owner).getSubUnit()==null) {
				((CountedUnit) owner).setSubUnit(target);
			}
		}

		// No need to configure.
		// doConfigure(newElement, monitor, info);
		
		// Done.
		((CreateElementRequest) getRequest()).setNewElement(target);
		return CommandResult.newOKCommandResult(target);
		
	}

	/**
	 * @generated
	 */
	protected void doConfigure(TransformationUnit newElement,
			IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

}
