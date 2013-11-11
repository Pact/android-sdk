//package io.catalyze.sdk.android.umls;
//
//import android.content.Context;
//
//import com.android.volley.Response;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.catalyze.sdk.android.CatalyzeRequest;
////import io.catalyze.sdk.android.customclasses.CustomClass;
//
///**
// * Created by mvolkhart on 8/26/13.
// */
//public class UmlsRequest {
//
//    /*
//        "https://api.catalyze.io/v1/umls/<phrase-as-object|code>/<vocab>/<lookupValue>"
//     */
//    private static final String URL = "https://api.catalyze.io/v1/umls/%s/%s/%s";
//
//    private static final String RESULTS = "results";
//
//    public enum Type {
//
//        PHRASE("phrase-as-object"), CODE("code");
//        private final String mValue;
//
//        private Type(String value) {
//            mValue = value;
//        }
//
//        @Override
//        public String toString() {
//            return mValue;
//        }
//    }
//
////    public static class Builder extends CatalyzeRequest.Builder<Object, List<UmlsResult>> {
////
////        public Builder(Context context, Type type, UMLS vocab, String query) {
////            super(context, String.format(URL, type.toString(), vocab.toString(), query));
////        }
////
//////        @Override
//////        public CatalyzeRequest.Builder<Object, List<UmlsResult>> setMethod(
//////                CatalyzeRequest.Method method) {
//////            super.setMethod(CatalyzeRequest.Method.RETRIEVE);
//////            return this;
//////        }
////
////        @Override
////        public CatalyzeRequest.Builder<Object, List<UmlsResult>> setContent(Object body) {
////            return this;
////        }
////
////        @Override
////        public CatalyzeRequest.Builder<Object, List<UmlsResult>> setListener(
////                final Response.Listener<List<UmlsResult>> listener) {
////            setJsonListener(new Response.Listener<JSONObject>() {
////                @Override
////                public void onResponse(JSONObject response) {
////                    JSONArray array = response.optJSONArray(RESULTS);
////                    int max = array.length();
////                    List<UmlsResult> results = new ArrayList<UmlsResult>(max);
////                    for (int i = 0; i < max; i++) {
////                        results.add(new UmlsResult(array.optJSONObject(i)));
////                    }
////                    listener.onResponse(results);
////                }
////            });
////            return this;
////        }
////    }
//}
