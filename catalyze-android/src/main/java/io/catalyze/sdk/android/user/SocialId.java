package io.catalyze.sdk.android.user;

/**
 * A socialId is a representation of a social media site. A CatalyzeUser can have any number of
 * socialIds and represents their username on that social media site as well as which site it is.
 * For example, network may be 'twitter' while handle may be '@catalyzeio'.
 */
public class SocialId implements Comparable<SocialId> {

    private String network;
    private String handle;

    public SocialId() { }

    /**
     * @return the name of the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Set the name of the network.
     * @param network
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * @return the handle, or identifier, of the user on the given network.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Set the handle, or identifier, of the user on the given network.
     * @param handle
     */
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
