package io.catalyze.sdk.android.user;

public class Guardian implements Comparable<Guardian> {

    private String guardianId;
    private String relationship;
    private boolean viewPhi;

    public Guardian() { }

    public String getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public boolean isViewPhi() {
        return viewPhi;
    }

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
