package aima.core.environment.map;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.EnvironmentViewNotifier;
import aima.core.agent.Percept;
import aima.core.agent.State;
import aima.core.agent.impl.DynamicPercept;
import aima.core.agent.impl.DynamicState;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SimpleProblemSolvingAgent;
import application.Messages;

/**
 * @author Ciaran O'Reilly
 * 
 */
public class MapAgent extends SimpleProblemSolvingAgent {
	private Map map = null;

	private EnvironmentViewNotifier notifier = null;

	private DynamicState state = new DynamicState();

	private Search search = null;

	private String[] goalTests = null;

	private int goalTestPos = 0;

	public MapAgent(Map map, EnvironmentViewNotifier notifier, Search search) {
		this.map = map;
		this.notifier = notifier;
		this.search = search;
	}

	public MapAgent(Map map, EnvironmentViewNotifier notifier, Search search,
			int maxGoalsToFormulate) {
		super(maxGoalsToFormulate);
		this.map = map;
		this.notifier = notifier;
		this.search = search;
	}

	public MapAgent(Map map, EnvironmentViewNotifier notifier, Search search,
			String[] goalTests) {
		super(goalTests.length);
		this.map = map;
		this.notifier = notifier;
		this.search = search;
		this.goalTests = new String[goalTests.length];
		System.arraycopy(goalTests, 0, this.goalTests, 0, goalTests.length);
	}

	//
	// PROTECTED METHODS
	//
	@Override
	protected State updateState(Percept p) {
		DynamicPercept dp = (DynamicPercept) p;

		state.setAttribute(DynAttributeNames.AGENT_LOCATION,
				dp.getAttribute(DynAttributeNames.PERCEPT_IN));

		return state;
	}

	@Override
	protected Object formulateGoal() {
		Object goal = null;
		if (null == goalTests) {
			goal = map.randomlyGenerateDestination();
		} else {
			goal = goalTests[goalTestPos];
			goalTestPos++;
		}
		notifier.notifyViews(Messages.getMessages().getString("start_in") + ": "
				+ state.getAttribute(DynAttributeNames.AGENT_LOCATION)
				+ ", " + Messages.getMessages().getString("goal_in")+ ": " + goal);

		return goal;
	}

	@Override
	protected Problem formulateProblem(Object goal) {
		return new BidirectionalMapProblem(map,
				(String) state.getAttribute(DynAttributeNames.AGENT_LOCATION),
				(String) goal);
	}

	@Override
	protected List<Action> search(Problem problem) {
		List<Action> actions = new ArrayList<Action>();
		try {
			List<Action> sactions = search.search(problem);
			for (Action action : sactions) {
				actions.add(action);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return actions;
	}

	@Override
	protected void notifyViewOfMetrics() {
		Set<String> keys = search.getMetrics().keySet();
		notifier.notifyViews("");
		for (String key : keys) {
			String traslationOfKey;
			try {
				traslationOfKey = Messages.getMessages().getString("metric_"+key);
			} catch (MissingResourceException mre) {
				traslationOfKey = key;
			} 
			notifier.notifyViews(traslationOfKey + ": " + search.getMetrics().get(key));
		}
	}
}
