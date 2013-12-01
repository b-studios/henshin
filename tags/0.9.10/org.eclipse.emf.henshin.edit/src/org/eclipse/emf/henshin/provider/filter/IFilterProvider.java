/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.provider.filter;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public interface IFilterProvider {
		
	public boolean isFiltered(EStructuralFeature feature);
	
}
