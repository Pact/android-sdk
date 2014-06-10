package io.catalyze.sdk.android.api;

import java.util.List;

import io.catalyze.sdk.android.CatalyzeCredentials;
import io.catalyze.sdk.android.CatalyzeEntry;
import io.catalyze.sdk.android.CatalyzeUser;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface CatalyzeAPI {
    /*
     * Auth
     */
    @POST("/auth/signin")
    void signIn(@Body CatalyzeCredentials credentials, Callback<CatalyzeUser> cb);

    @POST("/auth/signout")
    void signOut(Callback<String> cb);

    /*
     * Users
     */
    @POST("/users")
    void createUser(@Body CatalyzeUser user, Callback<CatalyzeUser> cb);

    @GET("/users/{id}")
    void retrieveUser(@Path("id") String id, Callback<CatalyzeUser> cb);

    @PUT("/users/{id}")
    void updateUser(@Path("id") String id, @Body CatalyzeUser user, Callback<CatalyzeUser> cb);

    @DELETE("/users/{id}")
    void deleteUser(@Path("id") String id, Callback<String> cb);

    @GET("/users/{appId}/search/{partial}")
    void searchUsers(@Path("appId") String appId, @Path("partial") String partialUsername, Callback<String> cb);

    /*
     * Custom Class Entries
     */
    @POST("/classes/{className}/entry")
    void createEntry(@Path("className") String className, @Body CatalyzeEntry entry, Callback<CatalyzeEntry> cb);

    @GET("/classes/{className}/entry/{entryId}")
    void retrieveEntry(@Path("className") String className, @Path("entryId") String entryId, Callback<CatalyzeEntry> cb);

    @PUT("/classes/{className}/entry/{entryId}")
    void updateEntry(@Path("className") String className, @Path("entryId") String entryId, @Body CatalyzeEntry entry, Callback<CatalyzeEntry> cb);

    @DELETE("/classes/{className}/entry/{entryId}")
    void deleteEntry(@Path("className") String className, @Path("entryId") String entryId, Callback<String> cb);

    /*
     * Custom Class Queries
     */
    @GET("/classes/{className}/query?pageSize={pageSize}&pageNumber={pageNumber}&field={field}&searchBy={searchBy}")
    void queryCustomClass(@Path("className") String className, @Path("pageSize") int pageSize, @Path("pageNumber") int pageNumber, @Path("field") String field, @Path("searchBy") String searchBy, Callback<List<CatalyzeEntry>> cb);

}