package io.catalyze.sdk.android.user;

/**
 * A CatalyzeUser has many emails that are all captured in this class. A CatalyzeUser only has one
 * instance of the Email class associated with it at any time. All email communications with the
 * CatalyzeUser are done through the primary email.
 */
public class Email implements Comparable<Email> {

    private String primary;
    private String secondary;
    private String work;
    private String other;

    public Email() { }

    /**
     * @return primary email.
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * Set the primary email.
     * @param primary
     */
    public void setPrimary(String primary) {
        this.primary = primary;
    }

    /**
     * @return secondary email.
     */
    public String getSecondary() {
        return secondary;
    }

    /**
     * Set the secondary email.
     * @param secondary
     */
    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    /**
     * @return work email.
     */
    public String getWork() {
        return work;
    }

    /**
     * Set the work email.
     * @param work
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * @return other email.
     */
    public String getOther() {
        return other;
    }

    /**
     * Set the other email.
     * @param other
     */
    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public int compareTo(Email other) {
        String fullString = getPrimary() + getSecondary() + getWork() +
                getOther();
        String otherFullString = other.getPrimary() + other.getSecondary() +
                other.getWork() + other.getOther();
        return fullString.compareTo(otherFullString);
    }

}
