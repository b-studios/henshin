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
package org.eclipse.emf.henshin.testframework;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.util.ModelUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.interpreter.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.impl.EmfEngine;
import org.eclipse.emf.henshin.interpreter.util.HenshinGraph;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.ocl.conditions.BooleanOCLCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.OCL;

/**
 * This class contains an assortment of various tools useful for testing and
 * debugging henshin rules.
 * 
 * @see HenshinLoaders
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class Tools {
	/*----------------------------
	 * MISC
	 * ----------------------------*/

	/**
	 * Get number of elements in the specified graph; execute
	 * {@link UnitApplicationImpl}; get number of elements in the specified graph
	 * again
	 * 
	 * @param ua
	 *            {@link UnitApplicationImpl}
	 * @return int[2]: <br>
	 *         [0] size before execution<br>
	 *         [1] size after execution
	 */
	protected static int[] getGraphSizes(UnitApplicationImpl ua) {
		EmfGraph graph = ((EmfEngine) ua.getInterpreterEngine()).getEmfGraph();
		int[] sizes = new int[2];
		sizes[0] = graph.geteObjects().size();
		ua.execute();
		sizes[1] = graph.geteObjects().size();
		return sizes;
	}
	
	/**
	 * <strong>DEPRECATED</strong>. Use a cast.
	 * Creates a {@link TransformationUnit} containing only the specified rule<br>
	 * (Actually, a {@link CountedUnit} with count = 1 will be created)
	 * 
	 * @param r
	 *            {@link Rule}
	 * @return {@link TransformationUnit}
	 */
	@Deprecated
	public static TransformationUnit createTUFromRule(Rule r) {
		return (TransformationUnit) r;
	}
	
	/**
	 * print matches
	 * 
	 * @param ma
	 */
	public static void printMatches(List<Match> ma) {
		System.out.println("matches:");
		for (Match m : ma) {
			printSubmatchesRec(m, 1);
			System.out.println("===");
		}
	}
	
	public static void printSubmatchesRec(Match m, int ident) {
		
		for (EObject eo : m.getNodeMapping().values()) {
			System.out.println(getTabs(ident) + eo);
		}
		
		
		if (m.getNestedMatches().size() == 0) {
			System.out.println(getTabs(ident) + "--");
			return;
		}

		for (Entry<Rule, List<Match>> e : m.getNestedMatches().entrySet()) {
			System.out.println(getTabs(ident+1) + "Rule: " + e.getKey());
			for (Match ma : e.getValue()) {
				printSubmatchesRec(ma, ident+1);
			}
		}
	}
	
	private static String getTabs(int i) {
		String tmp = "";
		while(i-- > 0) {
			tmp = tmp + "\t";
		}
		
		return tmp;
	}
	
	
	/**
	 * print match
	 * 
	 * @param ma
	 */
	public static void printMatch(Match ma) {
		System.out.println("match:");
		for (EObject eo : ma.getNodeMapping().values()) {
			System.out.println("\t" + eo);
		}
		System.out.println("--");
	}
	
	/**
	 * print matches
	 * 
	 * @param ma
	 */
	public static void printMatches(Match ma) {
		printMatch(ma);
	}
	
	/**
	 * print graph
	 * 
	 * @param graph
	 */
	public static void printGraph(EmfGraph graph) {
		for (EObject eo : graph.geteObjects()) {
			System.out.println(eo);
		}
	}
	
	/**
	 * print graph
	 * 
	 * @param graph
	 */
	public static void printGraph(Graph graph) {
		for (EObject eo : graph.eContents()) {
			System.out.println(eo);
			for (Edge edge : graph.getEdges()) {
				System.out.println("edge: " + edge);
			}
		}
	}
	
	/**
	 * store a graph to disk for analyzing
	 * 
	 * @param rootObject
	 * @param path
	 * @throws IOException
	 */
	public static void persist(EObject rootObject, String filename) throws IOException {
		ModelUtils.save(rootObject, filename);
	}
	
	@Deprecated
	public static void persistAllEmbeddedGraphs(TransformationSystem ts, String path, String fileExt)
			throws IOException {
		for (Graph g : ts.getInstances()) {
			HenshinGraph hgr = new HenshinGraph(g);
			System.out.println("saving " + path + g.getName() + "." + fileExt);
			persist(getGraphRoot(hgr), path + g.getName() + "." + fileExt);
		}
	}
	
	/**
	 * store a rule to disk for analyzing
	 * 
	 * @param rule
	 *            Rule to be stored
	 * @param filename
	 *            Path to store URI
	 * @throws IOException
	 */
	public static void persist(Rule rule, String filename) throws IOException {
		ResourceSet rSet = new ResourceSetImpl();
		URI myURI = URI.createFileURI(filename);
		Resource res = rSet.createResource(myURI, "henshin");
		res.getContents().add(rule);
		res.save(null);
	}
	
	/**
	 * Returns the objects matching the specified context-free OCL query
	 * 
	 * @param contextFreeOclQuery
	 *            context-free OCL query
	 * @param graph
	 *            {@link EmfGraph} the query is executed on
	 * @return
	 */
	public static Collection<? extends EObject> getOCLQueryResults(String contextFreeOclQuery,
			EmfGraph graph) {
		OCL ocl = org.eclipse.ocl.ecore.OCL.newInstance();
		
		Condition oclQueryCondition;
		try {
			oclQueryCondition = new BooleanOCLCondition<EClassifier, EClass, EObject>(
					ocl.getEnvironment(), contextFreeOclQuery, null);
		} catch (ParserException e) {
			e.printStackTrace();
			throw new AssertionError("error parsing OCL query!   " + contextFreeOclQuery);
		}
		
		WHERE wr = new WHERE((EObjectCondition) oclQueryCondition);
		FROM fm = new FROM(graph.geteObjects());
		SELECT st = new SELECT(fm, wr);
		
		IQueryResult result = st.execute();
		return result.getEObjects();
	}
	
	/**
	 * Return the first element matched by the context-free OCL query
	 * 
	 * @param contextFreeOclQuery
	 * @param graph
	 * @return
	 */
	public static EObject getFirstElementFromOCLQueryResult(String contextFreeOclQuery,
			EmfGraph graph) {
		OCL ocl = org.eclipse.ocl.ecore.OCL.newInstance();
		
		Condition oclQueryCondition;
		
		try {
			oclQueryCondition = new BooleanOCLCondition<EClassifier, EClass, EObject>(
					ocl.getEnvironment(), contextFreeOclQuery, null);
		} catch (ParserException e) {
			e.printStackTrace();
			throw new AssertionError("error parsing OCL query!   " + contextFreeOclQuery);
		}
		
		WHERE wr = new WHERE((EObjectCondition) oclQueryCondition);
		FROM fm = new FROM(graph.geteObjects());
		SELECT st = new SELECT(fm, wr);
		
		IQueryResult result = st.execute();
		if (result.size() == 0) {
			return null;
		}
		return result.getEObjects().toArray(new EObject[1])[0];
	}
	
	/**
	 * get the {@link EmfGraph}'s first root
	 * 
	 * @param graph
	 * @return
	 */
	public static EObject getGraphRoot(EmfGraph graph) {
		return graph.getRootObjects().toArray(new EObject[1])[0];
	}
	
	public static void printCollection(Collection<? extends EObject> coll) {
		System.out.println("-------");
		for (EObject eo : coll) {
			System.out.println(eo);
		}
		System.out.println("-------");
	}
	
	public static void printParameterMappings(RuleApplicationImpl ra) {
		for (Parameter p : ra.getMatch().getParameterValues().keySet()) {
			System.out.println(p.getName() + "\t-> " + ra.getMatch().getParameterValues().get(p));
		}
		for (Parameter p : ra.getComatch().getParameterValues().keySet()) {
			System.out.println(p.getName() + "\t<- " + ra.getComatch().getParameterValues().get(p));
		}
	}
	
	public static Map<Parameter, Object> createParameterMapping(Map<String, Object> mapping,
			Rule rule) {
		Map<Parameter, Object> pMapping = new HashMap<Parameter, Object>();
		for (Parameter param : rule.getParameters()) {
			if (mapping.get(param.getName()) != null) {
				pMapping.put(param, mapping.get(param.getName()));
			}
		}
		return pMapping;
	}
	
	public static void printParameterMappings(UnitApplicationImpl ua) {
		for (Parameter p : ua.getParameterValues().keySet()) {
			System.out.println(p.getName() + "\t : " + ua.getParameterValues().get(p));
		}
		
	}
	
}