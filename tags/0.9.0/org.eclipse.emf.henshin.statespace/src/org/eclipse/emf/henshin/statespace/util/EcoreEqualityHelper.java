package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.statespace.EqualityHelper;
import org.eclipse.emf.henshin.statespace.Model;

/**
 * Helper class for deciding whether two models are Ecore equal.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class EcoreEqualityHelper extends EcoreUtil.EqualityHelper {

	// Default serial ID:
	private static final long serialVersionUID = 1L;

	// State equality helper:
	private EqualityHelper equalityHelper;

	// Cached models to be compared.
	private Model model1, model2;

	/**
	 * Default constructor.
	 */
	public EcoreEqualityHelper(EqualityHelper equalityHelper) {
		this.equalityHelper = equalityHelper;
	}

	/**
	 * Check Ecore equality for two models.
	 * 
	 * @param model1 Model 1.
	 * @param model2 Model 2.
	 * @return <code>true</code> if they are equal.
	 */
	public boolean equals(Model model1, Model model2) {

		// Cache the models:
		this.model1 = model1;
		this.model2 = model2;

		// Check equality:
		boolean result = equals(model1.getResource().getContents(), model2
				.getResource().getContents());

		// Release the models again:
		this.model1 = null;
		this.model2 = null;

		// Done.
		return result;

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#equals(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean equals(EObject o1, EObject o2) {
		if (o1!=null && equalityHelper.getIdentityTypes().contains(o1.eClass())) {
			int key1 = model1.getObjectKeysMap().get(o1);
			int key2 = model2.getObjectKeysMap().get(o2);
			if (key1!=key2) {
				return false;
			}
		}
		return super.equals(o1, o2);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#haveEqualAttribute(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute)
	 */
	@Override
	protected boolean haveEqualAttribute(EObject o1, EObject o2, EAttribute attribute) {
		return (equalityHelper.getIgnoredAttributes().contains(attribute) ||
				super.haveEqualAttribute(o1, o2, attribute));
	}

}
