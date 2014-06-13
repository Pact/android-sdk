package io.catalyze.sdk.android;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.InputStream;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * The FileManager allows for the upload and download of files to the Catalyze
 * backend file store.
 * 
 * @author uphoff
 */
public class FileManager {

	/**
	 * Provides the ability to delete a file on the backend.
	 * 
	 * @param filesId
	 *            ID of file to delete
	 * @param userCallback
	 *            CatalyzeListener that expects a string in response. On success
	 *            a blank string is returned to this callback handler
	 */
	public static void deleteFile(String filesId, final CatalyzeListener<String> userCallback) {
		CatalyzeAPIAdapter.getApi().deleteFile(filesId, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                userCallback.onSuccess(jsonObject.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
	}

	/**
	 * Retrieves a file from the backend. The authenticated user must be the
	 * owner of the file or have sufficient permissions to read the file.
	 * 
	 * @param filesId
	 *            ID of file to retrieve
	 * @param userCallback
	 *            CatalyzeListener that expects a File in response. On success a
	 *            File is returned to the callback handler.
	 */
	public static void getFile(String filesId, final CatalyzeListener<InputStream> userCallback) {
        CatalyzeAPIAdapter.getApi().retrieveFile(filesId, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    userCallback.onSuccess(response.getBody().in());
                } catch (Exception e) {
                    userCallback.onError(new CatalyzeException(RetrofitError.unexpectedError("local", e)));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
	}

    /**
     * Retrieves a list of files from the backend. The authenticated user must be the
     * owner of the files or have sufficient permissions to read the file for them to be
     * included in the list.
     *
     * @param userCallback
     *            CatalyzeListener that expects a String in response.
     */
    public static void listFiles(final CatalyzeListener<String> userCallback) {
        CatalyzeAPIAdapter.getApi().listFiles(new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonElements, Response response) {
                userCallback.onSuccess(jsonElements.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
    }

    /**
     * This method provides the ability to upload a file associated with the
     * authenticated user.
     *
     * @param file
     *            File to upload
     * @param phi
     *            True or false value to indicate if file contains PHI
     * @param userCallback
     *            Must expect a string on success
     */
    public static void uploadFileToUser(String mimeType, File file, boolean phi, final CatalyzeListener<String> userCallback) {
        CatalyzeAPIAdapter.getApi().uploadFileToUser(new TypedFile(mimeType, file), new TypedString(Boolean.toString(phi)), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                userCallback.onSuccess(jsonObject.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
    }

    /**
     * Provides the ability to delete a file on the backend.
     *
     * @param usersId
     *            ID of the user whos file will be deleted
     * @param filesId
     *            ID of file belonging to the indicated user to delete
     * @param userCallback
     *            CatalyzeListener that expects a string in response. On success
     *            a blank string is returned to this callback handler
     */
    public static void deleteFileFromUser(String usersId, String filesId, final CatalyzeListener<String> userCallback) {
        CatalyzeAPIAdapter.getApi().deleteFileFromUser(usersId, filesId, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                userCallback.onSuccess(jsonObject.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
    }

    /**
     * Retrieves a file from the backend. The authenticated user must be the
     * owner of the file or have sufficient permissions to read the file.
     *
     * @param usersId
     *            ID of the user who owns the file
     * @param filesId
     *            ID of file to retrieve
     * @param userCallback
     *            CatalyzeListener that expects a File in response. On success a
     *            File is returned to the callback handler.
     */
    public static void getFileFromUser(String usersId, String filesId, final CatalyzeListener<InputStream> userCallback) {
        CatalyzeAPIAdapter.getApi().retrieveFileFromUser(usersId, filesId, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    userCallback.onSuccess(response.getBody().in());
                } catch (Exception e) {
                    userCallback.onError(new CatalyzeException(RetrofitError.unexpectedError("local", e)));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
    }

    /**
     * Retrieves a list of files from the backend. The authenticated user must be the
     * owner of the files or have sufficient permissions to read the file for them to be
     * included in the list.
     *
     * @param usersId
     *            ID of the users whos files will be listed
     * @param userCallback
     *            CatalyzeListener that expects a String in response.
     */
    public static void listFilesForUser(String usersId, final CatalyzeListener<String> userCallback) {
        CatalyzeAPIAdapter.getApi().listFilesForUser(usersId, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonElements, Response response) {
                userCallback.onSuccess(jsonElements.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
    }

	/**
	 * This method provides the ability to upload a file associated to a user
     * with the given usersId.
	 *
     * @param usersId
     *            ID of the user who will own the file
	 * @param file
	 *            File to upload
	 * @param phi
	 *            True or false value to indicate if file contains PHI
	 * @param userCallback
	 *            Must expect a string on success
	 */
	public static void uploadFileToOtherUser(String usersId, String mimeType, File file, boolean phi, final CatalyzeListener<String> userCallback) {
        CatalyzeAPIAdapter.getApi().uploadFileToOtherUser(usersId, new TypedFile(mimeType, file), new TypedString(Boolean.toString(phi)), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                userCallback.onSuccess(jsonObject.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                userCallback.onError(new CatalyzeException(error));
            }
        });
	}

}
