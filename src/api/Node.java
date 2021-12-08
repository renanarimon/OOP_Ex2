package api;



public class Node implements NodeData {
    private GeoLocation location;
    private GeoLocation oldLocation;
    private int key;
    private int tag;
    private double weight;
    private NodeData father;
    private String info;

    public Node(GeoLocation location, int key) {
        this.location = location;
        this.oldLocation = new Geo_Location(location.x(), location.y(), location.z());
        this.key = key;
        this.tag = 0;
        this.weight = 0.0;
        this.father = null;
        this.info = "";
    }

    public NodeData getFather() {
        return father;
    }

    public GeoLocation getOldLocation() {
        return oldLocation;
    }

    public void setFather(NodeData father) {
        this.father = father;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location=p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
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
        return key+"";
    }
}

