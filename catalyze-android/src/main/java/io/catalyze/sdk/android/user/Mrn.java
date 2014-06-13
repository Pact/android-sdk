package io.catalyze.sdk.android.user;

/**
 * A CatalyzeUser can have any number of MRNs (medical record numbers). They have two components,
 * the unique ID of an institution from the Catalyze API and the mrn unique to that institution.
 */
public class Mrn implements Comparable<Mrn> {

    private String institutionsId;
    private String mrn;

    public Mrn() { }

    /**
     * @return the institutions ID.
     */
    public String getInstitutionsId() {
        return institutionsId;
    }

    /**
     * Set the institutions ID.
     * @param institutionsId
     */
    public void setInstitutionsId(String institutionsId) {
        this.institutionsId = institutionsId;
    }

    /**
     * @return the medical record number unique to the given institution.
     */
    public String getMrn() {
        return mrn;
    }

    /**
     * Set the medical record number unique to the given institution.
     * @param mrn
     */
    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    @Override
    public int compareTo(Mrn other) {
        String fullString = getInstitutionsId() + getMrn();
        String otherFullString = other.getInstitutionsId() + other.getMrn();
        return fullString.compareTo(otherFullString);
    }

}
