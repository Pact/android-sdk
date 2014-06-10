package io.catalyze.sdk.android;

import java.util.List;

import io.catalyze.sdk.android.api.UmlsAPIAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This class allows interaction with the Catalyze.io UMLS lookup services. To
 * use this class you must instantiate UMLS by passing a new instance of UMLS an
 * existing instance of Catalyze.java, that has been instantiated with the
 * appropriate api key and application information.
 * 
 */
public class Umls {

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
	public static void codeLookup(String codeSet, String code,
			final CatalyzeListener<List<UmlsResult>> callbackHandler) {
        UmlsAPIAdapter.getApi().codeLookup(codeSet, code, new Callback<List<UmlsResult>>() {
            @Override
            public void success(List<UmlsResult> umlsResults, Response response) {
                callbackHandler.onSuccess(umlsResults);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
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
	public static void getCodesetList(final CatalyzeListener<List<String>> callbackHandler) {
		UmlsAPIAdapter.getApi().codesetList(new Callback<List<String>>() {
            @Override
            public void success(List<String> strings, Response response) {
                callbackHandler.onSuccess(strings);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
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
	public static void getValueSetList(final CatalyzeListener<List<String>> callbackHandler) {
		UmlsAPIAdapter.getApi().valuesetList(new Callback<List<String>>() {
            @Override
            public void success(List<String> strings, Response response) {
                callbackHandler.onSuccess(strings);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
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
	public static void searchByCodeOrConcept(String type, String codeSet, String code,
			final CatalyzeListener<List<UmlsResult>> callbackHandler) {
        UmlsAPIAdapter.getApi().searchByCodeOrConcept(type, codeSet, code, new Callback<List<UmlsResult>>() {
            @Override
            public void success(List<UmlsResult> umlsResults, Response response) {
                callbackHandler.onSuccess(umlsResults);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
	}

	/**
	 * Perform api call to search a given codeset for a word or phrase. Only the
	 * first 20 matching results will be returned. Value set searches are not
	 * currently supported.
	 * 
	 * @param codeSet
	 *            The UMLS code set (e.g. SNOMEDCT, LOINC, etc)
	 * @param keyword
	 * @param callbackHandler
	 *            CatalyzeListener that must expect an array of UmlsResult on
	 *            success
	 */
	public static void searchByKeyword(String codeSet, String keyword,
			final CatalyzeListener<List<UmlsResult>> callbackHandler) {
		UmlsAPIAdapter.getApi().searchByKeyword(codeSet, keyword, new Callback<List<UmlsResult>>() {
            @Override
            public void success(List<UmlsResult> umlsResults, Response response) {
                callbackHandler.onSuccess(umlsResults);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
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
	 * @param codeSet
	 *            The UMLS code set (e.g. SNOMEDCT, LOINC, etc)
	 * @param prefix
	 *            The search prefix, not case sensitive
	 * @param callbackHandler
	 *            CatalyzeListener that must expect an array of UmlsResult on
	 *            success
	 */
	public static void searchByPrefix(String codeSet, String prefix,
			final CatalyzeListener<List<UmlsResult>> callbackHandler) {
		UmlsAPIAdapter.getApi().searchByPrefix(codeSet, prefix, new Callback<List<UmlsResult>>() {
            @Override
            public void success(List<UmlsResult> umlsResults, Response response) {
                callbackHandler.onSuccess(umlsResults);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
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
	public static void valueLookup(String valueSet, String code,
			final CatalyzeListener<UmlsResult> callbackHandler) {
		UmlsAPIAdapter.getApi().valueLookup(valueSet, code, new Callback<UmlsResult>() {
            @Override
            public void success(UmlsResult umlsResult, Response response) {
                callbackHandler.onSuccess(umlsResult);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                callbackHandler.onError(new CatalyzeException(retrofitError));
            }
        });
	}
}
