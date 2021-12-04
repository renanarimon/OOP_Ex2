import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

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
     * This static function will run your GUI using the json fime.
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
        DirectedWeightedGraph dw = getGrapg("data/G1.json");
        DW_graph dw1 = (DW_graph) dw;

        DirectedWeightedGraphAlgorithms algo = new DW_graph_algo();
        algo.init(dw);
        DirectedWeightedGraph d = algo.copy();
        DW_graph d1 = (DW_graph) d;

        d1.removeEdge(0, 16);
        dw.removeEdge(0,1);
        System.out.println(dw1.getChildren());
        System.out.println(d1.getChildren());






    }
}