package io.catalyze.sdk.android;

import org.json.JSONObject;

/**
 * Data wrapper class for Catalyze UMLS api responses
 * 
 */
public class UmlsResult extends CatalyzeObject {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4659873670773688366L;
	
	// Field names
	private static final String DESCRIPTION = "desc";
	private static final String CODE = "code";
	private static final String VOCAB = "vocab";
	private static final String VALUESET = "valueset";
	private static final String OID = "oid";
	private static final String CONCEPT = "concept";

	/**
	 * Create a UMLS entry associated with the given Catalyze instance. Uses the
	 * provided JSON data to set the values.
	 * 
	 * @param catalyze
	 *            An instance of Catalyze. Does not need to be authenticated.
	 * @param json
	 *            The JSON data to populate the entry with
	 */
	protected UmlsResult(Catalyze catalyze, JSONObject json) {
		super(catalyze);
		this.mJson = json;
	}

	/**
	 * Get the UMLS code of this entry.
	 * 
	 * @return The UMLS code
	 */
	public String getCode() {
		return mJson.optString(CODE, null);
	}

	/**
	 * Get the concept code of this entry.
	 * 
	 * @return The concept code
	 */
	public String getConcept() {
		return mJson.optString(CONCEPT, null);
	}

	/**
	 * Get the description of this entry.
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return mJson.optString(DESCRIPTION, null);
	}

	/**
	 * Return the object identifier (OID) of this entry.
	 * 
	 * @return The OID
	 */
	public String getOID() {
		return mJson.optString(OID, null);
	}

	/**
	 * Get the type of data (value set).
	 * 
	 * @return The type of data
	 */
	public String getValueset() {
		return mJson.optString(VALUESET, null);
	}

	/**
	 * Get the vocabulary of this entry (e.g. SNOMEDCT, LOINC, etc).
	 * 
	 * @return The vocabulary name
	 */
	public String getVocab() {
		return mJson.optString(VOCAB, null);
	}

}
