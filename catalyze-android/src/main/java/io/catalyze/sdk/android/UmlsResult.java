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

    /**
     * Set the UMLS code of this entry.
     *
     * @param code the code to set
     */
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

    /**
     * Set the concept code of this entry.
     *
     * @param concept the concept to set
     */
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

    /**
     * Set the description of this entry.
     *
     * @param desc the description to set
     */
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

    /**
     * Return the object identifier (OID) of this entry.
     *
     * @param oid The OID to set
     */
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

    /**
     * Set the type of data (value set).
     *
     * @param valueset The type of data to set
     */
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

    /**
     * Set the vocabulary of this entry (e.g. SNOMEDCT, LOINC, etc).
     *
     * @param vocab The vocabulary name
     */
    public void setVocab(String vocab) {
        this.vocab = vocab;
    }
}
