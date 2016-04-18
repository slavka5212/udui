package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * Linear Conflict Function adds at least two moves to the 
 * Manhattan Distance Function of the two conflicting tiles,
 * by forcing them to surround one another.
 * The heuristic function adds a cost of 2 moves for each pair of conflicting tiles.
 * 
 * @author Slavka Ivanicova
 * 
 */
public class LinearConflictFunction extends ManhattanHeuristicFunction implements HeuristicFunction {

	@Override
	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {	
			XYLocation loc = board.getLocationOf(i);
			retVal += evaluateLinearConflict(i, loc);
		}
		return super.h(state) + retVal;
	}
	
	/*
	 * Two tiles tj and tk are in a linear conflict if tj and tk are in the same line, 
	 * the goal positions of tj and tk are both in that line, tj is to the right of tk 
	 * and goal position of tj is to the left of the goal position of tk.
	 * 
	 */
	private int evaluateLinearConflict(int i, XYLocation loc) {
		return 0;
	}
	

	
}