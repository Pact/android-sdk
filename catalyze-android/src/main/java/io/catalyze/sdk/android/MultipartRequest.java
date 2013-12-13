package io.catalyze.sdk.android;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

/**
 * A specialized request for handling Multipart binary data.
 * 
 * T is the expected return type
 * 
 * 
 * 
 * @param <T>
 */
public class MultipartRequest<T> extends Request<T> {

	private Map<String, String> mHeaders = new HashMap<String, String>();
	private final Response.Listener<T> mListener;
	private HttpEntity httpEntity;

	/**
	 * MultipartRequest constructor to download or delete file
	 * 
	 * @param method
	 *            The HTTP method
	 * @param url
	 *            The URL of the request's endpoint
	 * @param errorListener
	 *            The callback for errors
	 * @param listener
	 *            The callback for success
	 * @param fileHeaders
	 *            The key-value pairs to add to the HTTP header
	 */
	public MultipartRequest(int method, String url,
			Response.ErrorListener errorListener,
			Response.Listener<T> listener, Map<String, String> fileHeaders) {
		super(method, url, errorListener);
		mHeaders = fileHeaders;
		mListener = listener;
		if (true)
			throw new IllegalStateException(
					"File operations not yet supported.");
	}

	/**
	 * MultipartRequest constructor to upload file
	 * 
	 * @param url
	 * @param errorListener
	 * @param listener
	 * @param file
	 * @param phi
	 * @param fileHeaders
	 */
	public MultipartRequest(String url, Response.ErrorListener errorListener,
			Response.Listener<T> listener, File file, String phi,
			Map<String, String> fileHeaders) {
		super(Method.POST, url, errorListener);
		mHeaders = fileHeaders;
		mListener = listener;

		MultipartEntityBuilder multiPartBuilder = MultipartEntityBuilder
				.create();
		multiPartBuilder.addPart("file", new FileBody(file));
		multiPartBuilder.addTextBody("phi", phi);

		// If needed user the following method to change content type header
		// multiPartBuilder.addBinaryBody("file", file, "content-", filename)

		httpEntity = multiPartBuilder.build();

		if (true)
			throw new IllegalStateException(
					"File operations not yet supported.");
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			httpEntity.writeTo(bos);
		} catch (IOException e) {
			Log.e("Catalyze", "Multipart request error", e);
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@Override
	public String getBodyContentType() {
		return httpEntity.getContentType().getValue();
	}

	@Override
	public Map<String, String> getHeaders() {
		return mHeaders;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {

		// try {
		// String jsonString = new String(response.data,
		// HttpHeaderParser.parseCharset(response.headers));
		//
		// if (jsonString.charAt(0) == '{') {
		// return (Response<T>) Response.success(new JSONObject(jsonString),
		// HttpHeaderParser.parseCacheHeaders(response));
		// } else {
		// return (Response<T>) Response.success(new JSONArray(jsonString),
		// HttpHeaderParser.parseCacheHeaders(response));
		// }
		// } catch (UnsupportedEncodingException e) {
		// return Response.error(new ParseError(e));
		// } catch (JSONException je) {
		// //file response here?
		// return Response.error(new ParseError(je));
		// }

		// FIXME? good chance that this will requre a bit more work when I
		// actually try to run it
		// may need to detect whether the response is a file or JSON, could do
		// the json parsing attempt and then assume file upon exception, or if
		// there is a way to tell if something is a file try that

		// TODO no idea where this is or what is going on

		return (Response<T>) Response.success(response, getCacheEntry());
	}
}
