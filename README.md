# OOP EX2 -  Directed Weighted Graph

this asssinment has written by [Taliya Shitreet](https://github.com/taliyashitreet "Profile") and  [Renana Rimon](https://github.com/renanarimon "Profile") <br />
main classes:

## DW_graph implement DirectedWeightedGraph:
#### The class represents a basic graph structure, and allows:

- add node
- remove node
- connect 2 node with edge
- remove edge
- Iterator (one of Edges and one of Nodes)
###### Data structure:
- nodes
- children - all edges in origin point
- parents - all edges in reverse point
 
Example of graph string output:<br />
<sub>
  Vertices: 17 Edges: 36 MC: 53
  0: (0->16) w:1.3118716362419698(0->1) w:1.232037506070033
  1: (1->0) w:1.8635670623870366(1->2) w:1.8015954015822042
  2: (2->1) w:1.5784991011275615(2->3) w:1.0631605142699874(2->6) w:1.7938753352369698
  3: (3->2) w:1.440561778177153(3->4) w:1.2539385028794277
  4: (4->3) w:1.8418222744214585(4->5) w:1.1422264879958028
  5: (5->4) w:1.5855912911662344(5->6) w:1.734311926030133
  6: (6->2) w:1.8474047229605628(6->5) w:1.4964304236123005(6->7) w:1.237565124536135
  7: (7->6) w:1.5786081900467002(7->8) w:1.3717352984705653
  8: (8->7) w:1.2817370911337442(8->9) w:1.5328553219807337
  9: (9->8) w:1.9855087252581762(9->10) w:1.2861739185896588
  10: (10->9) w:1.5815006562559664(10->11) w:1.4962204797190428
  11: (11->10) w:1.3784147388591739(11->12) w:1.9316059913913906
  12: (12->11) w:1.0666986438224981(12->13) w:1.5484109702862576
  13: (13->12) w:1.823489852982211(13->14) w:1.011071987085077
  14: (14->13) w:1.3207562671517605(14->15) w:1.118950355920981
  15: (15->16) w:1.8726071511162605(15->14) w:1.635946027210021
  16: (16->0) w:1.4418017651347552(16->15) w:1.5677693324851103
</sup>


## DW_graph_algo implement DirectedWeightedGraphAlgorithms:

#### The class implements algorithms for use on the graph:

- isConnected(): check if graph is strongly connected.
- shortestPathDist(): find shortest path distance between 2 nodes.
- shortestPath(): find shortest path between 2 nodes.
- center(): find center node in graph
  [Graph_center](https://en.wikipedia.org/wiki/Graph_center)
- tsp():Computes a list of consecutive nodes which go over all the nodes in cities. 
  [Travelling_salesman_problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem)
- An example of TSP in our GUI :
![image](https://user-images.githubusercontent.com/77111035/145706446-64a457dd-563f-4363-9f80-f8b257c44d55.png)


## Canvas:
this class represents the GUI- extends JFrame and implements ActionListener <br />
 @Override :
 - paint
 - paintComponents (responsible of painting the whole GUI and repaint after Event)
 - actionPerformed (responsible to handle the whole Events)
 #### UML Diagram:
![image](https://user-images.githubusercontent.com/77111035/145707659-a911c416-245f-464a-913e-d24fc4152e75.png)

## Complexity Analysis:
| Method       |Description                                                                                                     | Conplexity                           |
|--------------|----------------------------------------------------------------------------------------------------------------|--------------------------------------|
|ShortestPath()| using dijkstra algorithm and  find the shortest path from a starting node to a target node in a weighted graph | O (V+E(logV)                         | 
|TSP()         | find the shortest possible route that visits every city exactly once and returns to the starting point         | O(k*(V+E(logV)) (k=mounts of cities) |  
| Center()     | the set of all vertices u where the greatest distance d(u,v) to other vertices v is minimal                    |O(V*(V+E(logV))                       |      

## RunTime for Large inputs:
![image](https://user-images.githubusercontent.com/77111035/145707734-1dab7974-723d-4808-a9b5-8ebd2ee7e3cd.png)
![image](https://user-images.githubusercontent.com/77111035/145707772-00ae8d05-d873-4379-969f-32614104f0ca.png)
