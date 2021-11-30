package api;

import api.GeoLocation;

public class geo_Location implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public geo_Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return 0;
    }

    @Override
    public double y() {
        return 0;
    }

    @Override
    public double z() {
        return 0;
    }

    @Override
    public double distance(GeoLocation g) {
        return 0;
    }

    @Override
    public String toString() {
        return "geoLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
