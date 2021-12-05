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
        double t1 = Math.pow(x() - g.x(), 2);
        double t2 = Math.pow(y() - g.y(), 2);
        double t3 = Math.pow(z() - g.z(), 2);
        return Math.sqrt(t1 + t2 + t3);
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
