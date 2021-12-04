package api;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    @Override
    public boolean isConnected() {
        return false;
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
