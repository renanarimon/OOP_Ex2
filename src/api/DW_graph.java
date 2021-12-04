package api;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DW_graph implements DirectedWeightedGraph {
    HashMap<Integer, NodeData> nodes; // (key, node)
    HashMap<Integer, HashMap<Integer, EdgeData>> children; //(src (dest, edge) , node =src
    HashMap<Integer, HashMap<Integer, EdgeData>> parents; // (dest (src, edge) , node =dest

    public DW_graph() {
        this.nodes = new HashMap<>();
        this.children = new HashMap<>();
        this.parents = new HashMap<>();
    }

    public DW_graph(HashMap<Integer, NodeData> nodes, HashMap<Integer, HashMap<Integer, EdgeData>> children, HashMap<Integer, HashMap<Integer, EdgeData>> parents, int MC){
        this.nodes = nodes;
        this.children = children;
        this.parents = parents;
        this.MC = MC;
    }

    public HashMap<Integer, NodeData> getNodes() {
        return nodes;
    }

    public HashMap<Integer, HashMap<Integer, EdgeData>> getChildren() {
        return children;
    }

    public HashMap<Integer, HashMap<Integer, EdgeData>> getParents() {
        return parents;
    }


    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return children.get(src).get(dest);
    }

    /**
     * initialize
     *
     * @param n
     */
    @Override
    public void addNode(NodeData n) {
        nodes.put(n.getKey(), n);
        HashMap<Integer, EdgeData> GoTo = new HashMap<>();
        HashMap<Integer, EdgeData> ComeFrom = new HashMap<>();
        this.children.put(n.getKey(), GoTo);
        this.parents.put(n.getKey(), ComeFrom);
    }

    /**
     * initialize new edge
     * add edge to 'children'
     * add opposite edge to 'parents'
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        Edge edge = new Edge(src, dest, w);
        this.children.get(src).put(dest, edge);
        this.parents.get(dest).put(src, edge);
    }

    /**
     * Iterator on nodes
     *
     * @return
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    /**
     * Iterator on edges (children)
     *
     * @return
     */
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
        NodeData node = nodes.get(key);
        Iterator<EdgeData> iter = this.edgeIter();
        while (iter.hasNext()) {
            EdgeData tmp = iter.next();
            int src = tmp.getSrc();
            int dest = tmp.getDest();
            if (src == key || dest == key) {
                removeEdge(src, dest);
            }
        }
        nodes.remove(key);
        return node;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edge = children.get(src).get(dest);
        children.get(src).remove(dest);
        parents.get(dest).remove(src);
        return edge;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        int count = 0;
        Iterator<EdgeData> iter = edgeIter();
        while (iter.hasNext()) {
            count++;
            iter.next();
        }
        return count;
    }

    @Override
    public int getMC() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Vertices: " + nodeSize() + " Edges: " + edgeSize() + " MC: " + getMC() + "\n");
        for (int key : nodes.keySet()) {
            s.append(key).append(": ");
            for (EdgeData e : children.get(key).values()) {
                s.append(e);
            }
            s.append("\n");
        }
        return s.toString();
    }
}
