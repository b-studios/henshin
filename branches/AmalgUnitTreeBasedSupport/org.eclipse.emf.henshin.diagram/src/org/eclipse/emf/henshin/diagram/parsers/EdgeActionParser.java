/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.parsers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.actions.Action;
import org.eclipse.emf.henshin.diagram.edit.actions.ActionType;
import org.eclipse.emf.henshin.diagram.edit.actions.EdgeActionHelper;
import org.eclipse.emf.henshin.diagram.edit.actions.NodeActionHelper;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * Parser for edge action labels.
 * @author Christian Krause
 * @generated NOT
 */
public class EdgeActionParser extends AbstractParser {
	
	/**
	 * Default constructor.
	 */
	public EdgeActionParser() {
		super(new EAttribute[] {});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		Edge edge = (Edge) element.getAdapter(EObject.class);
		Action action = EdgeActionHelper.INSTANCE.getAction(edge);
		return (action!=null) ? action.toString() : "unknown";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		return "<<" + getEditString(element, flags) + ">>";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	public ICommand getParseCommand(IAdaptable element, final String value, int flags) {
		
		// Resolve the edge:
		final Edge edge = (Edge) element.getAdapter(EObject.class);
		
		// Get the editing domain:
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(edge);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		// Create parse command:
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, "Parse Edge Action", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(value, edge);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse an edge action.
	 */
	private CommandResult doParsing(String value, Edge edge) {
		try {
			// Parse the action:
			Action action = Action.parse(value);
			
			// Make sure the action is compatible with the source and target actions:
			NodeActionHelper helper = NodeActionHelper.INSTANCE;
			Action src = helper.getAction(helper.getActionNode(edge.getSource()));
			Action trg = helper.getAction(helper.getActionNode(edge.getTarget()));
			
			// Warning message:
			CommandResult warning = CommandResult.newWarningCommandResult("Cannot set edge action because its source and target nodes have incompatible types.", null);
			
			// Case where source and target have the same action:
			if (src.equals(trg)) {
				if (src.getType()!=ActionType.PRESERVE && !action.equals(src)) {
					return warning;
				}
			} else {
				// At least one must be of type PRESERVE:
				if (src.getType()!=ActionType.PRESERVE && trg.getType()!=ActionType.PRESERVE) {
					return warning;
				}
				// Action of the none-PRESERVE node must be the same as the edge action:
				Action nodeAction = (src.getType()!=ActionType.PRESERVE) ? src : trg;
				if (!action.equals(nodeAction)) {
					return warning;
				}
			}
			
			// Now we can safely set the action:
			EdgeActionHelper.INSTANCE.setAction(edge, action);
			return CommandResult.newOKCommandResult();
		}
		catch (Throwable t) {
			HenshinDiagramEditorPlugin.getInstance().logError("Error occurred when trying to set an edge action", t);
			return CommandResult.newErrorCommandResult(t);
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

}
