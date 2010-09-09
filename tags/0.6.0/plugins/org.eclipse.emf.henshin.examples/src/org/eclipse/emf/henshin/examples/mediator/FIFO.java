/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.examples.mediator;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>FIFO</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.FIFO#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.FIFO#getSink <em>Sink</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.FIFO#isFull <em>Full</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getFIFO()
 * @model kind="class"
 * @generated
 */
public class FIFO extends Channel {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Node source;

	/**
	 * The cached value of the '{@link #getSink() <em>Sink</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSink()
	 * @generated
	 * @ordered
	 */
	protected Node sink;

	/**
	 * The default value of the '{@link #isFull() <em>Full</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFull()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FULL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFull() <em>Full</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFull()
	 * @generated
	 * @ordered
	 */
	protected boolean full = FULL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FIFO() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MediatorPackage.Literals.FIFO;
	}

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Node)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getFIFO_Source()
	 * @model
	 * @generated
	 */
	public Node getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (Node)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediatorPackage.FIFO__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSource() {
		return source;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.FIFO#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	public void setSource(Node newSource) {
		Node oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.FIFO__SOURCE, oldSource, source));
	}

	/**
	 * Returns the value of the '<em><b>Sink</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sink</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sink</em>' reference.
	 * @see #setSink(Node)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getFIFO_Sink()
	 * @model
	 * @generated
	 */
	public Node getSink() {
		if (sink != null && sink.eIsProxy()) {
			InternalEObject oldSink = (InternalEObject)sink;
			sink = (Node)eResolveProxy(oldSink);
			if (sink != oldSink) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediatorPackage.FIFO__SINK, oldSink, sink));
			}
		}
		return sink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSink() {
		return sink;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.FIFO#getSink <em>Sink</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sink</em>' reference.
	 * @see #getSink()
	 * @generated
	 */
	public void setSink(Node newSink) {
		Node oldSink = sink;
		sink = newSink;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.FIFO__SINK, oldSink, sink));
	}

	/**
	 * Returns the value of the '<em><b>Full</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Full</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Full</em>' attribute.
	 * @see #setFull(boolean)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getFIFO_Full()
	 * @model
	 * @generated
	 */
	public boolean isFull() {
		return full;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.FIFO#isFull <em>Full</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Full</em>' attribute.
	 * @see #isFull()
	 * @generated
	 */
	public void setFull(boolean newFull) {
		boolean oldFull = full;
		full = newFull;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.FIFO__FULL, oldFull, full));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MediatorPackage.FIFO__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case MediatorPackage.FIFO__SINK:
				if (resolve) return getSink();
				return basicGetSink();
			case MediatorPackage.FIFO__FULL:
				return isFull();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MediatorPackage.FIFO__SOURCE:
				setSource((Node)newValue);
				return;
			case MediatorPackage.FIFO__SINK:
				setSink((Node)newValue);
				return;
			case MediatorPackage.FIFO__FULL:
				setFull((Boolean)newValue);
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
			case MediatorPackage.FIFO__SOURCE:
				setSource((Node)null);
				return;
			case MediatorPackage.FIFO__SINK:
				setSink((Node)null);
				return;
			case MediatorPackage.FIFO__FULL:
				setFull(FULL_EDEFAULT);
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
			case MediatorPackage.FIFO__SOURCE:
				return source != null;
			case MediatorPackage.FIFO__SINK:
				return sink != null;
			case MediatorPackage.FIFO__FULL:
				return full != FULL_EDEFAULT;
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
		result.append(" (full: ");
		result.append(full);
		result.append(')');
		return result.toString();
	}

} // FIFO
