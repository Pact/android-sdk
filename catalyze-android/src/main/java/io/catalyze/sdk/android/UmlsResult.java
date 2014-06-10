package io.catalyze.sdk.android;

/**
 * Data wrapper class for Catalyze UMLS api responses
 */
public class UmlsResult {

	private String desc;
	private String code;
	private String vocab;
	private String valueset;
	private String oid;
	private String concept;

	public UmlsResult() { }

	/**
	 * Get the UMLS code of this entry.
	 * 
	 * @return The UMLS code
	 */
	public String getCode() {
		return code;
	}

    public void setCode(String code) {
        this.code = code;
    }

	/**
	 * Get the concept code of this entry.
	 * 
	 * @return The concept code
	 */
	public String getConcept() {
		return concept;
	}

    public void setConcept(String concept) {
        this.concept = concept;
    }

	/**
	 * Get the description of this entry.
	 * 
	 * @return The description
	 */
	public String getDesc() {
		return desc;
	}

    public void setDesc(String desc) {
        this.desc = desc;
    }

	/**
	 * Return the object identifier (OID) of this entry.
	 * 
	 * @return The OID
	 */
	public String getOID() {
		return oid;
	}

    public void setOid(String oid) {
        this.oid = oid;
    }

	/**
	 * Get the type of data (value set).
	 * 
	 * @return The type of data
	 */
	public String getValueset() {
		return valueset;
	}

    public void setValueset(String valueset) {
        this.valueset = valueset;
    }

	/**
	 * Get the vocabulary of this entry (e.g. SNOMEDCT, LOINC, etc).
	 * 
	 * @return The vocabulary name
	 */
	public String getVocab() {
		return vocab;
	}

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }
}
