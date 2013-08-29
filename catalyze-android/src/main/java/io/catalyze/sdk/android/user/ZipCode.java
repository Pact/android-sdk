package io.catalyze.sdk.android.user;

/**
 * USPS ZIP Code. Format is #####. When printing this, use toString().
 */
public class ZipCode {

    private String zip;

    @Deprecated
    public ZipCode(){};

    /**
     * Will allow nulls, otherwise only accepts 5 digit numeric string.
     *
     * @param zip 5 digit numeric string, or null.
     */
    public ZipCode(String zip) {
        setZip(zip);
    }

    /**
     * Will allow nulls, otherwise only accepts 5 digit numeric string.
     *
     * @param zip 5 digit numeric string, or null.
     */
    public void setZip(final String zip) {
        if (zip != null && !zip.matches("\\d{5}")) {
            throw new IllegalZipCodeFormatException(zip);
        }
        this.zip = zip;
    }

    /**
     * Will return 5-character ZIP code. If zip is null, will return null.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.zip;
    }

}
