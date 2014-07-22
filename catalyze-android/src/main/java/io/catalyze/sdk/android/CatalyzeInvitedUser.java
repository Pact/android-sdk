package io.catalyze.sdk.android;

/***
 * A subclass of CatalyzeUser that has an inviteCode field. This is used in conjunction with
 * invite-only applications. These can be setup on the dashboard. A valid inviteCode must be
 * specified for the user to be created. After initial sign up the user is no longer a
 * CatalyzeInvitedUser but a regular CatalyzeUser and this class should not be used.
 */
public class CatalyzeInvitedUser extends CatalyzeUser {

    private String inviteCode;

	protected CatalyzeInvitedUser(String inviteCode) {
        this.setInviteCode(inviteCode);
    }

    /**
     * Used internally.
     * @return inviteCode
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * Used internally.
     * @param inviteCode
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
