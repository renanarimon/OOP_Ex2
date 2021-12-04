package api;

import api.EdgeData;

public class Edge implements EdgeData {
    private  double weight;
    private int src,dest,tag;
    private String info;

    public Edge(int src, int dest, double weight) {
        this.src = src;
        this.weight = weight;
        this.dest = dest;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString() {
        return "("+getSrc()+"->"+getDest()+") "+"w:"+getWeight();
    }
}
