package io.catalyze.sdk.android.user;

/**
 * Breaks out the name for a CatalyzeUser into its individual pieces. This allows for better control
 * over displaying the user's name or parts of their name.
 */
public class Name implements Comparable<Name> {

    private String prefix;
    private String firstName;
    private String middleName;
    private String lastName;
    private String maidenName;
    private String suffix;

    public Name() { }

    /**
     * @return the prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Set the prefix.
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name.
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middle name.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Set the middle name.
     * @param middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name.
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the maiden name.
     */
    public String getMaidenName() {
        return maidenName;
    }

    /**
     * Set the maiden name.
     * @param maidenName
     */
    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    /**
     * @return the suffix.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Set the suffix.
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public int compareTo(Name other) {
        String fullString = getPrefix() + getFirstName() + getMiddleName() +
                getLastName() + getMaidenName() + getSuffix();
        String otherFullString = other.getPrefix() + other.getFirstName() +
                other.getMiddleName() + other.getLastName() +
                other.getMaidenName() + other.getSuffix();
        return fullString.compareTo(otherFullString);
    }

}
