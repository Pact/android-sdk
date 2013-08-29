package io.catalyze.sdk.android;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mvolkhart
 */
public class Pipeline implements Action {

    /**
     * The queue of actions to process.
     */
    private final List<Action> queue = new ArrayList<Action>();

    /**
     * Creates a new Pipeline with an empty queue.
     */
    public Pipeline() {
        // Do nothing
    }

    /**
     * Creates a new Pipeline and populates the queue with the specified actions.
     *
     * @param actions to add to the queue
     */
    public Pipeline(Collection<Action> actions) {
        queue.addAll(ImmutableList.copyOf(actions));
    }

    /**
     * Creates a new Pipeline and populates the queue with the specified actions.
     *
     * @param actions to add to the queue
     */
    public Pipeline(Iterable<Action> actions) {
        queue.addAll(ImmutableList.copyOf(actions));
    }

    /**
     * Adds a single action to this Pipeline's queue.
     *
     * @param action to add to the queue
     * @return this pipeline instance
     */
    public Pipeline register(Action action) {
        queue.add(action);
        return this;
    }

    @Override
    public void execute() {
        for (Action action : ImmutableList.copyOf(queue)) {
            action.execute();
        }
    }
}
