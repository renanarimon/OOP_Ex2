import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = new DW_graph();

        try {
            JsonObject json = new JsonParser().parse(new FileReader(json_file)).getAsJsonObject();
            JsonArray E = json.getAsJsonArray("Edges");
            JsonArray V = json.getAsJsonArray("Nodes");

            for (JsonElement node : V){
                String[] pos = ((JsonObject) node).get("pos").getAsString().split(",");
                int id = ((JsonObject) node).get("id").getAsInt();
                GeoLocation location = new geo_Location(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]), Double.parseDouble(pos[2]));
                NodeData newN = new Node(location, id);
                ans.addNode(newN);
            }

            for (JsonElement edge: E){
                JsonObject e = (JsonObject) edge;
                ans.connect(e.get("src").getAsInt(), e.get("dest").getAsInt(), e.get("w").getAsDouble());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ans;
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }

    /**
     * This static function will run your GUI using the json file.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }

    public static void main(String[] args) {
        DirectedWeightedGraph dw = getGrapg("data/1000Nodes.json");
        DW_graph dw1 = (DW_graph) dw;

        DirectedWeightedGraphAlgorithms algo = new DW_graph_algo();
        System.out.println(dw);

        algo.init(dw);
//        System.out.println(algo.shortestPathDist(5, 900));
//        System.out.println(algo.center());
//        System.out.println(algo.shortestPath(2,10));



        List<NodeData> list = new LinkedList<>();
        Iterator<NodeData> iterator = dw.nodeIter();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
//        System.out.println(list);
        System.out.println(algo.tsp(list));

//        System.out.println();
//        System.out.println(algo.center());


//        for (NodeData n: algo.shortestPath(0,2)){
//            System.out.println(n.getKey()+","+n.getWeight());
//        }
//        System.out.println(algo.shortestPathDist(0,2));
    }
}