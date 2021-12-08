package api;

import api.DirectedWeightedGraphAlgorithms;

import java.util.Iterator;

public class FormalGraph {
    FormalEdge[] Edges;
    FormalNode[] Nodes;

    public FormalGraph(DirectedWeightedGraphAlgorithms g){
        Edges = new FormalEdge[g.getGraph().edgeSize()];
        Iterator<EdgeData> itr = g.getGraph().edgeIter();
        for (int i = 0; i < Edges.length; i++) {
            Edges[i] = new FormalEdge((Edge) itr.next());
        }
        Iterator<NodeData> itr2 = g.getGraph().nodeIter();
        Nodes = new FormalNode[g.getGraph().nodeSize()];
        for (int i = 0; i < Nodes.length; i++) {
            Nodes[i] = new FormalNode((Node) itr2.next());
        }
    }
}
