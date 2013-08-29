package io.catalyze.sdk.android.user;

/**
 * List of 50 US states and Washington D.C. Names and abbreviations match USPS standards.
 */
public enum State {

    /**
     * Alabama
     */
    AL("AL", "Alabama"),
    /**
     * Alaska
     */
    AK("AK", "Alaska"),
    /**
     * Arizona
     */
    AZ("AZ", "Arizona"),
    /**
     * Arkansas
     */
    AR("AR", "Arkansas"),
    /**
     * California
     */
    CA("CA", "California"),
    /**
     * Colorado
     */
    CO("CO", "Colorado"),
    /**
     * Connecticut
     */
    CT("CT", "Connecticut"),
    /**
     * Washington D.C.
     */
    DC("DC", "Washington D.C."),
    /**
     * Delaware
     */
    DE("DE", "Delaware"),
    /**
     * Florida
     */
    FL("FL", "Florida"),
    /**
     * Georgia
     */
    GA("GA", "Georgia"),
    /**
     * Hawaii
     */
    HI("HI", "Hawaii"),
    /**
     * Idaho
     */
    ID("ID", "Idaho"),
    /**
     * Illinois
     */
    IL("IL", "Illinois"),
    /**
     * Indiana
     */
    IN("IN", "Indiana"),
    /**
     * Iowa
     */
    IA("IA", "Iowa"),
    /**
     * Kansas
     */
    KS("KS", "Kansas"),
    /**
     * Kentucky
     */
    KY("KY", "Kentucky"),
    /**
     * Louisiana
     */
    LA("LA", "Louisiana"),
    /**
     * Maine
     */
    ME("ME", "Maine"),
    /**
     * Maryland
     */
    MD("MD", "Maryland"),
    /**
     * Massachusetts
     */
    MA("MA", "Massachusetts"),
    /**
     * Michigan
     */
    MI("MI", "Michigan"),
    /**
     * Minnesota
     */
    MN("MN", "Minnesota"),
    /**
     * Mississippi
     */
    MS("MS", "Mississippi"),
    /**
     * Missouri
     */
    MO("MO", "Missouri"),
    /**
     * Montana
     */
    MT("MT", "Montana"),
    /**
     * Nebraska
     */
    NE("NE", "Nebraska"),
    /**
     * Nevada
     */
    NV("NV", "Nevada"),
    /**
     * New Hampshire
     */
    NH("NH", "New Hampshire"),
    /**
     * New Mexico
     */
    NM("NM", "New Mexico"),
    /**
     * New York
     */
    NY("NY", "New York"),
    /**
     * New Jersey
     */
    NJ("NJ", "New Jersey"),
    /**
     * North Carolina
     */
    NC("NC", "North Carolina"),
    /**
     * North Dakota
     */
    ND("ND", "North Dakota"),
    /**
     * Ohio
     */
    OH("OH", "Ohio"),
    /**
     * Oklahoma
     */
    OK("OK", "Oklahoma"),
    /**
     * Oregon
     */
    OR("OR", "Oregon"),
    /**
     * Pennsylvania
     */
    PA("PA", "Pennsylvania"),
    /**
     * Puerto Rico
     */
    PR("PR", "Puerto Rico"),
    /**
     * Rhode Island
     */
    RI("RI", "Rhode Island"),
    /**
     * South Carolina
     */
    SC("SC", "South Carolina"),
    /**
     * South Dakota
     */
    SD("SD", "South Dakota"),
    /**
     * Tennessee
     */
    TN("TN", "Tennessee"),
    /**
     * Texas
     */
    TX("TX", "Texas"),
    /**
     * Utah
     */
    UT("UT", "Utah"),
    /**
     * Vermont
     */
    VT("VT", "Vermont"),
    /**
     * Virginia
     */
    VA("VA", "Virginia"),
    /**
     * Washington
     */
    WA("WA", "Washington"),
    /**
     * West Virginia
     */
    WV("WV", "West Virginia"),
    /**
     * Wisconsin
     */
    WI("WI", "Wisconsin"),
    /**
     * Wyoming
     */
    WY("WY", "Wyoming");

    private final String abbr;

    private final String name;

    private State(final String abbr, final String name) {
        this.abbr = abbr;
        this.name = name;
    }

    /**
     * The public abbreviation of the state. Matches the official USPS nomenclature.
     *
     * Example: The abbreviation of Massachusetts, USA would be "MA".
     *
     * @return The public abbreviation of the state. Should never be null or empty.
     */
    public String getAbbr() {
        return this.abbr;
    }

    /**
     * The common name of the state.
     *
     * Example: The name of Massachusetts, USA would be "Massachusetts", not the official "The
     * Commonwealth of Massachusetts".
     *
     * @return The common name of the state. Should never be null or empty.
     */
    public String getName() {
        return this.name;
    }

}
