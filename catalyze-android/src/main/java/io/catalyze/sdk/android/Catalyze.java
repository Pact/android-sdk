package io.catalyze.sdk.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.common.base.Optional;

/**
 * Created by mvolkhart on 8/24/13.
 */
public enum Catalyze {

    INSTANCE;
    public static final String CATALYZE = "catalyze";
    private static final String AUTHORIZED = "authorized";
    private static final String SIGNIN_URL = "https://api.catalyze" +
            ".io/v1/%s/auth/signin?callbackUri=%s";
    private static final String LOGIN_USER_ID = "userId";

    private String userSessionToken;

    public Intent login(String applicationId, String applicationScheme) {

        Uri uri = Uri.parse(String.format(SIGNIN_URL, applicationId,
                applicationScheme.toLowerCase()));
        return new Intent(Intent.ACTION_VIEW, uri);

        /*
         * Cases
         *
         * 1. User is not logged in. No preferences.
         * 2. User is logged in & preferences are out of date. Need to check & update creds.
         * 3. User is logged in correctly. Skip network calls.
         */
    }

    /*
    returns true if login succeeded, otherwise false.
     */
    public Optional<User> finalizeLogin(Context context, Intent intent) {
        Uri data = intent.getData();
        User user = null;
        SharedPreferences preferences = context.getSharedPreferences(CATALYZE,
                Context.MODE_PRIVATE);
        if (data != null) {
            boolean authorized = Boolean.parseBoolean(data.getQueryParameter(AUTHORIZED));
            if (authorized) {
                final String token = data.getQueryParameter(User.SESSION_TOKEN);
                final long userId = Long.parseLong(data.getQueryParameter(LOGIN_USER_ID));
                user = new User();
                user.setId(userId);
                user.setSessionToken(token);


                preferences.edit().putLong(User.ID, user.getId()).putString(User.SESSION_TOKEN,
                        user.getSessionToken()).commit();
                userSessionToken = token;
            }
        } else {
            user = new User();
            user.setId(preferences.getLong(User.ID, -1));
            user.setSessionToken(preferences.getString(User.SESSION_TOKEN, null));
            userSessionToken = user.getSessionToken();
        }
        return Optional.fromNullable(user);
    }

    public void logout(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CATALYZE,
                Context.MODE_PRIVATE).edit();
        editor.remove(User.ID).remove(User.SESSION_TOKEN).commit();
        userSessionToken = null;
    }

    @Deprecated
    public String getAppId() {
        return "";
    }

    public String getUserSessionToken() {
        return userSessionToken;
    }


}
