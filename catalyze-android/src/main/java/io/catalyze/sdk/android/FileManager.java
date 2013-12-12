package io.catalyze.sdk.android;

import java.io.File;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;

/**
 * The FileManager allows for the upload and download of files to the Catalyze
 * backend file store.
 * 
 * @author uphoff
 * 
 */
public class FileManager extends CatalyzeObject {

	/**
	 * Create a new instance associated with the authenticated Catalyze
	 * instance.
	 * 
	 * @param catalyze
	 *            An authenticated connection to the API
	 */
	protected FileManager(Catalyze catalyze) {
		super(catalyze);
	}

	/**
	 * This method provides the ability to upload a file associated with the
	 * current app.
	 * 
	 * @param file
	 *            File to upload
	 * @param phi
	 *            True or false value to indicate if file contains PHI
	 * @param userCallback
	 *            Must expect a string on success
	 */
	public void uploadAppFile(File file, boolean phi,
			CatalyzeListener<String> userCallback) {
		uploadFile(catalyze.appFileUrl, file, phi, userCallback);
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
	public void uploadUserFile(File file, boolean phi,
			CatalyzeListener<String> userCallback) {
		uploadFile(catalyze.userFileUrl, file, phi, userCallback);
	}

	/**
	 * Helper method to upload either an app file or user file.
	 * 
	 * @param url
	 *            The URL: app file or user file URL
	 * @param file
	 *            The file to upload
	 * @param phi
	 *            If the file may have PHI
	 * @param userCallback
	 *            The user-defined callback to handle results
	 */
	private void uploadFile(String url, File file, boolean phi,
			CatalyzeListener<String> userCallback) {
		Map<String, String> fileHeaders = catalyze.getAuthorizedHeaders();
		fileHeaders.put("Content-Type", "multipart/form-data");
		MultipartRequest<JSONObject> request = new MultipartRequest<JSONObject>(
				url, Catalyze.createErrorListener(userCallback),
				createStringResponseListener(userCallback), file,
				Boolean.toString(phi), fileHeaders);
		CatalyzeRequest.getRequestQueue(userCallback.getContext()).add(request);
	}

	/**
	 * Retrieves a file from the backend. The authenticated user must be the
	 * owner of the file or have sufficient permissions to read the file.
	 * 
	 * @param fileID
	 *            ID of file to retrieve
	 * @param file
	 *            File that download will be saved to
	 * @param userCallback
	 *            CatalyzeListener that expects a File in response. On success a
	 *            pointer to the File is returned to the callback handler.
	 */
	public void getFile(String fileID, File file,
			final CatalyzeListener<File> userCallback) {
		Response.Listener<File> responseListener = new Response.Listener<File>() {
			@Override
			public void onResponse(File response) {
				// Return file to user
				userCallback.onSuccess(response);
			}
		};
		Map<String, String> fileHeaders = catalyze.getAuthorizedHeaders();
		MultipartRequest<File> request = new MultipartRequest<File>(Method.GET,
				catalyze.fileUrl + "/" + fileID,
				Catalyze.createErrorListener(userCallback), responseListener,
				fileHeaders);
		CatalyzeRequest.getRequestQueue(userCallback.getContext()).add(request);
	}

	/**
	 * Provides the ability to delete a file on the backend.
	 * 
	 * @param fileID
	 *            ID of file to delete
	 * @param userCallback
	 *            CatalyzeListener that expects a string in response. On success
	 *            a blank string is returned to this callback handler
	 */
	public void deleteFile(String fileID, boolean phi,
			CatalyzeListener<String> userCallback) {
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(
				CatalyzeRequest.DELETE, catalyze.fileUrl + "/" + fileID, null,
				createStringResponseListener(userCallback),
				Catalyze.createErrorListener(userCallback));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.execute(userCallback.getContext());
	}

	/**
	 * Volley callback handler that returns a string
	 * 
	 * @param userCallback
	 *            The callback created by the user
	 * @return The callback listener
	 */
	private Response.Listener<JSONObject> createStringResponseListener(
			final CatalyzeListener<String> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				userCallback.onSuccess(response.toString());
			}
		};
	}

}
