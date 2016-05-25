package aima.core.environment.eightpuzzle;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * Nilsson's Sequence Score is a combination of the Manhattan Distance Function P(n) and
 * the sequence score S(n) obtained by checking around the non-central squares in turn,
 * allotting 2 for every tile not followed by its proper successor 
 * and 1 in case that the center is not empty.
 * h(n) = P(n) + 3S(n)
 * 
 * @author Slavka Ivanicova
 * 
 */
public class NilssonsSequenceScoreFunction extends ManhattanHeuristicFunction implements HeuristicFunction {

	@Override
	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int[] a = new int[] {1,2,3,8,0,4,7,6,5};
		EightPuzzleBoard goalSquare = new EightPuzzleBoard(a);
		int score = 0;
		// for each tile
		for (int i = 1; i <= 8; i++) {
			int first = board.getValueAt(goalSquare.getLocationOf(i));
			int second = board.getValueAt(goalSquare.getLocationOf((i+1) % 8));
			if ((first+1) != second) { 
				score += 2;
			}  
		}
		// center is not empty 
		if (board.getValueAt(goalSquare.getLocationOf(0)) != 0) {
			score += 1;
		}
		return super.h(state) + (3*score);
	}
	
}