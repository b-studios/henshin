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
package org.eclipse.emf.henshin.statespace.external.prism;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.tuples.Tuple;
import org.eclipse.emf.henshin.statespace.tuples.TupleUtil;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;
import org.eclipse.emf.henshin.statespace.validation.Validator;

/**
 * PRISM utils.
 * @author Christian Krause
 */
public class PRISMUtil {
	
	/**
	 * Data class for ranges (and constants).
	 */
	public static class Range {
		
		// Minimum, step, maximum values:
		public double min = 0, step = 0, max = 0;
		
		public Range(double min, double step, double max) {
			this.min = min;
			this.step = step;
			this.max = max;
		}
		
		public Range(double constant) {
			this(constant,0,constant);
		}
		
		public Range(String value) throws ParseException {
			NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
			String[] fields = value.split(":");
			if (fields.length==1) {
				min = max = format.parse(fields[0]).doubleValue();
			}
			else if (fields.length==2) {
				min = format.parse(fields[0]).doubleValue();				
				max = format.parse(fields[1]).doubleValue();				
			}
			else if (fields.length==3) {
				min = format.parse(fields[0]).doubleValue();
				step = format.parse(fields[1]).doubleValue();
				max = format.parse(fields[2]).doubleValue();
			}
			else {
				throw new ParseException("Error parsing rate",0);
			}
		}
		
		public boolean isConstant() {
			return min==max;
		}
		
		public String toString() {
			if (isConstant()) {
				return String.valueOf(min);
			} else if (step<=0) {
				return min + ":" + max;
			} else {
				return min + ":" + step + ":" + max;
			}
		}

		public static boolean isRange(String value) {
			try {
				Range range = new Range(value);
				return !range.isConstant();
			} catch (ParseException e) {}
			return false;
		}
		
	}

	// Properties key for PRISM path.
	public static final String PRISM_PATH_KEY = "prismPath";
	
	// Properties key for PRISM arguments.
	public static final String PRISM_ARGS_KEY = "prismArgs";
	
	// Properties key for PRISM experiment parameter.
	public static final String PRISM_EXPERIMENT_KEY = "prismExperiment";

	/**
	 * Invoke PRISM on a state space.
	 * @param stateSpace State space.
	 * @param args Arguments.
	 * @param monitor Monitor.
	 * @return Created process.
	 * @throws Exception On errors.
	 */
	protected static Process invokePRISM(StateSpace stateSpace, File modelFile, File formulaFile, 
			String[] args, Map<String,String> constants, boolean allowExperiments, IProgressMonitor monitor) throws Exception {
				
		// Get the executable, path and arguments.
		File path = getPRISMPath(stateSpace);
		String baseArgs = getPRISMArgs(stateSpace);
		
		// Create the command.
		List<String> command = new ArrayList<String>();
		command.add(path!=null ? new File(path.getAbsolutePath()+File.separator+"prism").getAbsolutePath() : "prism");
		if (modelFile.getName().endsWith(".tra")) {
			command.add("-importtrans");
			command.add(modelFile.getAbsolutePath());
			command.add("-importstates");
			command.add(modelFile.getAbsolutePath().replaceAll(".tra", ".sta"));			
		} else {
			command.add(modelFile.getAbsolutePath());			
		}
		
		if (formulaFile!=null) {
			command.add(formulaFile.getAbsolutePath());
		}
		if (baseArgs!=null) {
			for (String arg : baseArgs.split(" ")) {
				command.add(arg.trim());
			}
		}
		if (args!=null) {
			for (String arg : args) {
				command.add(arg.trim());
			}
		}
		
		// Now add the constants arguments:
		String cons = "";
		if (constants!=null && !constants.isEmpty()) {
			boolean first = true;
			for (Entry<String,String> e : constants.entrySet()) {
				if (!first) {
					cons = cons + ",";
				}
				if (!Range.isRange(e.getValue())) {
					continue;
				}
				cons = cons + e.getKey() + "=" + e.getValue();
				first = false;
			}
			
		}
		if (cons.length()>0) {
			command.add("-const");
			command.add(cons);
		}
		
		// Now we can invoke the PRISM tool:
		System.out.println(command);
		return Runtime.getRuntime().exec(command.toArray(new String[] {}), null, path);
		
	}
	
	/*
	 * Expand labels.
	 */
	public static String expandLabels(String template, StateSpaceIndex index, List<Tuple> tuples, IProgressMonitor monitor) throws Exception {

		// Find out how many sections need to be replaced:
		int sections = -1;
		String dummy1 = template;
		String dummy2 = template;
		do {
			dummy1 = dummy2;
			dummy2 = dummy2.replaceFirst("<<<", "xxx");
			sections++;
		} while (!dummy1.equals(dummy2));
		
		// Now do the expansion:
		monitor.beginTask("Expanding labels...", sections);
		for (int i=0; i<sections; i++) {
			template = doExpandLabels(template, index, tuples, new SubProgressMonitor(monitor,1));
		}
		monitor.done();
		return template;

	}

