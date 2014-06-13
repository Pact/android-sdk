package io.catalyze.sdk.android;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * These calls can either by made synchronously or asynchronously.
 */
public interface UmlsAPI {

    @GET("/code/{codeset}/{code}")
    void codeLookup(@Path("codeset") String codeset, @Path("code") String code, Callback<List<UmlsResult>> cb);

    @GET("/code/{codeset}/{code}")
    List<UmlsResult> codeLookup(@Path("codeset") String codeset, @Path("code") String code);

    @GET("/codesets")
    void codesetList(Callback<List<String>> cb);

    @GET("/codesets")
    List<String> codesetList();

    @GET("/valuesets")
    void valuesetList(Callback<List<String>> cb);

    @GET("/valuesets")
    List<String> valuesetList();

    @GET("/related/{type}/{codeset}/{code}")
    void searchByCodeOrConcept(@Path("type") String type, @Path("codeset") String codeset, @Path("code") String code, Callback<List<UmlsResult>> cb);

    @GET("/related/{type}/{codeset}/{code}")
    List<UmlsResult> searchByCodeOrConcept(@Path("type") String type, @Path("codeset") String codeset, @Path("code") String code);

    @GET("/phrase/{codeset}/{keyword}")
    void searchByKeyword(@Path("codeset") String codeset, @Path("keyword") String keyword, Callback<List<UmlsResult>> cb);

    @GET("/phrase/{codeset}/{keyword}")
    List<UmlsResult> searchByKeyword(@Path("codeset") String codeset, @Path("keyword") String keyword);

    @GET("/prefix/{codeset}/{prefix}")
    void searchByPrefix(@Path("codeset") String codeset, @Path("prefix") String prefix, Callback<List<UmlsResult>> cb);

    @GET("/prefix/{codeset}/{prefix}")
    List<UmlsResult> searchByPrefix(@Path("codeset") String codeset, @Path("prefix") String prefix);

    @GET("/value/{valueset}/{code}")
    void valueLookup(@Path("valueset") String valueset, @Path("code") String code, Callback<UmlsResult> cb);

    @GET("/value/{valueset}/{code}")
    UmlsResult valueLookup(@Path("valueset") String valueset, @Path("code") String code);
}