package api;

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
    private static final double INFINITY = Double.POSITIVE_INFINITY;


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
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph g = new DW_graph();
        Iterator<NodeData> iterNodes = graph.nodeIter();
        while (iterNodes.hasNext()) {
            g.addNode(iterNodes.next());
        }
        Iterator<EdgeData> iterEdges = graph.edgeIter();
        while (iterEdges.hasNext()) {
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
        List<NodeData> path = shortestPath(src, dest);
        if (path != null) {
            return graph.getNode(dest).getWeight();
        }

        return -1;

    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> visited = new ArrayList<>();
        dijkstra(src);
        Node curr = (Node) graph.getNode(dest);
        while (curr.getFather() != null){
            visited.add(curr);
            curr = (Node) curr.getFather();
        }
        visited.add(graph.getNode(src));
        Collections.reverse(visited);
        return visited;
    }

    /**
     * if adding the edge make the path shorter --> add edge.
     * change the node weight.
     *
     * @param e: new edge
     */
    private void relax(EdgeData e) {
        Node src = (Node) graph.getNode(e.getSrc());
        Node dest = (Node) graph.getNode(e.getDest());
        if (dest.getWeight() > (src.getWeight() + e.getWeight())) {
            dest.setWeight(src.getWeight() + e.getWeight());
            dest.setFather(src);
        }
    }

    private void dijkstra(int src) {
        Iterator<NodeData> iter = graph.nodeIter();
        while (iter.hasNext()) {
            Node node = (Node) iter.next();
            node.setFather(null);
            if (node.getKey() == src) {
                node.setWeight(0);
            } else {
                node.setWeight(INFINITY);
            }
        }
        PriorityQueue<NodeData> pq = new PriorityQueue<>(Comparator.comparing(NodeData::getWeight));
        iter = graph.nodeIter();
        while (iter.hasNext()) {
            pq.add(iter.next());
        }

        while (!pq.isEmpty()) {
            NodeData curr = pq.poll();
            Iterator<EdgeData> iterE = graph.edgeIter(curr.getKey());
            while (iterE.hasNext()) {
                relax(iterE.next());
            }
            PriorityQueue<NodeData> pq2 = new PriorityQueue<>(Comparator.comparing(NodeData::getWeight));
            pq2.addAll(pq);
            pq = pq2;
        }
    }


    private double maxShortPath(int src) {
        dijkstra(src);
        Iterator<NodeData> iter = getGraph().nodeIter();
        double maxW = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            double tmpW = iter.next().getWeight();
            if (tmpW > maxW) {
                maxW = tmpW;
            }
        }
        return maxW;
    }

    private NodeData minShortPath(int src, List<NodeData> cities) {
        dijkstra(src);
        double minW = Integer.MAX_VALUE;
        NodeData ans = graph.getNode(src);
        for (NodeData n : cities) {
            if (n.getWeight() < minW) {
                minW = n.getWeight();
                ans = n;
            }
        }
        return ans;
    }


    @Override
    public NodeData center() {
        if (!isConnected()) {
            return null;
        }
        Iterator<NodeData> iter1 = graph.nodeIter();
        NodeData nodeAns = graph.getNode(0);
        double minDist = Integer.MAX_VALUE;
        while (iter1.hasNext()) {
            NodeData tmpNode = iter1.next();
            double tmpMaxDist = maxShortPath(tmpNode.getKey());
            if (tmpMaxDist < minDist) {
                minDist = tmpMaxDist;
                nodeAns = tmpNode;
            }
        }
        return nodeAns;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (!isConnected()) {
            return null;
        }
        List<NodeData> ans = new LinkedList<>();
        NodeData currNode = cities.remove(0);
        ans.add(currNode);
        NodeData bestNode;
        while (!cities.isEmpty()) {
            bestNode = minShortPath(currNode.getKey(), cities);
            ans.add(bestNode);
            cities.remove(bestNode);
            currNode = bestNode;
        }
        return ans;
}


    @Override
    public boolean save(String file) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fw = new FileWriter(file);
            FormalGraph fg = new FormalGraph(this);
            gson.toJson(fg, fw);
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            DirectedWeightedGraph G = new DW_graph();
            JsonObject json = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
            JsonArray E = json.getAsJsonArray("Edges");
            JsonArray V = json.getAsJsonArray("Nodes");

            for (JsonElement node : V) {
                String[] pos = ((JsonObject) node).get("pos").getAsString().split(",");
                int id = Integer.parseInt(((JsonObject) node).get("id").getAsString());
                GeoLocation location = new Geo_Location(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]));
                NodeData newN = new Node(location, id);
                G.addNode(newN);
            }
            //run by json and convert it to Edges
            for (JsonElement edge : E) {
                JsonObject e = (JsonObject) edge;
                G.connect(e.get("src").getAsInt(), e.get("dest").getAsInt(), e.get("w").getAsDouble());
            }
            this.graph = G;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
