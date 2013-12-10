package io.catalyze.sdk.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

/**
 * This class allows interaction with the Catalyze.io UMLS lookup services. To
 * use this class you must instantiate UMLS by passing a new instance of UMLS an
 * existing instance of Catalyze.java, that has been instantiated with the
 * appropriate api key and application information.
 * 
 */
public class UMLS extends CatalyzeObject {

	// Route values, set in constructor or by setBaseUrl()
	private String baseUrl;
	private String codesetsUrl = "codesets";
	private String valuesetsUrl = "valuesets";
	private String relatedUrl = "related";
	private String phraseUrl = "phrase";
	private String prefixUrl = "prefix";
	private String valueUrl = "value";
	private String codeUrl = "code";

	/**
	 * Create a UMLS instance associated with the given Catalyze object.
	 * 
	 * @param catalyze
	 *            The Catalyze instance. Does not need to be authenticated.
	 */
	public UMLS(Catalyze catalyze) {
		super(catalyze);
		this.setBaseURL("https://umls.catalyze.io/v1/umls");
	}

	/**
	 * Update the route URLs using the provided base URL.
	 * 
	 * @param url
	 *            A URL for the Catalyze API
	 */
	public void setBaseURL(String url) {
		this.baseUrl = url;
		this.codesetsUrl = url + "codesets";
		valuesetsUrl = url + "valuesets";
		relatedUrl = url + "related";
		phraseUrl = url + "phrase";
		prefixUrl = url + "prefix";
		valueUrl = url + "value";
		codeUrl = url + "code";
	}

	/**
	 * Perform an api call to get a list the UMLS codesets currently supported
	 * by Catalyze UMLS The current API only supports a subset of UMLS data. Use
	 * this command to return a list of supported codesets (AKA "vocabularies"
	 * or "RSABs").
	 * 
	 * @param callbackHandler
	 *            User CatalyzeListener that must expect an array of Strings on
	 *            successd
	 */
	public void getCodesetList(CatalyzeListener<String[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createListenerWithStringCallback(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.GET, codesetsUrl, null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getDefaultHeaders());
		request.execute(catalyze.getContext());
	}

	/**
	 * Perform an api call to get a list the UMLS values currently supported by
	 * Catalyze UMLS The current API only supports a subset of available value
	 * sets in CCDA. Use this command to return a list of supported value sets.
	 * 
	 * @param callbackHandler
	 *            User CatalyzeListener that must expect an array of Strings on
	 *            success
	 */
	public void getValueSetList(CatalyzeListener<String[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createListenerWithStringCallback(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.GET, valuesetsUrl, null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getDefaultHeaders());
		request.execute(catalyze.getContext());
	}

	/**
	 * Perform api call, which given a codeset and a code, returns the full
	 * detailed information about this UMLS entry. The value of code can also be
	 * an abbreviated code description/value as returned in prefix search
	 * results. Codes are not necessarily unique so an array of matches is
	 * returned. A good rule of thumb when processing multiple results is to use
	 * the result with the longest 'desc' value.
	 * 
	 * @param codeSet
	 * @param code
	 * @param callbackHandler
	 *            CatalyzeListener that must expect an array of UmlsResult on
	 *            success
	 */
	public void codeLookup(String codeSet, String code,
			CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.GET, codeUrl + "/" + codeSet + "/" + code,
				null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getDefaultHeaders());
		request.execute(catalyze.getContext());
	}

	/**
	 * Perform api call, which given a codeset and a code, returns the full
	 * detailed information about this UMLS entry. The value of {code} can also
	 * be an abbreviated code description/value as returned in prefix search
	 * results. Codes are not necessarily unique so an array of matches is
	 * returned. A good rule of thumb when processing multiple results is to use
	 * the result with the longest 'desc' value. By design this is not part of
	 * the API.
	 * 
	 * @param valueSet
	 * @param code
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a UmlsResult object on
	 *            success
	 */
	public void valueLookup(String valueSet, String code,
			CatalyzeListener<UmlsResult> callbackHandler) {
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.GET, valueUrl + "/" + valueSet + "/" + code,
				null, createUmlsResultCallback(callbackHandler),
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getDefaultHeaders());
		request.execute(catalyze.getContext());
	}

