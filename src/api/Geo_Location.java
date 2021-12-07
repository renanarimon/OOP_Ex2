package api;

import api.GeoLocation;

import java.text.DecimalFormat;

public class Geo_Location implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public Geo_Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
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
        DecimalFormat df = new DecimalFormat("#.#####");
        String x1 = df.format(x);
        String y1 = df.format(y);
        return x1 + "," + y1 + "," + z;
    }
}
