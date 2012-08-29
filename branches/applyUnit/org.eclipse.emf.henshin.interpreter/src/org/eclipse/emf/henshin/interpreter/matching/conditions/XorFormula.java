/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.matching.conditions;

public class XorFormula implements IFormula {
	
	private final IFormula left;
	private final IFormula right;
	
	public XorFormula(IFormula left, IFormula right) {
		this.left = left;
		this.right = right;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.conditions.IFormula#eval()
	 */
	@Override
	public boolean eval() {
		return left.eval() ^ right.eval();
	}
	
}