	/**
	 * Perform api call to search a given codeset for a word or phrase. Only the
	 * first 20 matching results will be returned. Value set searches are not
	 * currently supported.
	 * 
	 * @param codeSet The UMLS code set (e.g. SNOMEDCT, LOINC, etc)
	 * @param keyword
	 * @param callbackHandler
	 *            CatalyzeListener that must expect an array of UmlsResult on
	 *            success
	 */
	public void searchByKeyword(String codeSet, String keyword,
			CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.GET, phraseUrl + "/" + codeSet + "/" + keyword,
				null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getDefaultHeaders());
		request.execute(catalyze.getContext());
	}

	/**
	 * Perform api call which searches a given codeset by description prefix.
	 * The prefix is not case sensitive. Only letters and numbers are supported
	 * using this feature. Prefix search is meant for implementing autocomplete
	 * functionality in web apps. For example while the user is typing in a drug
	 * name this API can be called to suggest terms. Prefix search also
	 * typicially outperforms keyword/phrase search so use it instead where it
	 * is appropriate.
	 * 
	 * @param codeSet The UMLS code set (e.g. SNOMEDCT, LOINC, etc)
	 * @param prefix The search prefix, not case sensitive
	 * @param callbackHandler
	 *            CatalyzeListener that must expect an array of UmlsResult on
	 *            success
	 */
	public void searchByPrefix(String codeSet, String prefix,
			CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.GET, prefixUrl + "/" + codeSet + "/" + prefix,
				null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getDefaultHeaders());
		request.execute(catalyze.getContext());
	}

	/**
	 * Perform api call which finds other entries that are related to the
	 * specified code either by concept or code. Finding by concept returns all
	 * entries within the broader concept that the code falls under. Finding
	 * related items by code will return other entries in UMLS with the same
	 * code for the codeset.
	 * 
	 * @param type
	 * @param codeSet
	 * @param code
	 * @param callbackHandler
	 *            CatalyzeListener that must expect an array of UmlsResult on
	 *            success
	 */
	public void searchByCodeOrConcept(String type, String codeSet, String code,
			CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.GET, relatedUrl + "/" + type + "/" + codeSet
						+ "/" + code, null, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getDefaultHeaders());
		request.execute(catalyze.getContext());
	}

	/**
	 * Volley response listener that expects a JSONArray and returns String
	 * array to user CatalyzeListener
	 * 
	 * @param userCallback
	 *            The user-defined callback
	 * @return The volley listener
	 */
	private Response.Listener<JSONArray> createListenerWithStringCallback(
			final CatalyzeListener<String[]> userCallback) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				String[] results = new String[response.length()];
				for (int i = 0; i < response.length(); i++) {
					try {
						results[i] = response.getString(i);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				userCallback.onSuccess(results);
			}
		};
	}

	/**
	 * Volley response listener that expects a JSONArray and returns UmlsResult
	 * array to user CatalyzeListener
	 * 
	 * @param userCallback
	 *            The user-defined callback
	 * @return The volley listener
	 */
	private Response.Listener<JSONArray> createUmlsResultArrayCallback(
			final CatalyzeListener<UmlsResult[]> userCallback) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				UmlsResult[] umlsResults = new UmlsResult[response.length()];
				for (int i = 0; i < response.length(); i++) {
					try {
						umlsResults[i] = new UmlsResult(UMLS.this.catalyze,
								response.getJSONObject(i));
						umlsResults[i].mJson = response.getJSONObject(i);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				userCallback.onSuccess(umlsResults);
			}
		};
	}

	/**
	 * Volley response listener that expects a JSONObject and returns UmlsResult
	 * to user CatalyzeListener
	 * 
	 * @param userCallback
	 *            The user-defined callback
	 * @return The volley listener
	 */
	private Response.Listener<JSONObject> createUmlsResultCallback(
			final CatalyzeListener<UmlsResult> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				UmlsResult umlsResults = new UmlsResult(UMLS.this.catalyze,
						response);
				userCallback.onSuccess(umlsResults);
			}
		};
	}

}
