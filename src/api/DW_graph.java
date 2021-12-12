package api;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DW_graph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> nodes; // (key, node)
    private HashMap<Integer, HashMap<Integer, EdgeData>> children; //(src (dest, edge) , node =src
    private HashMap<Integer, HashMap<Integer, EdgeData>> parents; // (dest (src, edge) , node =dest
    private int MC;
    private int edgesCount;

    public DW_graph() {
        this.nodes = new HashMap<>();
        this.children = new HashMap<>();
        this.parents = new HashMap<>();
        this.edgesCount = 0;

    }

    public void setMC(int MC) {
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

    public void setChildren(HashMap<Integer, HashMap<Integer, EdgeData>> children) {
        this.children = children;
    }

    public void setParents(HashMap<Integer, HashMap<Integer, EdgeData>> parents) {
        this.parents = parents;
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
     * initialize new Node:
     * add node to 'nodes'
     * add key to 'children' and 'parents' for future connect edges.
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
        MC++;
    }

    /**
     * initialize new edge
     * add edge to 'children'
     * add opposite edge to 'parents'
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     * @throws Exception e - if not found a path
     */
    @Override
    public void connect(int src, int dest, double w) {
        try {
            EdgeData edgeC = new Edge(src, dest, w);
            EdgeData edgeP = new Edge(dest, src, w);
            this.children.get(src).put(dest, edgeC);
            this.parents.get(dest).put(src, edgeP);
            edgesCount++;
            MC++;
        } catch (Exception e) {
            System.err.println("src/dest doesn't found");
        }
    }

    /**
     * Iterator on nodes
     *
     * @return Iterator on nodes
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        try {
            return this.nodes.values().iterator();
        } catch (RuntimeException e) {
            throw new RuntimeException();
            //System.err.println("graph changed during iterate");
            //return null;
        }
    }

    /**
     * Iterator on edges (iterate on children hashmap):
     * all the edges are in the children hashmap- this is double hashmap so for iterate
     * it easily lets inside the edges to a Temporary hashmap and create an Iterator
     *
     * @return edge Iterator
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        ArrayList<EdgeData> allEdge = new ArrayList<>();
        try {
            for (int src : this.children.keySet()) {
                for (int dest : this.children.get(src).keySet()) {
                    allEdge.add(children.get(src).get(dest));
                }
            }
            return allEdge.iterator();
        } catch (Exception e) {
            System.err.println("graph changed during iterate");
            return null;
        }

    }

    /**
     * This iterator goes over all the edges that come out of a certain node
     * so we will go over the edges from a node within children hashmap
     *
     * @param node_id
     * @return edge Iterator
     */

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        try {
            if (this.children.containsKey(node_id)) {
                ArrayList<EdgeData> allEdge = new ArrayList<>();
                for (int dest : this.children.get(node_id).keySet()) {
                    allEdge.add(this.children.get(node_id).get(dest));
                }
                return allEdge.iterator();
            } else return null;
        } catch (Exception e) {
            System.err.println("graph changed during iterate");
            return null;
        }
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * with edge Iterator we can find the edges that needed to be removed
     *
     * @param key
     * @return the removed Node
     */


    @Override
    public NodeData removeNode(int key) {
        try {
            NodeData node = nodes.get(key);
            Iterator<EdgeData> iter = this.edgeIter();
            while (iter.hasNext()) {
                EdgeData tmp = iter.next();
                int src = tmp.getSrc();
                int dest = tmp.getDest();
                if (src == key || dest == key) {
                    removeEdge(src, dest);
                    MC--;
                }
            }
            nodes.remove(key);
            MC++;
            return node;
        } catch (Exception e) {
            System.out.println("incorrect key");
            return null;
        }
    }

    /**
     * Deletes the edge (with the given src and dest) from the graph -
     *
     * @param src, dest
     * @return the removed edge
     */

    @Override
    public EdgeData removeEdge(int src, int dest) {
        try {
            EdgeData edge = children.get(src).get(dest);
            children.get(src).remove(dest);
            parents.get(dest).remove(src);
            edgesCount--;
            MC++;
            return edge;
        } catch (Exception e) {
            System.out.println("not exist edge");
            return null;
        }

    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgesCount;
    }

    @Override
    public int getMC() {
        return this.MC;
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