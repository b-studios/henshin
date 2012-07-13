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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.henshin.commands.NegligentRemoveCommand;
import org.eclipse.emf.henshin.commands.NodeComplexRemoveCommand;
import org.eclipse.emf.henshin.commands.SetNestedConditionCommand;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;

/**
 * This is the item provider adapter for a {@link org.eclipse.emf.henshin.model.Graph} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GraphItemProvider extends NamedElementItemProvider implements
		IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider,
		IItemLabelProvider, IItemPropertySource, IItemColorProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public GraphItemProvider(AdapterFactory adapterFactory) {
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
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(HenshinPackage.Literals.GRAPH__NODES);
			childrenFeatures.add(HenshinPackage.Literals.GRAPH__EDGES);
			childrenFeatures.add(HenshinPackage.Literals.GRAPH__FORMULA);
		}
		return childrenFeatures;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}
	
	/**
	 * This returns Graph.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		Graph graph = (Graph) object;
		if (graph.getContainerRule() != null) {
			if (graph == graph.getContainerRule().getLhs())
				return overlayImage(object, getResourceLocator().getImage("full/obj16/Graph_L.png"));
			else if (graph == graph.getContainerRule().getRhs())
				return overlayImage(object, getResourceLocator().getImage("full/obj16/Graph_R.png"));
			
		}// if
		
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Graph.png"));
	}
	
	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Graph)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Graph_type") :
			getString("_UI_Graph_type") + " " + label;
	}
	
	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Graph.class)) {
			case HenshinPackage.GRAPH__NODES:
			case HenshinPackage.GRAPH__EDGES:
			case HenshinPackage.GRAPH__FORMULA:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}
	
	public void notifyCorrespondingEdges(Graph graph, Notification notification) {
		if (graph.isLhs()) {
			Graph rhs = graph.getContainerRule().getRhs();
			ItemProviderAdapter adapter = (ItemProviderAdapter) this.adapterFactory.adapt(rhs,
					Graph.class);
			adapter.fireNotifyChanged(new ViewerNotification(notification, rhs, true, true));
		} else if (graph.isRhs()) {
			Graph lhs = graph.getContainerRule().getLhs();
			ItemProviderAdapter adapter = (ItemProviderAdapter) this.adapterFactory.adapt(lhs,
					Graph.class);
			adapter.fireNotifyChanged(new ViewerNotification(notification, lhs, true, true));
		}
	}
	
	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
		
		newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.GRAPH__NODES,
				HenshinFactory.eINSTANCE.createNode()));
		
		newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.GRAPH__EDGES,
				HenshinFactory.eINSTANCE.createEdge()));
		
		/*
		 * If this graph is contained in TransformationSystem directly, it
		 * represents a host graph which has no Formulas
		 */
		if (((Graph) object).isLhs() || ((Graph) object).isNestedCondition()) {
			newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.GRAPH__FORMULA,
					HenshinFactory.eINSTANCE.createNestedCondition()));
			
			newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.GRAPH__FORMULA,
					HenshinFactory.eINSTANCE.createAnd()));
			
			newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.GRAPH__FORMULA,
					HenshinFactory.eINSTANCE.createOr()));
			
			newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.GRAPH__FORMULA,
					HenshinFactory.eINSTANCE.createXor()));
			
			newChildDescriptors.add(createChildParameter(HenshinPackage.Literals.GRAPH__FORMULA,
					HenshinFactory.eINSTANCE.createNot()));
			
		}// if
	}// collectNewChildDescriptors
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createRemoveCommand
	 * (org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection) {
		HenshinPackage pack = HenshinPackage.eINSTANCE;
		// if (feature == pack.getGraph_Nodes() || feature ==
		// pack.getGraph_Edges()) {
		// Graph graph = (Graph) owner;
		// if (graph.isLhs() || graph.isRhs()) {
		// Rule rule = graph.getContainerRule();
		// for (Object o : collection) {
		// if (rule.getOriginInKernelRule((GraphElement) o) != null)
		// return UnexecutableCommand.INSTANCE;
		// }
		// }
		// }
		
		if (feature == pack.getGraph_Nodes()) {
			return new NodeComplexRemoveCommand(domain, (Graph) owner,
					(Collection<Node>) collection);
		}
		
		// Objects might have been removed as side effect of other remove
		// commands
		
		if (feature == pack.getGraph_Edges()) {
			Command cmd = new NegligentRemoveCommand(domain, owner, feature, collection);
			return cmd;
		}
		
		return super.createRemoveCommand(domain, owner, feature, collection);
	}
	
	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection<?> collection, int index) {
		
		// HenshinPackage pack = HenshinPackage.eINSTANCE;
		//
		// if (feature == pack.getGraph_Nodes())
		// return createAddNodeCommand(domain, owner, feature, collection,
		// index);
		//
		// else if (feature == pack.getGraph_Edges())
		// return createAddEdgeCommand(domain, owner, feature, collection,
		// index);
		//
		// else
		return super.createAddCommand(domain, owner, feature, collection, index);
	}
	
	/**
	 *
	 *  
	 */
	// protected Command createAddNodeCommand(EditingDomain domain, EObject
	// owner,
	// EStructuralFeature feature, Collection<?> collection, int index) {
	// HenshinPackage pack = HenshinPackage.eINSTANCE;
	// Graph graph = (Graph) owner;
	// CompoundCommand cmpCmd = new
	// CompoundCommand(CompoundCommand.LAST_COMMAND_ALL);
	// for (Graph dependentGraph :
	// HenshinMultiRuleUtil.getDependentGraphs(graph)) {
	// Rule dependentRule = dependentGraph.getContainerRule();
	// for (Object cObj : collection) {
	// Node node = (Node) cObj;
	// Node dependentNode = EcoreUtil.copy(node);
	// cmpCmd.append(createAddCommand(domain, dependentGraph, feature,
	// Collections.singleton(dependentNode), index));
	// CommandParameter cmdParam = new CommandParameter(dependentRule,
	// pack.getRule_MultiMappings(),
	// Collections.singleton(HenshinFactory.eINSTANCE.createMapping(node,
	// dependentNode)), CommandParameter.NO_INDEX);
	// cmpCmd.append(domain.createCommand(AddCommand.class, cmdParam));
	// }
	// }
	// cmpCmd.append(super.createAddCommand(domain, owner, feature, collection,
	// index));
	// return cmpCmd.unwrap();
	// }
	
	/**
	 * 
	 *
	 */
	// protected Command createAddEdgeCommand(EditingDomain domain, EObject
	// owner,
	// EStructuralFeature feature, Collection<?> collection, int index) {
	// Graph graph = (Graph) owner;
	// CompoundCommand cmpCmd = new
	// CompoundCommand(CompoundCommand.LAST_COMMAND_ALL);
	// for (Graph dependentGraph :
	// HenshinMultiRuleUtil.getDependentGraphs(graph)) {
	// Rule dependentRule = dependentGraph.getContainerRule();
	// Collection<Edge> collectionCopy = new ArrayList<Edge>(collection.size());
	// for (Object o : collection) {
	// Edge edge = (Edge) o;
	// Edge dependentEdge = HenshinMultiRuleUtil.getDependentEdgeInRule(edge,
	// dependentRule);
	// if (dependentEdge == null) {
	// Edge edgeCopy = EcoreUtil.copy(edge);
	// edgeCopy.setSource(HenshinMultiRuleUtil.getDependentNodeInRule(
	// edge.getSource(), dependentRule));
	// edgeCopy.setTarget(HenshinMultiRuleUtil.getDependentNodeInRule(
	// edge.getTarget(), dependentRule));
	// collectionCopy.add(edgeCopy);
	// }
	// }
	// if (!collectionCopy.isEmpty())
	// cmpCmd.append(createAddCommand(domain, dependentGraph, feature,
	// collectionCopy,
	// index));
	// }
	// cmpCmd.append(super.createAddCommand(domain, owner, feature, collection,
	// index));
	// return cmpCmd.unwrap();
	// }
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.edit.provider.ItemProviderAdapter#createSetCommand(org
	 * .eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Object value) {
		if ((feature == HenshinPackage.eINSTANCE.getGraph_Formula())) {
			return new SetNestedConditionCommand(domain, owner, feature, value);
		}
		return super.createSetCommand(domain, owner, feature, value);
	}
	
	@Override
	public Object getToolTip(Object object) {
		Graph g = (Graph) object;
		return g.getName() + "(Nodes: " + g.getNodes().size() + ", Edges: " + g.getEdges().size()
				+ ")";
	}
	
}