	/*
	 * Expand the first occurrence of <<<...>>>.
	 */
	private static String doExpandLabels(String template, StateSpaceIndex index, List<Tuple> tuples, IProgressMonitor monitor) throws Exception {
		
		// Find <<< ... >>>
		int start = template.indexOf("<<<");
		if (start<0) return template;
		int end = template.indexOf(">>>", start);
		if (end<0) end = template.length();
		else end = end + 3;

		// Get the type: <<<TYPE ... >>>
		String toReplace = template.substring(start, end);
		String type = "";
		for (int i=3; i<toReplace.length(); i++) {
			if (Character.isWhitespace(toReplace.charAt(i))) {
				break;
			}
			type = type + toReplace.charAt(i);
		}

		// Find the state validator:
		StateValidator validator = null;
		for (Validator v : StateSpacePlugin.INSTANCE.getValidators().values()) {
			if (v.getName().startsWith(type) && v instanceof StateValidator) {
				validator = (StateValidator) v;
				break;
			}
		}
		if (validator==null) {
			throw new RuntimeException("Unknown validator \"" + type + "\"");
		}

		// Find all states which satisfy the property:
		String theProperty = toReplace.substring(3+type.length(), toReplace.length()-3).trim();
		validator.setStateSpaceIndex(index);
		validator.setProperty(theProperty);
		String result = "";
		IProgressMonitor nullMonitor = new NullProgressMonitor();
		monitor.beginTask("Expanding labels...", index.getStateSpace().getStates().size());
		for (State state : index.getStateSpace().getStates()) {
			if (validator.validate(state, nullMonitor).isValid()) {
				if (result.length()>0) result = result + " | ";
				result = result + PRISMUtil.getPRISMState(tuples.get(state.getIndex()), false);
			}
			monitor.worked(1);
			if (monitor.isCanceled()) {
				return template;
			}
		}
		if (result.length()==0) {
			result = "false";
		}

		// Replace the text with the result:
		String expanded = template.substring(0,start) + result + template.substring(end);
		
		// Done:
		monitor.done();
		return expanded;
	}

	/**
	 * Get the properties key for rule rates.
	 */
	public static String getRateKey(Rule rule) {
		return "rate" + capitalize(removeWhiteSpace(rule.getName()));
	}

	/**
	 * Get the rate of a rule, as specified in the state space properties.
	 */
	public static String getRate(StateSpace stateSpace, Rule rule) {
		String rate = stateSpace.getProperties().get(getRateKey(rule));
		if (rate!=null && rate.trim().length()==0) {
			rate = null;
		}
		return rate;
	}

	/**
	 * Get the rate of a rule, as specified in the state space properties.
	 */
	public static Range getRateAsRange(StateSpace stateSpace, Rule rule) throws ParseException {
		String value = getRate(stateSpace, rule);
		return (value!=null) ? new Range(value) : null;
	}
	
	/**
	 * Get all rates for a state space.
	 * @param stateSpace The state space.
	 * @param force Whether the rate must be specified.
	 * @return Map associating constants to values.
	 */
	public static Map<String,String> getAllRates(StateSpace stateSpace, boolean force) {
		Map<String,String> map = new HashMap<String,String>();
		for (Rule rule : stateSpace.getRules()) {
			String key = getRateKey(rule);
			String value = getRate(stateSpace, rule);
			if (value!=null) {
				map.put(key, value);
			} else if (force) {
				throw new RuntimeException("State space property \"" + key + "\" must be specified.");
			}
		}
		return map;
	}

	/**
	 * Get the properties key for rule probabilities.
	 */
	public static String getProbKey(Rule rule, int index) {
		return "prob" + capitalize(removeWhiteSpace(rule.getName())) + (index+1);
	}
	
	/**
	 * Get the probability of a rule, as specified in the state space properties.
	 */
	public static String getProb(StateSpace stateSpace, Rule rule, int index) {
		String prob = stateSpace.getProperties().get(getProbKey(rule, index));
		if (prob!=null && prob.trim().length()==0) {
			prob = null;
		}
		return prob;
	}

	/**
	 * Get the probability of a rule, as specified in the state space properties.
	 */
	public static Range getProbAsRange(StateSpace stateSpace, Rule rule, int index) throws ParseException {
		String value = getProb(stateSpace, rule, index);
		return (value!=null) ? new Range(value) : null;
	}

