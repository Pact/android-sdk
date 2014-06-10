package io.catalyze.sdk.android.user;

public class Name implements Comparable<Name> {

    private String prefix;
    private String firstName;
    private String middleName;
    private String lastName;
    private String maidenName;
    private String suffix;

    public Name() { }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getSuffix() {
        return suffix;
    }

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
