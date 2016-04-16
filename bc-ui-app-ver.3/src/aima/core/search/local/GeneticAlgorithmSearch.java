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
import application.Messages;

/**
 * Genetic Algorithm Search for NQueensApp
 * 
 * @author Slavka Ivanicova
 */
public class GeneticAlgorithmSearch<A> implements Search {
	
	public static final String METRIC_FITNESS = "fitness";
	public static final String METRIC_IS_GOAL = "isGoal";
	public static final String METRIC_POPULATION_SIZE = "populationSize";
	public static final String METRIC_ITERATIONS = "itertions";
	public static final String METRIC_TOOK = "took";
	
	protected Metrics metrics = new Metrics();
	
	protected int numberOfPopulation;
	protected double mutationProbability;
	protected long timeLimit;

	public GeneticAlgorithmSearch(int numberOfPopulation,
			double mutationProbability, long timeLimit) {	
		metrics = new Metrics();
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
			
			NQueenGeneticAlgorithm<Integer> ga = new NQueenGeneticAlgorithm<Integer>(
					boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(boardSize),
					mutationProbability);
			List<Individual<Integer>> listOfIndividuals = ga.geneticAlgorithmList(
					population, fitnessFunction, fitnessFunction, timeLimit);
			
			for (Individual<Integer> individual : listOfIndividuals) {
				List<Integer> representationList = individual.getRepresentation();
				List<XYLocation> newActionsList = new ArrayList<XYLocation>();
				for (int i = 0; i < representationList.size(); i++) {
					newActionsList.add(new XYLocation(i, representationList.get(i)));
				};
				result.add(new QueenAction("moveQueenTo", newActionsList));
			}			
			Individual<Integer> bestIndividual = listOfIndividuals.get(listOfIndividuals.size()-1);		
			// System.out.println("Board Size      = " + boardSize);
			// System.out.println("# Board Layouts = " + (new BigDecimal(boardSize)).pow(boardSize));
			metrics.set(METRIC_FITNESS, fitnessFunction.getValue(bestIndividual));	
			metrics.set(METRIC_IS_GOAL, Messages.getMessages().getString(Boolean.toString(fitnessFunction.isGoalState(bestIndividual))));	
			metrics.set(METRIC_POPULATION_SIZE, ga.getPopulationSize());	
			metrics.set(METRIC_ITERATIONS, ga.getIterations());	
			metrics.set(METRIC_TOOK, Long.toString(ga.getTimeInMilliseconds()) + "ms.");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
}