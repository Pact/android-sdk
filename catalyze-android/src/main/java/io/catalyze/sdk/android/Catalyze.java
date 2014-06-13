package io.catalyze.sdk.android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Base class for making Catalyze.io API calls. Catalyze.getInstance() must be called at least
 * once before any API calls are made.
 * 
 * Any API routes being performed must be provided a CatalyzeListener. Upon
 * completion of API request the provided CatalyzeListener will be called with
 * either a success or error response.
 * 
 * All API calls are handled asynchronously (except Umls calls which are handled either
 * synchronously or asynchronously), if calls require a fixed order,
 * this must be handled outside of the Catalyze Android SDK (handled by app
 * logic).
 */
public class Catalyze {

	/**
	 * The API key of the application. This is generated in the Catalyze
	 * Developer Portal. You must have it set in your app's manifest file as meta-data.
     * This is the full ApiKey, "<type> <identifier> <id>"
	 */
	private static String apiKey;

    /**
     * The App ID of the application. This is generated in the Catalyze
     * Developer Portal. You must have it set in your app's manifest file as meta-data.
     */
    private static String appId;

	// The authenticated user. Cannot be used until the instance is
	// authenticated.
	private CatalyzeUser user;

    private static Catalyze catalyzeInstance;
    private Context context;

    public static Catalyze getInstance(Context context) {
        if (catalyzeInstance == null) {
            synchronized (Catalyze.class) {
                if (catalyzeInstance == null) {
                    catalyzeInstance = new Catalyze(context);
                }
            }
        }
        catalyzeInstance.setContext(context);
        return catalyzeInstance;
    }

    private Catalyze(Context context) {
        this.context = context;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            apiKey = bundle.getString("io.catalyze.android.sdk.v2.API_KEY");
            appId = bundle.getString("io.catalyze.android.sdk.v2.APP_ID");
        } catch (PackageManager.NameNotFoundException nnfe) {
            throw new IllegalStateException("ApiKey and AppId must be set in the manifest file");
        }
    }

    private void setContext(Context context) {
        this.context = context;
    }

    /**
     * Return the context associated with this instance.
     *
     * @return The context associated with this instance
     */
    protected Context getContext() {
        return context;
    }

	/**
	 * Initializes the instance by authenticating as the user provided. This
	 * method must be called before a Catalyze instance can be used for any
	 * operations other than Umls lookups.
	 * 
	 * @param userName
	 *            The user to authenticate this instance as
	 * @param password
	 *            The user's password
	 * @param callbackHandler
	 *            The callback to handle the server's response. The instance is
	 *            not active until a successful callback result is returned.
	 */
	public void authenticate(String userName, String password,
			final CatalyzeListener<CatalyzeUser> callbackHandler) {

		CatalyzeAPIAdapter.getApi().signIn(new CatalyzeCredentials(userName, password), new Callback<CatalyzeUser>() {
            @Override
            public void success(CatalyzeUser catalyzeUser, Response response) {
                user = catalyzeUser;
                CatalyzeSession.getInstance().setSessionToken(user.getSessionToken());
                callbackHandler.onSuccess(user);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
	}

	/**
	 * Perform an API to create a new user. The callback will return an
	 * authenticated CatalyzeUser instance.
	 * 
	 * @param userName
	 *            The user name.
	 * @param password
	 *            The user's password.
	 * @param firstName
	 *            The user's first name.
	 * @param lastName
	 *            The user's last name.
     * @param email
     *            The user's email.
	 * @param callbackHandler
	 *            The call back to report back success or failure.
	 */
	public void signUp(String userName, String password,
			String firstName, String lastName, String email,
			final CatalyzeListener<CatalyzeUser> callbackHandler) {

		final CatalyzeUser catalyzeUser = new CatalyzeUser();
        catalyzeUser.setUsername(userName);
        catalyzeUser.setPassword(password);
        catalyzeUser.getName().setFirstName(firstName);
        catalyzeUser.getName().setLastName(lastName);
        catalyzeUser.getEmail().setPrimary(email);

        CatalyzeAPIAdapter.getApi().createUser(catalyzeUser, new Callback<CatalyzeUser>() {
            @Override
            public void success(CatalyzeUser catalyzeUser, Response response) {
                user = catalyzeUser;
                CatalyzeSession.getInstance().setSessionToken(user.getSessionToken());
                callbackHandler.onSuccess(user);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
	}

	/**
	 * All Catalyze instances must be associated with an authenticated user via
	 * the authenticate() method. This method returns that user.
	 * 
	 * @return The authenticated user
	 * @throws IllegalStateException
	 *             If the Catalyze instance has not already been authenticated.
	 */
	public CatalyzeUser getAuthenticatedUser() {
		if (user == null)
			throw new IllegalStateException(
					"No authenticated user has been assigned. Must call " +
                            "Catalyze.getInstance().authenticate() and wait for the callback " +
                            "before using this instance.");
		return user;
	}

	/**
	 * The instance is authenticated off the user is non-null. The session token
	 * may have expired for the user but this test must be checked through an
	 * API call to the backed and should be handled by program logic (handling
	 * of CatalyzeErrors on callbacks),
	 * 
	 * @return Returns true iff the user has been authenticated
	 */
	public boolean isAuthenticated() {
		return user == null;
	}

	/**
	 * Sign out this authenticated CatalyzeUser.
	 * 
	 * @param callbackHandler
	 *            Function to call after HTTP response handled
	 */
	public void signOut(final CatalyzeListener<String> callbackHandler) {
		if (user == null) {
            throw new IllegalStateException(
                    "No authenticated user has been assigned. Must call Catalyze.authenticate() " +
                            "and wait for the callback before using this instance.");
        }
		CatalyzeAPIAdapter.getApi().signOut(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                user = null;
                CatalyzeSession.getInstance().setSessionToken(null);
                callbackHandler.onSuccess(s);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
	}

	/**
	 * Returns the API key for this Catalyze instance.
	 * 
	 * @return The API key
	 */
	protected static String getApiKey() {
		return apiKey;
	}

    /**
     * Returns the appId for this Catalyze instance.
     *
     * @return The appId
     */
    protected static String getAppId() {
        return appId;
    }
}
