package aima.core.search.local;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import aima.core.search.framework.GoalTest;
import aima.core.search.framework.Metrics;
import aima.core.util.Util;

/**
 * Genetic Algorithm for N Queen problem
 * 
 * @author Slavka Ivanicova
 * 
 * @param <A>
 *            the type of the alphabet used in the representation of the
 *            individuals in the population (this is to provide flexibility in
 *            terms of how a problem can be encoded).
 */
public class NQueenGeneticAlgorithm<A> extends GeneticAlgorithm<A> {
	
	public NQueenGeneticAlgorithm(int individualLength, Set<A> finiteAlphabet, double mutationProbability) {
		super(individualLength, finiteAlphabet, mutationProbability);
	}

	public NQueenGeneticAlgorithm(int individualLength, Set<A> finiteAlphabet, double mutationProbability,
			Random random) {
		super(individualLength, finiteAlphabet, mutationProbability, random);
	}
	
	/**
	 * Returns the best individual in the specified population, according to the
	 * specified FITNESS-FN and goal test.
	 * 
	 * @param population
	 *            a set of individuals
	 * @param fitnessFn
	 *            a function that measures the fitness of an individual
	 * @param goalTest
	 *            test determines whether a given individual is fit enough to
	 *            return.
	 * @param maxTimeMilliseconds
	 *            the maximum time in milliseconds that the algorithm is to run
	 *            for (approximate). Only used if > 0L.
	 * @return list of the individuals representation created in the specified population, 
	 *         according to the specified FITNESS-FN and goal test.         
	 */
	// function GENETIC-ALGORITHM(population, FITNESS-FN) returns an individual
	// inputs: population, a set of individuals
	// FITNESS-FN, a function that measures the fitness of an individual
	public List<Individual<A>> geneticAlgorithmList(Set<Individual<A>> population,
			FitnessFunction<A> fitnessFn, GoalTest goalTest,
			long maxTimeMilliseconds) {
		Individual<A> bestIndividual = null;
		List<Individual<A>> listOfIndividuals = new ArrayList<Individual<A>>();
		
		// Create a local copy of the population to work with
		population = new LinkedHashSet<Individual<A>>(population);
		// Validate the population and setup the instrumentation
		validatePopulation(population);
		clearInstrumentation();
		setPopulationSize(population.size());

		long startTime = System.currentTimeMillis();

		// repeat
		int cnt = 0;
		do {
			bestIndividual = nextGeneration(population, fitnessFn);
			listOfIndividuals.add(bestIndividual);
			
			//System.out.println(bestIndividual.getRepresentation());			
			cnt++;

			// until some individual is fit enough, or enough time has elapsed
			if (maxTimeMilliseconds > 0L) {
				if ((System.currentTimeMillis() - startTime) > maxTimeMilliseconds) {
					break;
				}
			}
		} while (!goalTest.isGoalState(bestIndividual));
		setIterations(cnt);
		setTimeInMilliseconds(System.currentTimeMillis()-startTime);

		// return the list of individuals in population, according to FITNESS-FN
		return listOfIndividuals;
	}

}