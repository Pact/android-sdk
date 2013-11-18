package io.catalyze.sdk.android;

import org.json.JSONObject;


public class UmlsResult extends CatalyzeObject {

	private static final String DESCRIPTION = "desc";
	private static final String CODE = "code";
	private static final String VOCAB = "vocab";
	private static final String VALUESET = "valueset";
	private static final String OID = "oid";
	private static final String CONCEPT = "concept";

	UmlsResult(JSONObject json) {
		super(json);
	}

	public String getCode() {
		return mJson.optString(CODE, null);
	}

	public String getVocab() {
		return mJson.optString(VOCAB, null);
	}

	public String getDescription() {
		return mJson.optString(DESCRIPTION, null);
	}
	
	public String getValueset() {
		return mJson.optString(VALUESET, null);
	}
	
	public String getOID() {
		return mJson.optString(OID, null);
	}
	
	public String getConcept() {
		return mJson.optString(CONCEPT, null);
	}

}