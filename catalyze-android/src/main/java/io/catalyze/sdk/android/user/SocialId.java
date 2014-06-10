package io.catalyze.sdk.android.user;

public class SocialId implements Comparable<SocialId> {

    private String network;
    private String handle;

    public SocialId() { }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    @Override
    public int compareTo(SocialId other) {
        String fullString = getNetwork() + getHandle();
        String otherFullString = other.getNetwork() + other.getHandle();
        return fullString.compareTo(otherFullString);
    }

}
