package io.catalyze.sdk.internal;

/**
 * Thrown when there is an attempt to set an age to an illegal value.
 */
public class IllegalAgeFormatException extends IllegalArgumentException {

    /**
     * @param age
     * @see IllegalArgumentException#IllegalArgumentException(String)
     */
    public IllegalAgeFormatException(final int age) {
        super(Integer.toString(age) + " is not a valid age");
    }

}
