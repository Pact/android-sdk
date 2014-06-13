package io.catalyze.sdk.android.user;

/**
 * Simple class used in Address to accurately represent a location with a longitude and latitude.
 */
public class Geocode implements Comparable<Geocode> {

    private double latitude;
    private double longitude;

    public Geocode() { }

    /**
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude.
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int compareTo(Geocode other) {
        double geocode = getLatitude() + getLongitude();
        double otherGeocode = other.getLatitude() + other.getLongitude();
        return Double.compare(geocode, otherGeocode);
    }
}
