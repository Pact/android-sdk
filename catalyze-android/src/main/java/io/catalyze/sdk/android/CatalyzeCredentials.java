package io.catalyze.sdk.android;

/**
 * Helper class for authenticating with the Catalyze API.
 */
public class CatalyzeCredentials {

    private String username;
    private String password;

    public CatalyzeCredentials() { }

    /**
     * Helper constructor to set the username and password on creation.
     *
     * @param username
     * @param password
     */
    public CatalyzeCredentials(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    /**
     * Returns the username of the user.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user locally. Call CatalyzeUser.update() to
     * update on the backend.
     *
     * @param username
     *            The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the credentials locally.
     *
     * @param password
     *            The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
