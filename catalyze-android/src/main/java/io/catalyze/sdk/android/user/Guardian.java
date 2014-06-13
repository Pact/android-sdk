package io.catalyze.sdk.android.user;

/**
 * On the Catalyze API, a guardian has special permissions in regards to the user that it is
 * associated with. A guardian may be a parent, grandparent, or even someone with no relation. The
 * only restriction is that this person have a CatalyzeUser on the Catalyze API. A guardian has
 * access to all non PHI data for a user and even PHI data if the viewPhi flag is set to true.
 */
public class Guardian implements Comparable<Guardian> {

    private String guardianId;
    private String relationship;
    private boolean viewPhi;

    public Guardian() { }

    /**
     * @return the unique identifier of the guardian's user.
     */
    public String getGuardianId() {
        return guardianId;
    }

    /**
     * Set the unique identifier of the guardian's user.
     * @param guardianId
     */
    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }

    /**
     * @return the relationship.
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Set the relationship.
     * @param relationship
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * @return whether or not the guardian can see the user's PHI data.
     */
    public boolean isViewPhi() {
        return viewPhi;
    }

    /**
     * Set whether or not the guardian has permission to view the user's PHI data.
     * @param viewPhi
     */
    public void setViewPhi(boolean viewPhi) {
        this.viewPhi = viewPhi;
    }

    @Override
    public int compareTo(Guardian other) {
        String fullString = getGuardianId() + getRelationship() +
                Boolean.toString(isViewPhi());
        String otherFullString = other.getGuardianId() + other.getRelationship() +
                Boolean.toString(other.isViewPhi());
        return fullString.compareTo(otherFullString);
    }

}
