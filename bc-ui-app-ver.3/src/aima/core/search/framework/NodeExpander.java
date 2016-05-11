package aima.core.search.framework;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import aima.core.agent.Action;

/**
 * @author Ravi Mohan
 * @author Mike Stampone
 */
public class NodeExpander {
	public static final String METRIC_NODES_EXPANDED = "nodesExpanded";

	public static final String EFFECTIVE_BRANCHING_FACTOR = "effectiveBranchingFactor";
	
	protected Metrics metrics;

	public NodeExpander() {
		metrics = new Metrics();
	}

	/**
	 * Sets the nodes expanded metric to zero.
	 */
	public void clearInstrumentation() {
		metrics.set(METRIC_NODES_EXPANDED, 0);
		metrics.set(EFFECTIVE_BRANCHING_FACTOR, 0);
	}

	/**
	 * Returns the number of nodes expanded so far.
	 * 
	 * @return the number of nodes expanded so far.
	 */
	public int getNodesExpanded() {
		return metrics.getInt(METRIC_NODES_EXPANDED);
	}

	public double getEffectiveBranchingFactor() {
		return metrics.getDouble(EFFECTIVE_BRANCHING_FACTOR);
	}

	public void setEffectiveBranchingFactor(String d) {
		metrics.set(EFFECTIVE_BRANCHING_FACTOR, d);
	}
	
	/**
	 * Returns all the metrics of the node expander.
	 * 
	 * @return all the metrics of the node expander.
	 */
	public Metrics getMetrics() {
		return metrics;
	}

	/**
	 * Returns the children obtained from expanding the specified node in the
	 * specified problem.
	 * 
	 * @param node
	 *            the node to expand
	 * @param problem
	 *            the problem the specified node is within.
	 * 
	 * @return the children obtained from expanding the specified node in the
	 *         specified problem.
	 */
	public List<Node> expandNode(Node node, Problem problem) {
		List<Node> childNodes = new ArrayList<Node>();

		ActionsFunction actionsFunction = problem.getActionsFunction();
		ResultFunction resultFunction = problem.getResultFunction();
		StepCostFunction stepCostFunction = problem.getStepCostFunction();

		for (Action action : actionsFunction.actions(node.getState())) {
			Object successorState = resultFunction.result(node.getState(),
					action);

			double stepCost = stepCostFunction.c(node.getState(), action,
					successorState);
			childNodes.add(new Node(successorState, node, action, stepCost));
		}
		metrics.set(METRIC_NODES_EXPANDED,
				metrics.getInt(METRIC_NODES_EXPANDED) + 1);		
		
		
		setEffectiveBranchingFactor(String.format("%.3f%n", 
				//Math.pow(getNodesExpanded(), ((double)1/(double)node.getPathFromRoot().size()))
				effectiveBranchingFactor(getNodesExpanded(), node.getPathFromRoot().size())		
		));
		//System.out.println("Hlbka = "+node.getPathFromRoot().size());
		return childNodes;
	}
	
	/**
	 * Calculates the effective branching factor.
	 * Adapted from jerrychong google code archive
	 * http://jerrychong.googlecode.com/svn/trunk/15puzzle/src/search/Node.java (was available 11.5.2016)
	 * 
	 * @param nNodes 	number of nodes
	 * @param depth 	depth where solution was found
	 * @return 	the estimated effective branching factor
	 */
	public static double effectiveBranchingFactor(int nNodes, int depth) {
		double maxError = 0.01; // the maximum error we accept from a solution 
		double delta = 0.01; // how much we change our tested ebf each iteration   
		int signOfError = 0; // the sign of the difference between N+1 and 1+b+b^2+......+b^d
		double b = 0; // search for the optimum b will start from minimum possible b
		while (true) { // search for b starts here
			// compute the expression 1+b+b^2+......+b^d
			double sum = 1;
			for (int i = 1; i < depth + 1; i++) {
				sum += Math.pow(b, (double) i);
			}
			// now the tricky bit, we compute the difference between 
			// N+1 and 1+b+b^2+......+b^d, remember that we should have N+1=1+b+b^2+......+b^d
			double error = sum - (1 + (double) nNodes);
			// save previous sign of error
			int signOfPreviousError = signOfError;
			// determine the new sign of error
			if (error < 0.0) // negative
				signOfError = -1;
			else
				// positive
				signOfError = +1;
			/* if the error calculated above is greater than the maximum acceptable error, then check if sign of error
			   was changed, if so that means loop missed the root that we are looking for, then decrease b by delta and 
			   decrease delta to catch root in next search if sign of error wasn't change then increase 'b' by delta
			   otherwise if error is smaller than the limit return the effective branch factor */
			if (Math.abs(error) > maxError) { // error is big
				if (signOfPreviousError == signOfError
						|| signOfPreviousError == 0) {
					b += delta; // taking another step towards solution
				} else { // change of sign which means that we jumped over the minima
					b -= delta; // go back
					delta /= 2; // take smaller steps
					signOfError = signOfPreviousError; // undo change of sign
				}
			} else
				// error is small, let's return current estimate
				return (b);
		}
	}
}