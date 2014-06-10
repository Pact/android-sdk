package io.catalyze.sdk.android;

public class CatalyzeSession {

    private static CatalyzeSession sessionInstance;

    public static CatalyzeSession getInstance() {
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

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
