package io.catalyze.sdk.android;

/**
 * Singleton holder for the currently logged in user's session token. Used internally.
 */
public class CatalyzeSession {

    private static CatalyzeSession sessionInstance;

    protected static CatalyzeSession getInstance() {
        if (sessionInstance == null) {
            synchronized (CatalyzeSession.class) {
                if (sessionInstance == null) {
                    sessionInstance = new CatalyzeSession();
                }
            }
        }
        return sessionInstance;
    }

    private String sessionToken;

    private CatalyzeSession() { }

    protected String getSessionToken() {
        return sessionToken;
    }

    protected void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
