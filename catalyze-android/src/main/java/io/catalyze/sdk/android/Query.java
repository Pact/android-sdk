package io.catalyze.sdk.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.Response;

/**
 * Use this class to make API calls to query a custom class, and to interact
 * with the data results from these queries. To perform a query operation, first
 * instantiate an instance of Query with the name of the Custom Class to query
 * and an authenticated user. Next set field, searchBy, pageNumber and pageSize
 * as desired, then call execute query.
 */
public class Query extends CatalyzeObject {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -9121968191350735246L;
	
	private static final String FIELD = "field";
	private static final String SEARCH_BY = "searchBy";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_SIZE = "pageSize";
	private String customClassName;
	private ArrayList<CustomClass> queryResults;

	public Query(String className, Catalyze catalyze) {
		super(catalyze);
		this.customClassName = className;
		this.queryResults = new ArrayList<CustomClass>();

		// Set default settings (return the first ten items in a custom class). 
		try {
			//this.mJson.put(FIELD, "");
			//this.mJson.put(SEARCH_BY, "");
			this.mJson.put(PAGE_NUMBER, "1");
			this.mJson.put(PAGE_SIZE, "10");
		} catch (JSONException jse) {
			jse.printStackTrace();
		}
	}

	/**
	 * Run a query with the current settings of this isntance of Query
	 * 
	 * @param callbackHandler
	 *            CatalyzeListener that must expect a Query on successful
	 *            callback. This Query will be a reference to this instance of
	 *            query, and will have been updated to so that a call to
	 *            getResults will return a list containing the results form the
	 *            last executed query
	 */
	public void executeQuery(CatalyzeListener<Query> callbackHandler) {
		Response.Listener<JSONArray> responseListener = testListener(
				callbackHandler, this);
		CatalyzeRequest<JSONArray> request = new CatalyzeRequest<JSONArray>(
				CatalyzeRequest.POST, catalyze.queryUrl + customClassName
						+ "/query", this.mJson, responseListener,
				Catalyze.createErrorListener(callbackHandler));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute();
	}

	/**
	 * Get the name of the custom class related to this query.
	 * 
	 * @return The custom class name.
	 */
	public String getCustomClassName() {
		return this.customClassName;
	}

	public String getField() {
		return mJson.optString(FIELD, null);
	}

	/**
	 * Get the results from the last executed query.
	 * 
	 * @return
	 */
	public ArrayList<CustomClass> getResults() {
		return queryResults;
	}

	public String getSearchBy() {
		return mJson.optString(SEARCH_BY, null);
	}

	/**
	 * Change the name of the custom class to query.
	 * 
	 * @param className
	 */
	public void setCustomClassName(String className) {
		customClassName = className;
	}

	public Query setField(String name) {
		setSomething(FIELD, name);
		return this;
	}

	public void setPageNumber(int pageNumber) {
		try {
			mJson.put(PAGE_NUMBER, pageNumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setPageSize(int pageSize) {
		try {
			mJson.put(PAGE_SIZE, pageSize);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setSearchBy(String data) {
		setSomething(SEARCH_BY, data);
	}

	private void setSomething(String key, String value) {
		try {
			mJson.put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private Response.Listener<JSONArray> testListener(
			final CatalyzeListener<Query> callbackHandler, final Query q) {
		return new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				for (int i = 0; i < response.length(); i++) {
					try {
						q.queryResults.add(new CustomClass(Query.this.catalyze,
								Query.this.customClassName, response
										.getJSONObject(i)));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				callbackHandler.onSuccess(q);
			}
		};
	}
}
