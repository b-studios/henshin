/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model.util;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.henshin.model.*;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.model.HenshinPackage
 * @generated
 */
public class HenshinValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final HenshinValidator INSTANCE = new HenshinValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.emf.henshin.model";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;


	/**
	 * The parsed OCL expression for the definition of the '<em>ValidName</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint namedElement_ValidNameInvOCL;

	/**
	 * The parsed OCL expression for the definition of the '<em>EqualParentGraphs</em>' invariant constraint.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Constraint edge_EqualParentGraphsInvOCL;
	private static final String OCL_ANNOTATION_SOURCE = "http://www.eclipse.org/emf/2010/Henshin/OCL";
	
	private static final OCL OCL_ENV = OCL.newInstance();

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HenshinValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return HenshinPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case HenshinPackage.NAMED_ELEMENT:
				return validateNamedElement((NamedElement)value, diagnostics, context);
			case HenshinPackage.DESCRIBED_ELEMENT:
				return validateDescribedElement((DescribedElement)value, diagnostics, context);
			case HenshinPackage.TRANSFORMATION_SYSTEM:
				return validateTransformationSystem((TransformationSystem)value, diagnostics, context);
			case HenshinPackage.RULE:
				return validateRule((Rule)value, diagnostics, context);
			case HenshinPackage.ATTRIBUTE_CONDITION:
				return validateAttributeCondition((AttributeCondition)value, diagnostics, context);
			case HenshinPackage.VARIABLE:
				return validateVariable((Variable)value, diagnostics, context);
			case HenshinPackage.PORT:
				return validatePort((Port)value, diagnostics, context);
			case HenshinPackage.PORT_OBJECT:
				return validatePortObject((PortObject)value, diagnostics, context);
			case HenshinPackage.PORT_PARAMETER:
				return validatePortParameter((PortParameter)value, diagnostics, context);
			case HenshinPackage.GRAPH:
				return validateGraph((Graph)value, diagnostics, context);
			case HenshinPackage.GRAPH_ELEMENT:
				return validateGraphElement((GraphElement)value, diagnostics, context);
			case HenshinPackage.MAPPING:
				return validateMapping((Mapping)value, diagnostics, context);
			case HenshinPackage.NODE:
				return validateNode((Node)value, diagnostics, context);
			case HenshinPackage.ATTRIBUTE:
				return validateAttribute((Attribute)value, diagnostics, context);
			case HenshinPackage.EDGE:
				return validateEdge((Edge)value, diagnostics, context);
			case HenshinPackage.TRANSFORMATION_UNIT:
				return validateTransformationUnit((TransformationUnit)value, diagnostics, context);
			case HenshinPackage.INDEPENDENT_UNIT:
				return validateIndependentUnit((IndependentUnit)value, diagnostics, context);
			case HenshinPackage.SEQUENTIAL_UNIT:
				return validateSequentialUnit((SequentialUnit)value, diagnostics, context);
			case HenshinPackage.CONDITIONAL_UNIT:
				return validateConditionalUnit((ConditionalUnit)value, diagnostics, context);
			case HenshinPackage.PRIORITY_UNIT:
				return validatePriorityUnit((PriorityUnit)value, diagnostics, context);
			case HenshinPackage.AMALGAMATED_UNIT:
				return validateAmalgamatedUnit((AmalgamatedUnit)value, diagnostics, context);
			case HenshinPackage.COUNTED_UNIT:
				return validateCountedUnit((CountedUnit)value, diagnostics, context);
			case HenshinPackage.NESTED_CONDITION:
				return validateNestedCondition((NestedCondition)value, diagnostics, context);
			case HenshinPackage.FORMULA:
				return validateFormula((Formula)value, diagnostics, context);
			case HenshinPackage.UNARY_FORMULA:
				return validateUnaryFormula((UnaryFormula)value, diagnostics, context);
			case HenshinPackage.BINARY_FORMULA:
				return validateBinaryFormula((BinaryFormula)value, diagnostics, context);
			case HenshinPackage.AND:
				return validateAnd((And)value, diagnostics, context);
			case HenshinPackage.OR:
				return validateOr((Or)value, diagnostics, context);
			case HenshinPackage.NOT:
				return validateNot((Not)value, diagnostics, context);
			case HenshinPackage.PORT_MAPPING:
				return validatePortMapping((PortMapping)value, diagnostics, context);
			case HenshinPackage.PORT_KIND:
				return validatePortKind((PortKind)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(namedElement, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(namedElement, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ValidName constraint of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement_ValidName(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (namedElement_ValidNameInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.NAMED_ELEMENT);
			
			EAnnotation ocl = HenshinPackage.Literals.NAMED_ELEMENT.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("ValidName");
			
			try {
				namedElement_ValidNameInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(namedElement_ValidNameInvOCL);
		
		if (!query.check(namedElement)) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "ValidName", getObjectLabel(namedElement, context) },
						 new Object[] { namedElement },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDescribedElement(DescribedElement describedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(describedElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransformationSystem(TransformationSystem transformationSystem, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(transformationSystem, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(transformationSystem, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRule(Rule rule, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(rule, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(rule, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeCondition(AttributeCondition attributeCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(attributeCondition, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(attributeCondition, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVariable(Variable variable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(variable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(variable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(variable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(variable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(variable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(variable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(variable, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(variable, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePort(Port port, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(port, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(port, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(port, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(port, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(port, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(port, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(port, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(port, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePortObject(PortObject portObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(portObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(portObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(portObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(portObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(portObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(portObject, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(portObject, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(portObject, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePortParameter(PortParameter portParameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(portParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(portParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(portParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(portParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(portParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(portParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(portParameter, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(portParameter, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraph(Graph graph, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(graph, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(graph, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGraphElement(GraphElement graphElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(graphElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMapping(Mapping mapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(mapping, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNode(Node node, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(node, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(node, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(node, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEdge(Edge edge, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(edge, diagnostics, context);
		if (result || diagnostics != null) result &= validateEdge_EqualParentGraphs(edge, diagnostics, context);
		return result;
	}

	/**
	 * Validates the EqualParentGraphs constraint of '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEdge_EqualParentGraphs(Edge edge, DiagnosticChain diagnostics, Map<Object, Object> context) {
        if (edge_EqualParentGraphsInvOCL == null) {
			OCL.Helper helper = OCL_ENV.createOCLHelper();
			helper.setContext(HenshinPackage.Literals.EDGE);
			
			EAnnotation ocl = HenshinPackage.Literals.EDGE.getEAnnotation(OCL_ANNOTATION_SOURCE);
			String expr = ocl.getDetails().get("EqualParentGraphs");
			
			try {
				edge_EqualParentGraphsInvOCL = helper.createInvariant(expr);
			}
			catch (ParserException e) {
				throw new UnsupportedOperationException(e.getLocalizedMessage());
			}
		}
		
		Query<EClassifier, ?, ?> query = OCL_ENV.createQuery(edge_EqualParentGraphsInvOCL);
		
		if (!query.check(edge)) {
			if (diagnostics != null) {
				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_GenericConstraint_diagnostic",
						 new Object[] { "EqualParentGraphs", getObjectLabel(edge, context) },
						 new Object[] { edge },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTransformationUnit(TransformationUnit transformationUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(transformationUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(transformationUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIndependentUnit(IndependentUnit independentUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(independentUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(independentUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSequentialUnit(SequentialUnit sequentialUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(sequentialUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(sequentialUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConditionalUnit(ConditionalUnit conditionalUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(conditionalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(conditionalUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePriorityUnit(PriorityUnit priorityUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(priorityUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(priorityUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAmalgamatedUnit(AmalgamatedUnit amalgamatedUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(amalgamatedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(amalgamatedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(amalgamatedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(amalgamatedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(amalgamatedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(amalgamatedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(amalgamatedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(amalgamatedUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCountedUnit(CountedUnit countedUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validate_EveryMultiplicityConforms(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(countedUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateNamedElement_ValidName(countedUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNestedCondition(NestedCondition nestedCondition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(nestedCondition, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFormula(Formula formula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(formula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnaryFormula(UnaryFormula unaryFormula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(unaryFormula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinaryFormula(BinaryFormula binaryFormula, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(binaryFormula, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnd(And and, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(and, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOr(Or or, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(or, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNot(Not not, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(not, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePortMapping(PortMapping portMapping, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(portMapping, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePortKind(PortKind portKind, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //HenshinValidator