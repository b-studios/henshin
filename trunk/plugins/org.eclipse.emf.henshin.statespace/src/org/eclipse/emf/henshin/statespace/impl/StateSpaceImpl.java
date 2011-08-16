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
package org.eclipse.emf.henshin.statespace.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * Concrete implementation of the {@link State} interface.
 * @generated
 */
public class StateSpaceImpl extends StorageImpl implements StateSpace {

	/**
	 * Default constructor.
	 * @generated NOT
	 */
	protected StateSpaceImpl() {
		super();
		// Create a default equality helper:
		setEqualityHelper(StateSpaceFactory.eINSTANCE.createStateEqualityHelper());
	}

	/**
	 * Get the list of open states in this state space.
	 * @generated NOT
	 */
	public EList<State> getOpenStates() {
		if (openStates == null) {
			openStates = new EObjectResolvingEList<State>(State.class, this, StateSpacePackage.STATE_SPACE__OPEN_STATES) {
				private static final long serialVersionUID = 1L;
				@Override
				protected boolean isUnique() {
					// For performance we omit the uniqueness check.
					return false;
				}
			};
		}
		return openStates;
	}
	
	/**
	 * Remove a state and detach its transitions from the other states.
	 * The transitions are still connected to the removed node afterwards.
	 * All predecessor states are automatically marked as open.
	 * @generated NOT
	 */
	public boolean removeState(State state) {
		
		// Try to remove the state:
		if (getStates().remove(state)) {
			
			// Detach incoming transitions:
			for (Transition transition : state.getIncoming()) {
				
				// Mark the predecessor state as open!
				State source = transition.getSource();
				if (source!=null) {
					source.setOpen(true);
					if (!getOpenStates().contains(source)) {
						getOpenStates().add(source);
					}
				}
				
				// Detach...
				transition.setSource(null);
				
			}
			
			// Detach outgoing transitions:
			for (Transition transition : state.getOutgoing()) {
				transition.setTarget(null);
			}
			
			// Reset the indizes of all states:
			int index = 0;
			for (State current : getStates()) {
				current.setIndex(index++);
			}
			
			// Done.
			return true;
			
		} else {
			return false;
		}
		
	}

	/**
	 * @generated NOT
	 */
	private int getPercent(int index, int def) {
		int p = getData(index);
		return (p==0) ? def : Math.min(100,Math.max(p,1));
	}

	/**
	 * @generated NOT
	 */
	private void setPercent(int index, int p) {
		p = Math.min(100,Math.max(p,1));
		if (getData(index)!=p) {
			setData(index, p);
		}
	}

	
	/**
	 * @generated NOT
	 */
	public int getZoomLevel() {
		return getPercent(0, 100);
	}

	/**
	 * @generated NOT
	 */
	public void setZoomLevel(int zoomLevel) {
		setPercent(0, zoomLevel);
	}

	/**
	 * @generated NOT
	 */
	public int getStateRepulsion() {
		return getPercent(1, 50);
	}

	/**
	 * @generated NOT
	 */
	public void setStateRepulsion(int repulsion) {
		setPercent(1, repulsion);
	}

	/**
	 * @generated NOT
	 */
	public int getTransitionAttraction() {
		return getPercent(2, 50);
	}

	/**
	 * @generated NOT
	 */
	public void setTransitionAttraction(int attraction) {
		setPercent(2, attraction);
	}

	/**
	 * @generated NOT
	 */
	public int getMaxStateDistance() {
		int maxDistance = getData(3);
		return (maxDistance>0) ? maxDistance : -1;
	}

	/**
	 * @generated NOT
	 */
	public void setMaxStateDistance(int maxStateDistance) {
		setData(3, maxStateDistance);
	}


	/**
	 * @generated NOT
	 */
	public boolean isHideLabels() {
		return getData(4)!=0;
	}

	/**
	 * @generated NOT
	 */
	public void setHideLabels(boolean hideLabels) {
		setData(4, hideLabels ? 1 : 0);
	}

	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> rules;

	
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;

	/**
	 * The cached value of the '{@link #getInitialStates() <em>Initial States</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> initialStates;

	/**
	 * The cached value of the '{@link #getOpenStates() <em>Open States</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpenStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> openStates;

	/**
	 * The default value of the '{@link #getTransitionCount() <em>Transition Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionCount()
	 * @generated
	 * @ordered
	 */
	protected static final int TRANSITION_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTransitionCount() <em>Transition Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionCount()
	 * @generated
	 * @ordered
	 */
	protected int transitionCount = TRANSITION_COUNT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEqualityHelper() <em>Equality Helper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEqualityHelper()
	 * @generated
	 * @ordered
	 */
	protected StateEqualityHelper equalityHelper;

	/**
	 * The default value of the '{@link #getZoomLevel() <em>Zoom Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZoomLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int ZOOM_LEVEL_EDEFAULT = 0;


	/**
	 * The default value of the '{@link #getStateRepulsion() <em>State Repulsion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateRepulsion()
	 * @generated
	 * @ordered
	 */
	protected static final int STATE_REPULSION_EDEFAULT = 0;


	/**
	 * The default value of the '{@link #getTransitionAttraction() <em>Transition Attraction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionAttraction()
	 * @generated
	 * @ordered
	 */
	protected static final int TRANSITION_ATTRACTION_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getMaxStateDistance() <em>Max State Distance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxStateDistance()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_STATE_DISTANCE_EDEFAULT = -1;


