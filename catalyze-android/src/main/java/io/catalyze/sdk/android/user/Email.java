package io.catalyze.sdk.android.user;

public class Email implements Comparable<Email> {

    private String primary;
    private String secondary;
    private String work;
    private String other;

    public Email() { }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getOther() {
        return other;
    }

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
