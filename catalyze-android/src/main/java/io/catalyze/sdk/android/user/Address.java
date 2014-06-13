package io.catalyze.sdk.android.user;

/**
 * Represents a location tied with a CatalyzeUser. A CatalyzeUser can have any number of addresses
 * associated with them. The type of address is represented by the type field. This is primarily
 * from the following list
 *
 * BA, Bad address
 * N, Birth (nee) (birth address, not otherwise specified)
 * BDL, Birth delivery location (address where birth occurred)
 * F, Country Of Origin
 * C, Current Or Temporary
 * B, Firm/Business
 * H, Home
 * L, Legal Address
 * M, Mailing
 * O, Office
 * P, Permanent
 * RH, Registry home. Refers to the information system, typically managed by a public health agency, that stores patient information such as immunization histories or cancer data, regardless of where the patient obtains services.
 * BR, Residence at birth (home address at time of birth)
 */
public class Address implements Comparable<Address> {

    private String type;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Geocode geocode;

    public Address() { }

    /**
     * @return the address type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the address type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return line 1 of the address, commonly the street number and name.
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Set line 1 of the address, commonly the street number and name.
     * @param addressLine1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return line 2 of the address, commonly an apartment/unit number.
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Set line 2 of the address, commonly an apartment/unit number.
     * @param addressLine2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city.
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return state.
     */
    public String getState() {
        return state;
    }

    /**
     * Set the state.
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return zip code.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Set the zip code.
     * @param zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country.
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the geocode object containing the latitude and longitude.
     */
    public Geocode getGeocode() {
        return geocode;
    }

    /**
     * Set the geocode object containing the latitude and longitude.
     * @param geocode
     */
    public void setGeocode(Geocode geocode) {
        this.geocode = geocode;
    }

    @Override
    public int compareTo(Address other) {
        String fullAddress = getType() + getAddressLine1() +
                getAddressLine2() + getCity() + getState() + getZipCode() +
                getCountry() + getGeocode();
        String otherFullAddress = other.getType() + other.getAddressLine1() +
                other.getAddressLine2() + other.getCity() + other.getState() +
                other.getZipCode() + other.getCountry() + other.getGeocode();
        return fullAddress.compareTo(otherFullAddress);
    }

}
