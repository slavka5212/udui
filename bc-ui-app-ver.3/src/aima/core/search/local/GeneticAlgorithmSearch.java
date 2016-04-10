package aima.core.search.local;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.QueenAction;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.util.datastructure.XYLocation;

/**
 * Genetic Algorithm Search for NQueensApp
 * 
 * @author Slavka Ivanicova
 */
public class GeneticAlgorithmSearch<A> implements Search {
	
	protected Metrics metrics = new Metrics();
	
	protected int numberOfPopulation;
	protected double mutationProbability;
	protected long timeLimit;

	public GeneticAlgorithmSearch(int numberOfPopulation,
			double mutationProbability, long timeLimit) {	
		this.numberOfPopulation = numberOfPopulation;
		this.mutationProbability = mutationProbability;
		this.timeLimit = timeLimit;
	}

	/**
	 * Returns all the metrics of the genetic algorithm.
	 * 
	 * @return all the metrics of the genetic algorithm.
	 */
	public Metrics getMetrics() {
		return metrics;
	}

	@Override
	public List<Action> search(Problem p) throws Exception {
		ArrayList<Action> result = new ArrayList<Action>();
		int boardSize = ((NQueensBoard) p.getInitialState()).getSize();
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < numberOfPopulation; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(boardSize),
					mutationProbability);
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(
					population, fitnessFunction, fitnessFunction, timeLimit);
			List<Integer> representationList = bestIndividual.getRepresentation();
			for (int i = 0; i < representationList.size(); i++) {
				result.add(new QueenAction("placeQueenAt", new XYLocation(i, representationList.get(i))));
			};
			
			
			System.out.println("Max Time (1 second) Best Individual=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + boardSize);
			System.out.println("# Board Layouts = "
					+ (new BigDecimal(boardSize)).pow(boardSize));
			System.out.println("Fitness         = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Is Goal         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = "
					+ ga.getTimeInMilliseconds() + "ms.");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
}