	/**
	 * The default value of the '{@link #isHideLabels() <em>Hide Labels</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHideLabels()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIDE_LABELS_EDEFAULT = false;


	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> properties;

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackage.Literals.STATE_SPACE;
	}
	
	/**
	 * Get the list of states in this state space.
	 * @generated
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentWithInverseEList<State>(State.class, this, StateSpacePackage.STATE_SPACE__STATES, StateSpacePackage.STATE__STATE_SPACE);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getInitialStates() {
		if (initialStates == null) {
			initialStates = new EObjectResolvingEList<State>(State.class, this, StateSpacePackage.STATE_SPACE__INITIAL_STATES);
		}
		return initialStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getRules() {
		if (rules == null) {
			rules = new EObjectResolvingEList<Rule>(Rule.class, this, StateSpacePackage.STATE_SPACE__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTransitionCount() {
		return transitionCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransitionCount(int newTransitionCount) {
		int oldTransitionCount = transitionCount;
		transitionCount = newTransitionCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_SPACE__TRANSITION_COUNT, oldTransitionCount, transitionCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateEqualityHelper getEqualityHelper() {
		return equalityHelper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, StateSpacePackage.STATE_SPACE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEqualityHelper(StateEqualityHelper newEqualityHelper, NotificationChain msgs) {
		StateEqualityHelper oldEqualityHelper = equalityHelper;
		equalityHelper = newEqualityHelper;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, oldEqualityHelper, newEqualityHelper);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEqualityHelper(StateEqualityHelper newEqualityHelper) {
		if (newEqualityHelper != equalityHelper) {
			NotificationChain msgs = null;
			if (equalityHelper != null)
				msgs = ((InternalEObject)equalityHelper).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, null, msgs);
			if (newEqualityHelper != null)
				msgs = ((InternalEObject)newEqualityHelper).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, null, msgs);
			msgs = basicSetEqualityHelper(newEqualityHelper, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_SPACE__EQUALITY_HELPER, newEqualityHelper, newEqualityHelper));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStates()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				return basicSetEqualityHelper(null, msgs);
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				return getRules();
			case StateSpacePackage.STATE_SPACE__STATES:
				return getStates();
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				return getInitialStates();
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				return getOpenStates();
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				return getTransitionCount();
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				return getEqualityHelper();
			case StateSpacePackage.STATE_SPACE__ZOOM_LEVEL:
				return getZoomLevel();
			case StateSpacePackage.STATE_SPACE__STATE_REPULSION:
				return getStateRepulsion();
			case StateSpacePackage.STATE_SPACE__TRANSITION_ATTRACTION:
				return getTransitionAttraction();
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				return getMaxStateDistance();
			case StateSpacePackage.STATE_SPACE__HIDE_LABELS:
				return isHideLabels();
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				if (coreType) return getProperties();
				else return getProperties().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends Rule>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends State>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				getInitialStates().clear();
				getInitialStates().addAll((Collection<? extends State>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				getOpenStates().clear();
				getOpenStates().addAll((Collection<? extends State>)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				setTransitionCount((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				setEqualityHelper((StateEqualityHelper)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__ZOOM_LEVEL:
				setZoomLevel((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__STATE_REPULSION:
				setStateRepulsion((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__TRANSITION_ATTRACTION:
				setTransitionAttraction((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				setMaxStateDistance((Integer)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__HIDE_LABELS:
				setHideLabels((Boolean)newValue);
				return;
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				((EStructuralFeature.Setting)getProperties()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				getRules().clear();
				return;
			case StateSpacePackage.STATE_SPACE__STATES:
				getStates().clear();
				return;
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				getInitialStates().clear();
				return;
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				getOpenStates().clear();
				return;
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				setTransitionCount(TRANSITION_COUNT_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				setEqualityHelper((StateEqualityHelper)null);
				return;
			case StateSpacePackage.STATE_SPACE__ZOOM_LEVEL:
				setZoomLevel(ZOOM_LEVEL_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__STATE_REPULSION:
				setStateRepulsion(STATE_REPULSION_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__TRANSITION_ATTRACTION:
				setTransitionAttraction(TRANSITION_ATTRACTION_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				setMaxStateDistance(MAX_STATE_DISTANCE_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__HIDE_LABELS:
				setHideLabels(HIDE_LABELS_EDEFAULT);
				return;
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				getProperties().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_SPACE__RULES:
				return rules != null && !rules.isEmpty();
			case StateSpacePackage.STATE_SPACE__STATES:
				return states != null && !states.isEmpty();
			case StateSpacePackage.STATE_SPACE__INITIAL_STATES:
				return initialStates != null && !initialStates.isEmpty();
			case StateSpacePackage.STATE_SPACE__OPEN_STATES:
				return openStates != null && !openStates.isEmpty();
			case StateSpacePackage.STATE_SPACE__TRANSITION_COUNT:
				return transitionCount != TRANSITION_COUNT_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__EQUALITY_HELPER:
				return equalityHelper != null;
			case StateSpacePackage.STATE_SPACE__ZOOM_LEVEL:
				return getZoomLevel() != ZOOM_LEVEL_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__STATE_REPULSION:
				return getStateRepulsion() != STATE_REPULSION_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__TRANSITION_ATTRACTION:
				return getTransitionAttraction() != TRANSITION_ATTRACTION_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__MAX_STATE_DISTANCE:
				return getMaxStateDistance() != MAX_STATE_DISTANCE_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__HIDE_LABELS:
				return isHideLabels() != HIDE_LABELS_EDEFAULT;
			case StateSpacePackage.STATE_SPACE__PROPERTIES:
				return properties != null && !properties.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (transitionCount: ");
		result.append(transitionCount);
		result.append(')');
		return result.toString();
	}

} //StateSpaceImpl
