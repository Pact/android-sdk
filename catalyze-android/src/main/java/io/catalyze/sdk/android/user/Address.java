package io.catalyze.sdk.android.user;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Geocode getGeocode() {
        return geocode;
    }

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
