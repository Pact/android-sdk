package io.catalyze.sdk.android.user;

public class HealthPlan implements Comparable<HealthPlan> {

    private String institutionsId;
    private String groupId;
    private String groupName;
    private String memberId;
    private String type;

    public HealthPlan() { }

    public String getInstitutionsId() {
        return institutionsId;
    }

    public void setInstitutionsId(String institutionsId) {
        this.institutionsId = institutionsId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

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
