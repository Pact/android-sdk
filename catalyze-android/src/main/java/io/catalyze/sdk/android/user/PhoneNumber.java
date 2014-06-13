package io.catalyze.sdk.android.user;

/**
 * A CatalyzeUser can have a variety of phone number associated with their account including their
 * home, mobile, work, and other phone. The preferred field is a string representation of one of
 * the other four values, i.e. preferred can be 'home', 'work', 'mobile', or 'other'.
 */
public class PhoneNumber implements Comparable<PhoneNumber> {

    private String home;
    private String mobile;
    private String work;
    private String other;
    private String preferred;

    public PhoneNumber() { }

    /**
     * @return the home phone.
     */
    public String getHome() {
        return home;
    }

    /**
     * Set the home phone.
     * @param home
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * @return the mobile phone.
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Set the mobile phone.
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the work phone.
     */
    public String getWork() {
        return work;
    }

    /**
     * Set the work phone.
     * @param work
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * @return the other phone.
     */
    public String getOther() {
        return other;
    }

    /**
     * Set the other phone.
     * @param other
     */
    public void setOther(String other) {
        this.other = other;
    }

    /**
     * @return the preferred phone. Should be one of 'home', 'mobile', 'work', or 'other'.
     */
    public String getPreferred() {
        return preferred;
    }

    /**
     * Set the preferred phone. Should be one of 'home', 'mobile', 'work', or 'other'.
     * @param preferred
     */
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
