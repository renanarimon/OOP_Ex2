import api.EdgeData;

public class Edge implements EdgeData {
    private int src;
    private  double weight;
    private int dest;

    public Edge(int src, int dest, double weight) {
        this.src = src;
        this.weight = weight;
        this.dest = dest;
    }

    @Override
    public int getSrc() {
        return 0;
    }

    @Override
    public int getDest() {
        return 0;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
