package io.catalyze.sdk.android.user;

/**
 * Thrown when there is an attempt to set a ZIP code to an illegal value.
 *
 * @see ZipCode
 */
public class IllegalZipCodeFormatException extends IllegalArgumentException {

    private static final long serialVersionUID = 6367113304959164109L;

    /**
     * @see IllegalArgumentException#IllegalArgumentException(String)
     */
    public IllegalZipCodeFormatException(final String zip) {
        super(zip + " is not a valid ZIP code");
    }

}
