
package io.catalyze.sdk.android;

import java.io.File;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;

public class FileManager extends CatalyzeObject {

	private static final String FILE_ROUTE = Catalyze.BASE_URL + "file";
	private static final String APP_FILE_ROUTE = Catalyze.BASE_URL + "file/app";
	private static final String USER_FILE_ROUTE = Catalyze.BASE_URL + "file/user";
	private Map<String, String> fileHeaders;

	public FileManager(Catalyze catalyze) {
		super(catalyze);
		fileHeaders = catalyze.getAuthorizedHeaders();
	}

	/**
	 * 
	 * @param file
	 *            File to upload
	 * @param phi
	 *            True or false value to indicate if file contains PHI
	 * @param userCallback
	 *            Must expect a string on success
	 */
	public void uploadAppFile(File file, boolean phi, CatalyzeListener<String> userCallback) {
		uploadFile(APP_FILE_ROUTE, file, phi, userCallback);
	}

	/**
	 * 
	 * @param file
	 *            File to upload
	 * @param phi
	 *            True or false value to indicate if file contains PHI
	 * @param userCallback
	 *            Must expect a string on success
	 */
	public void uploadUserFile(File file, boolean phi, CatalyzeListener<String> userCallback) {
		uploadFile(USER_FILE_ROUTE, file, phi, userCallback);
	}

	private void uploadFile(String url, File file, boolean phi, CatalyzeListener<String> userCallback) {
		fileHeaders.put("Content-Type", "multipart/form-data");
		MultipartRequest<JSONObject> request = new MultipartRequest<JSONObject>(url,
				Catalyze.createErrorListener(userCallback), createStringResponseListener(userCallback), file,
				Boolean.toString(phi), fileHeaders);
		CatalyzeRequest.getRequestQueue(catalyze.getContext()).add(request);
	}

	/**
	 * 
	 * @param fileID
	 *            ID of file to retrieve
	 * @param file
	 *            File that download will be saved to
	 * @param userCallback
	 *            CatalyzeListener that expects a File in response. On success a
	 *            pointer to the File is returned to the callback handler.
	 */
	public void getFile(String fileID, File file, CatalyzeListener<File> userCallback) {
		// MultipartRequest<File> request = new
		// MultipartRequest<File>(Method.GET, FILE_ROUTE + "/" + fileID,
		// CatalyzeObject.createErrorListener(userCallback),
		// createFileResponseListener(userCallback), file, fileHeaders);
		MultipartRequest<File> request = new MultipartRequest<File>(Method.GET, FILE_ROUTE + "/" + fileID,
				Catalyze.createErrorListener(userCallback), createFileResponseListener(userCallback), fileHeaders);
		CatalyzeRequest.getRequestQueue(catalyze.getContext()).add(request);
	}

	/**
	 * 
	 * @param fileID
	 *            ID of file to delete
	 * @param userCallback
	 *            CatalyzeListener that expects a string in response. On success
	 *            a blank string is returned to this callback handler
	 */
	public void deleteFile(String fileID, boolean phi, CatalyzeListener<String> userCallback) {
		CatalyzeRequest<JSONObject> request = new CatalyzeRequest<JSONObject>(FILE_ROUTE + "/" + fileID, null,
				createStringResponseListener(userCallback), Catalyze.createErrorListener(userCallback));
		request.setHeaders(catalyze.getAuthorizedHeaders());
		request.delete(catalyze.getContext());
	}

	/**
	 * Volley callback handler that returns a string
	 * 
	 * @param userCallback
	 * @return
	 */
	private Response.Listener<JSONObject> createStringResponseListener(final CatalyzeListener<String> userCallback) {
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				userCallback.onSuccess(response.toString());
			}
		};
	}

	/**
	 * Volley callbaack handler that returns a file pointer
	 * 
	 * @param userCallback
	 * @return
	 */
	private Response.Listener<File> createFileResponseListener(final CatalyzeListener<File> userCallback) {
		return new Response.Listener<File>() {
			@Override
			public void onResponse(File response) {
				// Return file to user
				userCallback.onSuccess(response);
			}
		};
	}

}
