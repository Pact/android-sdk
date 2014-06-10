package io.catalyze.sdk.android.user;

public class Geocode implements Comparable<Geocode> {

    private double latitude;
    private double longitude;

    public Geocode() { }

    public double getLatitude() {
        return latitude;
    }

    public Geocode setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Geocode setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public int compareTo(Geocode other) {
        double geocode = getLatitude() + getLongitude();
        double otherGeocode = other.getLatitude() + other.getLongitude();
        return Double.compare(geocode, otherGeocode);
    }
}
