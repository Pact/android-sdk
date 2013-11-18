package io.catalyze.sdk.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;

public class UMLS extends CatalyzeObject {


	// URL constants
	private static final String UMLS_ROUTE = "https://umls.catalyze.io/v1/umls";
	private static final String CODESETS = "codesets";
	private static final String VALUESETS = "valuesets";
	private static final String RELATED = "related";
	private static final String PHRASE = "phrase";
	private static final String PREFIX = "prefix";
	private static final String VALUE = "value";
	private static final String CODE = "code";	
	private Catalyze catalyze;

	public UMLS(Catalyze catalyze) {
		this.catalyze = catalyze;
	}

	public void getCodesetList(CatalyzeListener<String[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createListenerWithStringCallback(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getUmlsUrl(CODESETS), null,
				responseListener, errorListener);
		request.setHeaders(catalyze.getDefaultHeaders());
		request.get(catalyze.getContext());
	}

	public void getValueSetList(CatalyzeListener<String[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createListenerWithStringCallback(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getUmlsUrl(VALUESETS), null,
				responseListener, errorListener);
		request.setHeaders(catalyze.getDefaultHeaders());
		request.get(catalyze.getContext());
	}

	public void codeLookup(String codeSet, String code, CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getUmlsUrl(CODE, codeSet, code), null,
				responseListener, errorListener);
		request.setHeaders(catalyze.getDefaultHeaders());
		request.get(catalyze.getContext());
	}

	public void valueLookup(String valueSet, String code, CatalyzeListener<UmlsResult> callbackHandler) {
		responseListener = createUmlsResultCallback(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(getUmlsUrl(VALUE, valueSet, code), null,
				responseListener, errorListener);
		request.setHeaders(catalyze.getDefaultHeaders());
		request.get(catalyze.getContext());
	}

	public void searchByKeyword(String codeSet, String keyword, CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getUmlsUrl(PHRASE, codeSet, keyword),
				null, responseListener, errorListener);
		request.setHeaders(catalyze.getDefaultHeaders());
		request.get(catalyze.getContext());
	}

	public void searchByPrefix(String codeSet, String prefix, CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getUmlsUrl(PREFIX, codeSet, prefix),
				null, responseListener, errorListener);
		request.setHeaders(catalyze.getDefaultHeaders());
		request.get(catalyze.getContext());
	}

	public void searchByCodeOrConcept(String type, String codeSet, String code,
			CatalyzeListener<UmlsResult[]> callbackHandler) {
		Response.Listener<JSONArray> responseListener = createUmlsResultArrayCallback(callbackHandler);
		errorListener = createErrorListener(callbackHandler);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(getUmlsUrl(RELATED, type, codeSet, code),
				null, responseListener, errorListener);
		request.setHeaders(catalyze.getDefaultHeaders());
		request.get(catalyze.getContext());
	}

	private Response.Listener<JSONArray> createListenerWithStringCallback(final CatalyzeListener<String[]> userCallback) {
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
	
	private Response.Listener<JSONArray> createUmlsResultArrayCallback(final CatalyzeListener<UmlsResult[]> userCallback) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				UmlsResult[] umlsResults = new UmlsResult[response.length()];
				for(int i = 0; i < response.length(); i++){
					try {
						umlsResults[i] = new UmlsResult(response.getJSONObject(i));
						umlsResults[i].mJson = response.getJSONObject(i);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				userCallback.onSuccess(umlsResults);
			}
		};
	}
	
	private Response.Listener<JSONObject> createUmlsResultCallback(final CatalyzeListener<UmlsResult> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				UmlsResult umlsResults = new UmlsResult(response);
				userCallback.onSuccess(umlsResults);
			}
		};
	}

	private String getUmlsUrl(String... args) {
		String url = UMLS_ROUTE;
		for (String s : args) {
			url += "/" + s;
		}
		return url;
	}

}
