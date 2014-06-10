package io.catalyze.sdk.android.user;

public class PhoneNumber implements Comparable<PhoneNumber> {

    private String home;
    private String mobile;
    private String work;
    private String other;
    private String preferred;

    public PhoneNumber() { }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getPreferred() {
        return preferred;
    }

    public void setPreferred(String preferred) {
        this.preferred = preferred;
    }

    @Override
    public int compareTo(PhoneNumber other) {
        String fullString = getHome() + getMobile() + getWork() +
                getOther() + getPreferred();
        String otherFullString = other.getHome() + other.getMobile() +
                other.getWork() + other.getOther() + other.getPreferred();
        return fullString.compareTo(otherFullString);
    }

}
