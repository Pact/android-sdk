package io.catalyze.sdk.android.user;

/**
 * Class representing a health plan at a given institution. A CatalyzeUser can have any number of
 * health plans from a variety of different institutions.
 */
public class HealthPlan implements Comparable<HealthPlan> {

    private String institutionsId;
    private String groupId;
    private String groupName;
    private String memberId;
    private String type;

    public HealthPlan() { }

    /**
     * @return the ID of the institution where this health plan is from.
     */
    public String getInstitutionsId() {
        return institutionsId;
    }

    /**
     * Set the ID of the institution where this health plan is from.
     * @param institutionsId
     */
    public void setInstitutionsId(String institutionsId) {
        this.institutionsId = institutionsId;
    }

    /**
     * @return the group ID.
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Set the group ID.
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the group name.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Set the gorup name.
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the member ID.
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * Set the member ID.
     * @param memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(HealthPlan other) {
        String fullString = getInstitutionsId() + getGroupId() + getGroupName() +
                getMemberId() + getType();
        String otherFullString = other.getInstitutionsId() + other.getGroupId() +
                other.getGroupName() + other.getMemberId() + other.getType();
        return fullString.compareTo(otherFullString);
    }

}
