package io.catalyze.sdk.android.api;

import java.util.List;

import io.catalyze.sdk.android.UmlsResult;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UmlsAPI {

    @GET("/code/{codeset}/{code}")
    void codeLookup(@Path("codeset") String codeset, @Path("code") String code, Callback<List<UmlsResult>> cb);

    @GET("/codesets")
    void codesetList(Callback<List<String>> cb);

    @GET("/valuesets")
    void valuesetList(Callback<List<String>> cb);

    @GET("/related/{type}/{codeset}/{code}")
    void searchByCodeOrConcept(@Path("type") String type, @Path("codeset") String codeset, @Path("code") String code, Callback<List<UmlsResult>> cb);

    @GET("/phrase/{codeset}/{keyword}")
    void searchByKeyword(@Path("codeset") String codeset, @Path("keyword") String keyword, Callback<List<UmlsResult>> cb);

    @GET("/prefix/{codeset}/{prefix}")
    void searchByPrefix(@Path("codeset") String codeset, @Path("prefix") String prefix, Callback<List<UmlsResult>> cb);

    @GET("/value/{valueset}/{code}")
    void valueLookup(@Path("valueset") String valueset, @Path("code") String code, Callback<UmlsResult> cb);
}