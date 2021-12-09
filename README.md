# OOP_Ex2

main classes:
* **DW_graph** implement DirectedWeightedGraph:
  ####The class represents a basic graph structure, and allows:
  * add node
  * remove node
  * connect 2 node with edge
  * remove edge
  * Iterator
  #### Data structure
    * nodes
    * children - all edges in origin point
    * parents - all edges in reverse point 
  
* **DW_graph_algo** implement DirectedWeightedGraphAlgorithms:
  ####The class implements algorithms for use on the graph:
    * isConnected(): check if graph is strongly connected.
    * shortestPathDist(): find shortest path distance between 2 nodes.
    * shortestPath(): find shortest path between 2 nodes.
    * center(): find center node in graph (https://en.wikipedia.org/wiki/Graph_center)
    * tsp():Computes a list of consecutive nodes which go over all the nodes in cities.
  (https://en.wikipedia.org/wiki/Travelling_salesman_problem)

![img.png](img.png)

![img_1.png](img_1.png)



