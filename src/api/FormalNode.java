package api;

public class FormalNode {
    private String pos;
    private int id;

    public FormalNode(Node n){
        this.pos=n.getLocation().x()+","+n.getLocation().y()+","+n.getLocation().z();
        this.id = n.getKey();
    }
}