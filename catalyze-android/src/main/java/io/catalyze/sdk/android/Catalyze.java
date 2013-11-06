package io.catalyze.sdk.android;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by mvolkhart on 8/24/13.
 */
public class Catalyze{
    
    protected String apiKey;
    private final static String baseUrl = "https://api.catalyze.io";
    
    protected static TextView mResult;
    private static final String AUTHORIZED = "authorized";
    private static final String SIGNIN_URL = "https://api.catalyze.io/v1/auth/signin";
    private static final String SIGNUP_URL = "https://api.catalyze.io/v1/user";
    private static final String USER_LOCATION_URL = "https://api.catalyze.io/v1/user";
    private static final String ANDROID = "android";
    private final String identifier;
    private CatalyzeUser user;
    private Context appContext;
    
    public Catalyze(String apiKey, String identifier, Context context){
    	this.apiKey = apiKey;
    	appContext = context;
    	this.identifier = identifier;
    }
    
    /**
	 * Catalyze Constructor for logging in an existing user
	 * 
	 * @param apiKey
	 * @param userName
	 * @param password
	 * @param context
	 */
	public Catalyze(String apiKey, String identifier, String userName, String password,
			CatalyzeListener<CatalyzeUser> handleResponse, Context context) {
		this.apiKey = apiKey;
		user = new CatalyzeUser(this);
    	user.getAuthenticatedUser(userName, password, handleResponse, context);
    	appContext = context;
    	this.identifier = identifier;
    }
    
    /***
	 * Catalyze constructor for signing up a new new user
	 * 
	 * @param apiKey
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param context
	 */
//	public Catalyze(String apiKey, String userName, String password,
//			String firstName, String lastName,
//			Response.Listener<JSONObject> handleResponse, Context context) {
//		this.apiKey = apiKey;
//		user = new CatalyzeUser(apiKey);
//		user.signUp(userName, password, firstName, lastName, context);
//	}
	
	public void updateContext(Context context){
		this.appContext = context;
	}
	
	protected Context getContext(){
		return appContext;
	}
	
	protected CatalyzeUser getUser(){
		return user;
	}
	
	public void getUser(String userName, String password, CatalyzeListener<CatalyzeUser> callbackHandler){
		user = new CatalyzeUser(this);
		user.getAuthenticatedUser(userName, password, callbackHandler, appContext);
		//return user;
	}
	
	public void signUp(String userName, String password, String firstName, String lastName, CatalyzeListener<CatalyzeUser> callbackHandler){
		user = new CatalyzeUser(this);
		user.signUp(userName, password, firstName, lastName, callbackHandler, appContext);
		//return user;
	}
	
	public void logoutCurrentUser(CatalyzeListener<CatalyzeUser> callbackHandler){
		user.signOut(callbackHandler, appContext);
	}
	
	public void deleteCurrentUser(CatalyzeListener<CatalyzeUser> callbackHandler){
		user.deleteUser(callbackHandler, appContext);
	}

	protected String getAPIKey() {
		return apiKey;
	}
	
	protected Map<String, String> getDefaultHeaders(){
		Map<String, String> headers = new HashMap<String, String>();
		//headers.put("X-Api-Key", "android io.catalyze.example " + getAPIKey());
		headers.put("X-Api-Key", "android " +  identifier + " " + getAPIKey());
		headers.put("Content-Type", "application/json");
		return headers;
	}

	/**
	 * Returns the currently signed in user. If no user has been previously created will return null
	 * @return
	 */
	public CatalyzeUser getCurrentUser() {
		return user;
	}
	
	/***
	 * FIXME add additional error reporting
	 * Generic error handler
	 * @return
	 */	
	protected Response.ErrorListener createErrorListener(final CatalyzeListener<CatalyzeUser> userCallback) {
		return new Response.ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				JSONObject e = new JSONObject();
				CatalyzeError ce = new CatalyzeError(error);
				try {
					e = new JSONObject();
					e.put("error", error.toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				userCallback.onError(ce);
			}
		};
	}
    
    
}
