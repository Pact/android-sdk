package io.catalyze.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.common.collect.ImmutableMap;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mvolkhart on 8/24/13.
 */
public class CatalyzeRequest extends JsonObjectRequest {

    public static final String BASE_PATH = "https://api.catalyze.io/v1";
    private static final String TAG = CatalyzeRequest.class.getSimpleName();
    private static final String ACCESS_KEY_HEADER = "X-Access-Key";
    private static final String ACCESS_KEY_TYPE = "android ";
    private static final String ACCESS_KEY_VALIDATOR_KEY = "io.catalyze.android.sdk.v1.ACCESS_KEY";
    private static final String CONTENT_TYPE = "application/json";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String AUTHENTICATION_SCHEMA = "Bearer ";
    private final Map<String, String> mHeaders = new HashMap<String, String>();
    private Priority mPriority;

    private CatalyzeRequest(int method, String url, JSONObject jsonRequest, Response
            .Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    private CatalyzeRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject>
            listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() {
        return ImmutableMap.copyOf(mHeaders);
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    public enum Method {
        CREATE, RETRIEVE, UPDATE, DELETE
    }

    public abstract static class Builder<Q, S> {

        private final Context mContext;
        private final String mUrl;
        private int mMethod = Integer.MIN_VALUE;
        private JSONObject mJson;
        private Response.Listener<JSONObject> mListener;
        private Response.ErrorListener mErrorListener;
        private Priority mPriority;

        public Builder(Context context, String url) {
            mContext = context.getApplicationContext();
            mUrl = url;
        }

        public Builder<Q, S> setMethod(Method method) {
            switch (method) {
                case CREATE:
                    mMethod = Request.Method.POST;
                    break;
                case RETRIEVE:
                    mMethod = Request.Method.GET;
                    break;
                case UPDATE:
                    mMethod = Request.Method.PUT;
                    break;
                case DELETE:
                    mMethod = Request.Method.DELETE;
                    break;
            }
            return this;
        }

        public abstract Builder<Q, S> setContent(final Q body);

        protected void setJson(JSONObject json) {
            mJson = json;
        }

        public abstract Builder<Q, S> setListener(final Response.Listener<S> listener);

        protected Builder<Q, S> setJsonListener(Response.Listener<JSONObject> listener) {
            mListener = listener;
            return this;
        }

        public Builder<Q, S> setErrorListener(Response.ErrorListener errorListener) {
            mErrorListener = errorListener;
            return this;
        }

        public Builder<Q, S> setPriority(Priority priority) {
            mPriority = priority;
            return this;
        }

        public CatalyzeRequest build() {

            // generate the request
            CatalyzeRequest request;
            if (mMethod == Integer.MIN_VALUE) {
                request = new CatalyzeRequest(mUrl, mJson, mListener, mErrorListener);
            } else {
                if (mMethod == Request.Method.GET || mMethod == Request.Method.DELETE) {
                    mJson = null;
                }
                request = new CatalyzeRequest(mMethod, mUrl, mJson, mListener, mErrorListener);
            }

            // Set the priority
            if (mPriority != null) {
                request.mPriority = mPriority;
            }

            // Set api key header
            StringBuilder stringBuilder = new StringBuilder(ACCESS_KEY_TYPE);
            stringBuilder.append(computeCertificateFingerprint(mContext));
            stringBuilder.append(';');
            stringBuilder.append(mContext.getPackageName());
            stringBuilder.append(' ');
            stringBuilder.append(getValidatorFromManifest(mContext));
            request.mHeaders.put(ACCESS_KEY_HEADER, stringBuilder.toString());
            computeCertificateFingerprint(mContext);

            // Set the content type header
            request.mHeaders.put(CONTENT_TYPE_HEADER, CONTENT_TYPE);

            setAuthenticationHeader(request.mHeaders);

            request.setShouldCache(false);
            return request;
        }

        private String getValidatorFromManifest(Context context) {
            ApplicationInfo ai = null;
            try {
                ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return ai.metaData.getString(ACCESS_KEY_VALIDATOR_KEY);
        }

        private String computeCertificateFingerprint(Context context) {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            int flags = PackageManager.GET_SIGNATURES;

            PackageInfo packageInfo = null;

            try {
                packageInfo = pm.getPackageInfo(packageName, flags);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Signature[] signatures = packageInfo.signatures;

            final byte[] certRaw = signatures[0].toByteArray();

            String strResult = "";

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA1");
                md.update(certRaw);
                strResult = new String(md.digest());
                strResult = strResult.toUpperCase();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
            return strResult;
        }

        private void setAuthenticationHeader(Map<String, String> headers) {
            headers.put(AUTHENTICATION_HEADER, AUTHENTICATION_SCHEMA + Catalyze.INSTANCE
                    .getUserSessionToken());
        }
    }
}
