import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;

public class DW_graph implements DirectedWeightedGraph {
    HashMap<Integer, NodeData> nodes; // (key, node)
    HashMap<Integer, HashMap<Integer, EdgeData>> children; //(src (dest, edge) , node =src
    HashMap<Integer, HashMap<Integer, EdgeData>> parents; // (dest (src, edge) , node =dest

    public DW_graph() {
        this.nodes = new HashMap<>();
        this.children = new HashMap<>();
        this.parents = new HashMap<>();
    }

    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return children.get(src).get(dest);
    }

    @Override
    public void addNode(NodeData n) {
        nodes.put(n.getKey(), n);
        HashMap<Integer, EdgeData> GoTo = new HashMap<>();
        HashMap<Integer, EdgeData> ComeFrom = new HashMap<>();
        this.children.put(n.getKey(), GoTo);
        this.parents.put(n.getKey(), ComeFrom);
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge edge = new Edge(src, dest, w);
        this.children.get(src).put(dest,edge);
        this.parents.get(dest).put(src,edge);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
