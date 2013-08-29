package io.catalyze.sdk.android;

/**
 * An action is anything that is guaranteed to be executable and thread safe.
 *
 * @author mvolkhart
 */
public interface Action {

    // TODO implement versions that take an OnCompleteListener and an OnFailListener

    /**
     * Performs the responsibilities of this action.
     */
    void execute();
}