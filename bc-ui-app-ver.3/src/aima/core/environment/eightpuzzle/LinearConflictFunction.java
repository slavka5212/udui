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
		// rows
		for (int i = 0; i < 3; i++) {	
			int pom = 0;
			// columns
			for (int j = 0; j < 3; j++) {
				int value = board.getValueAt(new XYLocation(j,i));
				// the value should be in the row but in the different column 
				if (i * 3 <= value && value < (i+1) * 3 && value != i * 3 + j) 
				pom++; 
			} 
			// the are at least two tiles in a linear conflict
			if (pom > 1) retVal += pom;
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