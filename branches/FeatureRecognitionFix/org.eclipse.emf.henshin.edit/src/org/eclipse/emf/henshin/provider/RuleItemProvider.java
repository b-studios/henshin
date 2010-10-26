/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.emf.henshin.model.Rule} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class RuleItemProvider extends TransformationUnitItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RuleItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);
			
		}
		return itemPropertyDescriptors;
	}
	
	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HenshinPackage.Literals.RULE__LHS);
			childrenFeatures.add(HenshinPackage.Literals.RULE__RHS);
			childrenFeatures.add(HenshinPackage.Literals.RULE__ATTRIBUTE_CONDITIONS);
			childrenFeatures.add(HenshinPackage.Literals.RULE__MAPPINGS);
		}
		return childrenFeatures;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.
		
		return super.getChildFeature(object, child);
	}
	
	/**
	 * This returns Rule.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Rule"));
	}
	
	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Rule) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Rule_type")
				: getString("_UI_Rule_type") + " " + label;
	}
	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		
		switch (notification.getFeatureID(Rule.class)) {
			case HenshinPackage.RULE__LHS:
			case HenshinPackage.RULE__RHS:
			case HenshinPackage.RULE__ATTRIBUTE_CONDITIONS:
			case HenshinPackage.RULE__MAPPINGS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(),
						true, false));
				return;
		}
		super.notifyChanged(notification);
	}
	
	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
		
		newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.RULE__LHS,
				HenshinFactory.eINSTANCE.createGraph()));
		
		newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.RULE__RHS,
				HenshinFactory.eINSTANCE.createGraph()));
		
		newChildDescriptors.add(createChildParameter(
				HenshinPackage.Literals.RULE__ATTRIBUTE_CONDITIONS,
				HenshinFactory.eINSTANCE.createAttributeCondition()));
		
		newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.RULE__MAPPINGS,
				HenshinFactory.eINSTANCE.createMapping()));
	}
	
	/**
	 * This returns the label text for
	 * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child,
			Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;
		
		boolean qualify = childFeature == HenshinPackage.Literals.RULE__LHS
				|| childFeature == HenshinPackage.Literals.RULE__RHS;
		
		if (qualify) {
			return getString("_UI_CreateChild_text2", new Object[] { getTypeText(childObject),
					getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}
	
	// /* (non-Javadoc)
	// * @see
	// org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang.Object)
	// */
	// @Override
	// public Object getParent(Object object) {
	// Object o = super.getParent(object);
	//
	// if (o instanceof AmalgamationUnit) {
	// AmalgamationUnit au = (AmalgamationUnit) o;
	// AmalgamationUnitItemProvider auIp = (AmalgamationUnitItemProvider)
	// adapterFactory
	// .adapt(au, IEditingDomainItemProvider.class);
	// if (au.getKernelRule().equals(object)) {
	// return auIp != null ? auIp.getKernelRuleItemProvider() : null;
	// } else {
	// return auIp != null ? auIp.getMuliRulesItemProvider() : null;
	// }
	// } else {
	// return super.getParent(object);
	// }
	//
	// }
	
}