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

	@Override
	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int[] tempBoard = board.getState();
		int[] solution = new int[] {0,1,2,3,4,5,6,7,8};
		//for (int a = 0; a < tempBoard.length; a++) System.out.print(tempBoard[a]);
		//System.out.println();
		int returnH = 0;
		while (true)  {
			for (int i = 8; i > -1; i--) {
				if (tempBoard[i] == 0 && tempBoard[i] != solution[i]) {
					// swap space with the right tile
					swap(tempBoard, i, getPositionOf(tempBoard, solution[i]));
					for (int a = 0; a < tempBoard.length; a++) System.out.print(tempBoard[a]);
					System.out.println("SWAP 0");
					returnH++;
					break;
				}
				else if (tempBoard[i] != solution[i]) {
					// swap any tile with the space
					swap(tempBoard, i, getPositionOf(tempBoard, 0));
					for (int a = 0; a < tempBoard.length; a++) System.out.print(tempBoard[a]);
					System.out.println("SWAP ANY");
					returnH++;
					break;
				}
			} 
			for (int a = 0; a < tempBoard.length; a++) System.out.print(tempBoard[a]);
			System.out.println("CYKLUS");
			int count = 0;
			for(int k = 0; k <= 8; k++) {
				if(tempBoard[k] == solution[k]) {
					count++;
				}
			}
			if(count == 9) break;
		}
		System.out.println("H="+returnH);
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
	
	private void swap(int[] state, int indexA, int indexB) {
		int temp = state[indexA];
		state[indexA] = state[indexB];
		state[indexB] = temp;
	}
}