	/**
	 * Get all probabilities for a state space.
	 * @param stateSpace The state space.
	 * @param force Whether the probability must be specified.
	 * @return Map associating constants to values.
	 */
	public static Map<String,String> getAllProbs(StateSpace stateSpace, boolean force) {
		Map<String,String> map = new HashMap<String,String>();
		Map<String, List<Rule>> probRules = getProbabilisticRules(stateSpace);
		for (String ruleName : probRules.keySet()) {
			List<Rule> rules = probRules.get(ruleName);
			if (rules.size()>1) {
				for (int i=0; i<rules.size(); i++) {
					String key = getProbKey(rules.get(i), i);
					String value = getProb(stateSpace, rules.get(i), i);
					if (value!=null && value.trim().length()>0) {
						map.put(key, value);
					} else if (force) {
						throw new RuntimeException("State space property \"" + key + "\" must be specified.");
					}				
				}
			}
		}		
		return map;
	}
	
	/**
	 * Partition the rules of a state space into probabilistic rules, based on their names.
	 * That is, all rules with the same name become part of one probabilistic rule.
	 * The derived probabilistic rules are meaningful in the context of an MDP.
	 * @param stateSpace State space.
	 * @return Probabilistic rules.
	 */
	public static Map<String,List<Rule>> getProbabilisticRules(StateSpace stateSpace) {
		Map<String,List<Rule>> probRules = new LinkedHashMap<String,List<Rule>>();
		for (Rule rule : stateSpace.getRules()) {
			List<Rule> rules = probRules.get(rule.getName());
			if (rules==null) {
				rules = new ArrayList<Rule>();
				probRules.put(rule.getName(), rules);
			}
			rules.add(rule);
		}
		return probRules;
	}

	/**
	 * Get the PRISM path property.
	 * @param stateSpace State space.
	 * @return PRISM path property (can be <code>null</code>)
	 */
	public static File getPRISMPath(StateSpace stateSpace) {
		String path = stateSpace.getProperties().get(PRISM_PATH_KEY);
		if (path!=null && path.trim().length()>0) {
			return new File(path.trim());
		}
		return null;
	}

	/**
	 * Get the PRISM arguments property.
	 * @param stateSpace State space.
	 * @return PRISM arguments property (can be <code>null</code>)
	 */
	public static String getPRISMArgs(StateSpace stateSpace) {
		return stateSpace.getProperties().get(PRISM_ARGS_KEY);
	}

	/**
	 * Get the PRISM experiment parameter.
	 * @param stateSpace State space.
	 * @return PRISM experiment parameter (can be <code>null</code>)
	 */
	public static String getPRISMExperiment(StateSpace stateSpace) {
		return stateSpace.getProperties().get(PRISM_EXPERIMENT_KEY);
	}
	
	public static String[] getVariables(int count) {
		if (count==0) {
			return new String[0];
		}
		else if (count==1) {
			return new String[] { "s" };
		}
		else {
			String[] vars = new String[count];
			for (int i=0; i<count; i++) {
				vars[i] = "s" + (i+1);
			}
			return vars;
		}
	}
	
	public static String getVariableDeclarations(List<Tuple> tuples, boolean explicit) {
		if (tuples.isEmpty()) {
			return "";
		}
		String[] vars = getVariables(tuples.get(0).size());
		if (explicit) {
			String v = "(";
			for (int i=0; i<vars.length; i++) {
				v = v + vars[i];
				if (i<vars.length-1) {
					v = v + ",";
				}
			}
			return v + ")";
		} else {
			List<Tuple> ranges = TupleUtil.getRanges(tuples);
			String v = "";
			for (int i=0; i<vars.length; i++) {
				v = v + "\t" + vars[i] + " : [" + 
						ranges.get(0).data()[i] + ".." + ranges.get(1).data()[i] + "];\n";
			}
			return v;
		}
	}
	
	/*
	 * Capitalize a string.
	 */
	private static String capitalize(String string) {
		if (string==null || string.length()==0) return string;
		String first = string.substring(0,1).toUpperCase();
		if (string.length()==0) return first;
		else return first + string.substring(1);
	}
	
	/*
	 * Remove white space from a string.
	 */
	private static String removeWhiteSpace(String string) {
		string = string.replaceAll(" ", "_");
		string = string.replaceAll("\t", "_");
		string = string.replaceAll("\n", "_");
		return string;
	}

	public static String getModelHeader(String modelType) {
		String h = "// " + modelType.toUpperCase() + " model generated by Henshin on " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "\n\n";
		h = h + modelType.toLowerCase() + "\n\n";
		return h;
	}
	
	public static String getPRISMState(Tuple t, boolean successor) {
		if (t.size()==0) {
			return "true";
		}
		String s = "";
		String[] vars = getVariables(t.size());
		for (int i=0; i<vars.length; i++) {
			s = s + "(" + vars[i];
			if (successor) s = s + "'";
			s = s + "=" + t.data()[i] + ")";
			if (i<vars.length-1) s = s + " & ";
		}
		return s;
	}
	
	public static String getPRISMStates(List<Tuple> tuples) {
		String r = "";
		int count = tuples.size();
		for (int i=0; i<count; i++) {
			r = r + getPRISMState(tuples.get(i),false);
			if (i<count-1) r = r + " | ";
		}
		return r;
	}
	
}
