package api;

public class FormalEdge {
    private int src;
    private double w;
    private int dest;

    public FormalEdge(Edge e){
        this.src = e.getSrc();
        this.w = e.getWeight();
        this.dest = e.getDest();
    }
}
