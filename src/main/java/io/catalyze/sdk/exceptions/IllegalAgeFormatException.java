package io.catalyze.sdk.exceptions;

/**
 * Thrown when there is an attempt to set an age to an illegal value.
 *
 * @see io.catalyze.sdk.external.Person#setAge(int) for format requirements
 */
public class IllegalAgeFormatException extends Exception {

    /**
     * @param age which has an invalid format
     */
    public IllegalAgeFormatException(final int age) {
        super(Integer.toString(age) + " is not a valid age");
    }

}
