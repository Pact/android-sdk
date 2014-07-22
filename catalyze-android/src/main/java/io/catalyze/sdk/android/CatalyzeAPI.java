package io.catalyze.sdk.android;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public interface CatalyzeAPI {
    /*
     * Auth
     */
    @POST("/auth/signin")
    @Headers("Content-Type: application/json")
    void signIn(@Body CatalyzeCredentials credentials, Callback<CatalyzeUser> cb);

    @POST("/auth/signout")
    @Headers("Content-Type: application/json")
    void signOut(Callback<String> cb);

    /*
     * Users
     */
    @POST("/users")
    @Headers("Content-Type: application/json")
    void createUser(@Body CatalyzeUser user, Callback<CatalyzeUser> cb);

    @GET("/users/{id}")
    @Headers("Content-Type: application/json")
    void retrieveUser(@Path("id") String id, Callback<CatalyzeUser> cb);

    @PUT("/users/{id}")
    @Headers("Content-Type: application/json")
    void updateUser(@Path("id") String id, @Body CatalyzeUser user, Callback<CatalyzeUser> cb);

    @DELETE("/users/{id}")
    @Headers("Content-Type: application/json")
    void deleteUser(@Path("id") String id, Callback<String> cb);

    @GET("/users/{appId}/search/{partial}")
    @Headers("Content-Type: application/json")
    void searchUsers(@Path("appId") String appId, @Path("partial") String partialUsername, Callback<String> cb);

    /*
     * Custom Class Entries
     */
    @POST("/classes/{className}/entry")
    @Headers("Content-Type: application/json")
    void createEntry(@Path("className") String className, @Body CatalyzeEntry entry, Callback<CatalyzeEntry> cb);

    @POST("/classes/{className}/entry/{usersId}")
    @Headers("Content-Type: application/json")
    void createEntryForUser(@Path("className") String className, @Path("usersId") String usersId, @Body CatalyzeEntry entry, Callback<CatalyzeEntry> cb);

    @GET("/classes/{className}/entry/{entryId}")
    @Headers("Content-Type: application/json")
    void retrieveEntry(@Path("className") String className, @Path("entryId") String entryId, Callback<CatalyzeEntry> cb);

    @PUT("/classes/{className}/entry/{entryId}")
    @Headers("Content-Type: application/json")
    void updateEntry(@Path("className") String className, @Path("entryId") String entryId, @Body Map<String, Object> content, Callback<CatalyzeEntry> cb);

    @DELETE("/classes/{className}/entry/{entryId}")
    @Headers("Content-Type: application/json")
    void deleteEntry(@Path("className") String className, @Path("entryId") String entryId, Callback<String> cb);

    /*
     * Custom Class Queries
     */
    @GET("/classes/{className}/query")
    @Headers("Content-Type: application/json")
    void queryCustomClass(@Path("className") String className, @Query("pageSize") int pageSize, @Query("pageNumber") int pageNumber, @Query("field") String field, @Query("searchBy") String searchBy, Callback<List<CatalyzeEntry>> cb);

    /*
     * User File Management
     */
    @Multipart
    @POST("/users/files")
    void uploadFileToUser(@Part("file") TypedFile file, @Part("phi") TypedString phi, Callback<JsonObject> cb);

    @GET("/users/files")
    void listFiles(Callback<JsonArray> cb);

    @GET("/users/files/{filesId}")
    void retrieveFile(@Path("filesId") String filesId, Callback<Response> cb);

    @DELETE("/users/files/{filesId}")
    void deleteFile(@Path("filesId") String filesId, Callback<JsonObject> cb);

    @Multipart
    @POST("/users/{usersId}/files")
    void uploadFileToOtherUser(@Path("usersId") String usersId, @Part("file") TypedFile file, @Part("phi") TypedString phi, Callback<JsonObject> cb);

    @GET("/users/{usersId}/files")
    void listFilesForUser(@Path("usersId") String usersId, Callback<JsonArray> cb);

    @GET("/users/{usersId}/files/{filesId}")
    void retrieveFileFromUser(@Path("usersId") String usersId, @Path("filesId") String filesId, Callback<Response> cb);

    @DELETE("/users/{usersId}/files/{filesId}")
    void deleteFileFromUser(@Path("usersId") String usersId, @Path("filesId") String filesId, Callback<JsonObject> cb);
}