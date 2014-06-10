package io.catalyze.sdk.android;

import java.util.List;

import io.catalyze.sdk.android.api.CatalyzeAPIAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Use this class to make API calls to query a custom class, and to interact
 * with the data results from these queries. To perform a query operation, first
 * instantiate an instance of Query with the name of the Custom Class to query
 * and an authenticated user. Next set field, searchBy, pageNumber and pageSize
 * as desired, then call execute query.
 */
public class Query {

	private String field;
	private String searchBy;
	private int pageNumber;
	private int pageSize;
	private String customClassName;

	public Query(String customClassName) {
		this.customClassName = customClassName;
        this.pageNumber = 1;
        this.pageSize = 10;
        this.field = "";
        this.searchBy = "";
	}

    public String getField() {
        return field;
    }

	public void setField(String field) {
		this.field = field;
	}

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public int getPageNumber() {
        return pageNumber;
    }

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

    public int getPageSize() {
        return pageSize;
    }

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

    /**
     * Get the name of the custom class related to this query.
     *
     * @return The custom class name.
     */
    public String getCustomClassName() {
        return customClassName;
    }

    /**
     * Change the name of the custom class to query.
     *
     * @param customClassName
     */
    public void setCustomClassName(String customClassName) {
        this.customClassName = customClassName;
    }

    /**
     * Run a query with the current settings of this instance of Query
     *
     * @param callbackHandler
     *            CatalyzeListener that must expect a Query on successful
     *            callback. This Query will be a reference to this instance of
     *            query, and will have been updated to so that a call to
     *            getResults will return a list containing the results form the
     *            last executed query
     */
    public void executeQuery(final CatalyzeListener<List<CatalyzeEntry>> callbackHandler) {
        CatalyzeAPIAdapter.getApi().queryCustomClass(customClassName, pageSize, pageNumber, field, searchBy, new Callback<List<CatalyzeEntry>>() {
            @Override
            public void success(List<CatalyzeEntry> catalyzeEntries, Response response) {
                callbackHandler.onSuccess(catalyzeEntries);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
    }
}
