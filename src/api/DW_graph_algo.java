package api;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DW_graph_algo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    public DW_graph_algo() {
        this.graph = new DW_graph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }


    @Override
    public DirectedWeightedGraph copy(){
        DirectedWeightedGraph g = new DW_graph();
        Iterator<NodeData> iterNodes = graph.nodeIter();
        while (iterNodes.hasNext()){
            g.addNode(iterNodes.next());
        }
        Iterator<EdgeData> iterEdges = graph.edgeIter();
        while (iterEdges.hasNext()){
            EdgeData e = iterEdges.next();
            g.connect(e.getSrc(), e.getDest(), e.getWeight());
        }

        return g;
    }

    /**
     * BFS on graph & Transpose graph.
     * if in one of them a V is unvisited (0) --> return false
     *
     * @return true iff graph is strongly connected
     */
    @Override
    public boolean isConnected() {
        Iterator<NodeData> iterN = graph.nodeIter();
        NodeData start = iterN.next();

        int[] visited = BFS(graph, start);
        for (int v : visited) {
            if (v == 0) {
                return false;
            }
        }
        visited = BFS(transpose(), start);
        for (int v : visited) {
            if (v == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * BFS - search on graph
     *
     * @param g     - graph to search (can be transposed)
     * @param start - Node to start search
     * @return int[] VISITED:
     * 1 if visited
     * 0 if not visited
     */
    public int[] BFS(DirectedWeightedGraph g, NodeData start) {
        int[] VISITED = new int[g.nodeSize()];
        Queue<NodeData> queue = new LinkedList<>();
        queue.add(start);
        VISITED[start.getKey()] = 1;
        while (!queue.isEmpty()) {
            NodeData curr = queue.poll();
            Iterator<EdgeData> iter = g.edgeIter(curr.getKey());
            while (iter.hasNext()) {
                NodeData tmp = g.getNode(iter.next().getDest());
                if (VISITED[tmp.getKey()] == 0) {
                    VISITED[tmp.getKey()] = 1;
                    queue.add(tmp);
                }
            }
        }
        return VISITED;
    }

    /**
     * Reverse all edges to Transpose the graph
     * (swap children & parents)
     *
     * @return Graph Transpose
     */
    private DirectedWeightedGraph transpose() {
        DW_graph trans = (DW_graph) copy();
        HashMap<Integer, HashMap<Integer, EdgeData>> tmp = trans.getChildren();
        trans.setChildren(trans.getParents());
        trans.setParents(tmp);
        return trans;
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
//        if (isConnected()){
//
//        }
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        JSONObject obj = new JSONObject();
        JSONArray Nodes = new JSONArray();
        JSONArray Edges = new JSONArray();

        Iterator<NodeData> iterNode = graph.nodeIter();
        while (iterNode.hasNext()){
            NodeData tmpN = iterNode.next();
            JSONObject node = new JSONObject();
            try {
                node.put("pos", tmpN.getLocation().toString());
                node.put("id", tmpN.getKey());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Nodes.put(node);
        }

        Iterator<EdgeData> iterE = graph.edgeIter();
        while (iterE.hasNext()){
            EdgeData e = iterE.next();
            JSONObject edge = new JSONObject();
            try {
                edge.put("src", e.getSrc());
                edge.put("w", e.getWeight());
                edge.put("dest", e.getDest());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            Edges.put(edge);
        }
        try {
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.setPrettyPrinting();
//            Gson gson = gsonBuilder.create();
            obj.putOpt("Edges", Edges);
            obj.putOpt("Nodes", Nodes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert fileWriter != null;
            fileWriter.write(obj.toString());
            return true; // successful saved to file
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert fileWriter != null;
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false; // unsuccessful saved to file
    }

    @Override
    public boolean load(String file) {



        return false;
    }


}
