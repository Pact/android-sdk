package io.catalyze.sdk.android.user;

public class Mrn implements Comparable<Mrn> {

    private String institutionsId;
    private String mrn;

    public Mrn() { }

    public String getInstitutionsId() {
        return institutionsId;
    }

    public void setInstitutionsId(String institutionsId) {
        this.institutionsId = institutionsId;
    }

    public String getMrn() {
        return mrn;
    }

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
