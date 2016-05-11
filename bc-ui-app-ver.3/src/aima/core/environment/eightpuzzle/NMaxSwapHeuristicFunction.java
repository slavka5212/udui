package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;

/**
 * h - returns the number of steps it would take to solve the problem 
 * if it was possible to swap any tile with the "space".
 * 
 * @author Slavka Ivanicova
 * 
 */
public class NMaxSwapHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int[] tempBoard = board.getState();
		int[] solution = new int[] {0,1,2,3,4,5,6,7,8};
		int returnH = 0;
		while (true)  {
			for (int i = 8; i > -1; i--) {
				if (tempBoard[i] == 0 && tempBoard[i] != solution[i]) {
					// swap space with the right tile
					swap(tempBoard, i, getPositionOf(tempBoard, solution[i]));
					returnH++;
					break;
				}
				else if (tempBoard[i] != solution[i]) {
					// swap any tile with the space
					swap(tempBoard, i, getPositionOf(tempBoard, 0));
					returnH++;
					break;
				}
			} 
			int count = 0;
			for(int k = 0; k <= 8; k++) {
				if(tempBoard[k] == solution[k]) {
					count++;
				}
			}
			if(count == 9) break;
		}
		return returnH;
	}

	private int getPositionOf(int[] state, int val) {
		int retVal = -1;
		for (int i = 0; i < 9; i++) {
			if (state[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}
	
	private void swap(int[] array, int indexA, int indexB) {
		int temp = array[indexA];
		array[indexA] = array[indexB];
		array[indexB] = temp;
	}
}