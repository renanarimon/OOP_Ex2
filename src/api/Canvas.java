package api;/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Canvas extends JFrame implements ActionListener, MouseListener {
    DirectedWeightedGraph graph;

    final double EPSILON = 1E-5;
    int Width = 700;
    int Height = 700;
    int R = 5;
    Color black = new Color(37, 21, 29, 255);
    Color pink= new Color(243, 9, 79);

    JTextField fieldSrc, filedDest, filedWeight;
    JPanel scanPanel;

    int src, dest;
    double weight;
    List<NodeData> path;
    List<NodeData> TSP;

    boolean AfterLoad=false;
    boolean PaintTSP= false;
    boolean PaintShortedPath=false;
    boolean removed = false;
    int isGeoloc = 0;
    JButton shortedPathBtn;
    JButton addEdgeBtn;
    JButton removeEdgeBtn;

    MenuBar menuBar;
    MenuItem Edit, geo, save, load, is_connected, removeNode, center, tsp, SgeoLocation, Sweight;
    Menu Show, File, Algo;
    NodeData centerOn = null;

    Color colorGry= new Color(54, 47, 47);


    public Canvas(DirectedWeightedGraph g) {
        this.graph = g;
        runCan();
    }


    public void runCan() {
        this.setName("renana & talya");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, Width - 100, Height - 100);
        this.setSize(Width, Height);
        this.setResizable(false);
        this.setBackground(colorGry);
        this.addMouseListener(this);
        scaleGarph();
        setMenu();
        setScanPanel();
    }

    private void setMenu() {
        menuBar = new MenuBar();

        Edit = new MenuItem("Edit Graph");
        geo = new MenuItem("Show/hide Geo Location");
        save = new MenuItem("Save");
        load = new MenuItem("load");
        is_connected = new MenuItem("is connected");
        removeNode = new MenuItem("remove node");
        center = new MenuItem("center point");
        tsp = new MenuItem("tsp");
        SgeoLocation = new MenuItem("geo location");

        center.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        removeNode.addActionListener(this);
        tsp.addActionListener(this);
        is_connected.addActionListener(this);
        SgeoLocation.addActionListener(this);

        File = new Menu("File");
        Algo = new Menu("Algorithm");
        Show = new Menu("Show/Hide");

        File.add(save);
        File.add(load);
        Algo.add(is_connected);
        Algo.add(removeNode);
        Algo.add(center);
        Algo.add(tsp);
        Show.add(SgeoLocation);

        menuBar.add(File);
        menuBar.add(Show);
        menuBar.add(Algo);
        this.setMenuBar(menuBar);

    }


    private void setScanPanel() {
        scanPanel = new JPanel();
        String title = "ENTER NUMBERS HERE";
        Border border = BorderFactory.createTitledBorder(title);
        scanPanel.setBorder(border);

        shortedPathBtn = new JButton("shorted Path");
        addEdgeBtn = new JButton("add Edge");
        removeEdgeBtn = new JButton("remove Edge");
        shortedPathBtn.addActionListener(this);
        addEdgeBtn.addActionListener(this);
        removeEdgeBtn.addActionListener(this);

        JLabel labSrc = new JLabel("src");
        JLabel labDest = new JLabel("Dest");
        JLabel labWeight = new JLabel("Weight");

        this.fieldSrc = new JTextField(3);
        this.filedDest = new JTextField(3);
        this.filedWeight = new JTextField(5);

        scanPanel.add(labSrc);
        scanPanel.add(fieldSrc);
        scanPanel.add(labDest);
        scanPanel.add(filedDest);
        scanPanel.add(labWeight);
        scanPanel.add(filedWeight);
        scanPanel.add(shortedPathBtn);
        scanPanel.add(addEdgeBtn);
        scanPanel.add(removeEdgeBtn);
        scanPanel.setBounds((int) (Width-Width*0.4), (int) (Height-Height*0.25), (int) (Width*0.4), (int) (Height*0.25));

        this.add(scanPanel);
        this.getContentPane().setLayout(null);
        fieldSrc.addActionListener(this);
    }

    /**
     * setLocation of all nodes
     */
    public void scaleGarph() {
        double maxx = this.maxX();
        double minx = this.minX();
        double maxy = this.maxY();
        double miny = this.minY();
        Iterator<NodeData> iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Node node = (Node) iter.next();
            Geo_Location loc = (Geo_Location) node.getLocation();

            double newX = (Width * (loc.x() - minx)) / (maxx - minx) + Width * 0.04;
            double newY = (Height * (maxy - loc.y())) / (maxy - miny) + Height * 0.2;
            Geo_Location gN = new Geo_Location(newX * 0.7, Height - newY * 0.7, 0);
            node.setLocation(gN);
        }
    }

    private double maxX() {
        Iterator<NodeData> iter = graph.nodeIter();
        double max = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            double tmp = iter.next().getLocation().x();
            if (tmp > max) {
                max = tmp;
            }
        }
        return max;
    }

    private double maxY() {
        Iterator<NodeData> iter = graph.nodeIter();
        double max = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            double tmp = iter.next().getLocation().y();
            if (tmp > max) {
                max = tmp;
            }
        }
        return max;
    }

    private double minX() {
        Iterator<NodeData> iter = graph.nodeIter();
        double min = Integer.MAX_VALUE;
        while (iter.hasNext()) {
            double tmp = iter.next().getLocation().x();
            if (tmp < min) {
                min = tmp;
            }
        }
        return min;
    }

    private double minY() {
        Iterator<NodeData> iter = graph.nodeIter();
        double min = Integer.MAX_VALUE;
        while (iter.hasNext()) {
            double tmp = iter.next().getLocation().y();
            if (tmp < min) {
                min = tmp;
            }
        }
        return min;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponents(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2D = (Graphics2D) g;
        Iterator<EdgeData> iterE = graph.edgeIter();
        while (iterE.hasNext()) {
            DrawEdge(iterE.next(), g2D);
        }
        Iterator<NodeData> iterN = graph.nodeIter();
        while (iterN.hasNext()) {
            DrawNode(iterN.next(), R, g2D);
        }

        if (isGeoloc ==1){
            iterN = graph.nodeIter();
            while (iterN.hasNext()){
                Node node = (Node) iterN.next();
                GeoLocation loc = node.getLocation();
                GeoLocation oldLoc = node.getOldLocation();
                String pos = oldLoc.toString();
                g.drawString(pos, (int) loc.x() + 9, (int) loc.y() + 3);
            }
        }


        if (centerOn != null) {
            g.setColor(Color.YELLOW);
            g.fillOval((int) centerOn.getLocation().x() - R, (int) centerOn.getLocation().y() - R, R * 3, R * 3);
            centerOn = null;
        }
        if (PaintShortedPath) {
            drawPath(g,path);
            path.clear();
            PaintShortedPath=false;
        }
        if(AfterLoad){
            runCan();
            AfterLoad=false;
        }
        if(PaintTSP){
            drawPath(g,TSP);
            TSP.clear();
            PaintTSP=false;
        }
    }

    private void drawPath(Graphics g, List<NodeData> list) {
        NodeData pre = null;
        int x_p = 0;
        int y_p = 0;
        for (NodeData n : list) {
            Color color = new Color(58, 213, 131);
            g.setColor(color);
            int x = (int) n.getLocation().x() - R;
            int y = (int) n.getLocation().y() - R;
            if (pre != null) {
                Arrow arrow = new Arrow(x_p, y_p,x + R, y + R, color, 3);
                arrow.draw(g);
            }
            g.fillOval(x, y, R * 3, R * 3);
            pre = n;
            x_p = x + R;
            y_p = y + R;
        }
    }

    public void DrawNode(NodeData node, int r, Graphics2D g) {
        GeoLocation loc = node.getLocation();
        g.setColor(Color.black);
        g.drawString("" + node.getKey(), (int) loc.x(), (int) (loc.y() - 10));
        g.setColor(pink);
        g.fillOval((int) loc.x() - r, (int) loc.y() - r, r * 2, r * 2);

    }

    private void DrawEdge(EdgeData edge, Graphics2D g) {
        Geo_Location locSrc = (Geo_Location) graph.getNode(edge.getSrc()).getLocation();
        Geo_Location locDest = (Geo_Location) graph.getNode(edge.getDest()).getLocation();
        Arrow arrow = new Arrow((int)locSrc.x(), (int)locSrc.y(), (int)locDest.x(), (int)locDest.y(), black, 2);
        arrow.draw(g);
    }

    private void clearText(){
        fieldSrc.setText("");
        filedDest.setText("");
        filedWeight.setText("");
    }

    private boolean setJtext(){
        try {
            src = Integer.parseInt(fieldSrc.getText());
            dest = Integer.parseInt(filedDest.getText());
            return true;
        } catch (Exception exception){
            JOptionPane.showMessageDialog(this, "please enter new numbers!\n (src, dest)");
            clearText();
            return false;
        }
    }

    private boolean correctInput(){
        if (src<0 || dest<0 || src> graph.nodeSize() || dest> graph.nodeSize() || src==dest){
            JOptionPane.showMessageDialog(this, "nodes do not exist in the graph\n" +
                    "please enter new numbers!\n (src, dest, weight)");
            clearText();
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DirectedWeightedGraphAlgorithms graph_algo = new DW_graph_algo();
        if (e.getSource() == center) {
            graph_algo.init(graph);
            this.centerOn = graph_algo.center();
            if(centerOn==null){
                JOptionPane.showMessageDialog(this,"The graph is not connected,\ncan't find center");
            }
            else{
                repaint();
            }
        } else if (e.getSource() == load) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                graph_algo.load(selectedFile.getAbsolutePath());
                graph = graph_algo.getGraph();
                System.out.println(graph);
                AfterLoad=true;
                repaint();
            }
        } else if (e.getSource() == save) {
            graph_algo.init(graph);
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                graph_algo.save(fileToSave.getAbsolutePath()+".json");
            }

        } else if (e.getSource() == shortedPathBtn) {
            if (setJtext() && correctInput()) {
                graph_algo.init(graph);
                double dist = graph_algo.shortestPathDist(src, dest);
                path = graph_algo.shortestPath(src, dest);
                if (dist == -1) {
                    JOptionPane.showMessageDialog(this, "there is no path between" + src + " and " + dest);
                } else {
                    path = graph_algo.shortestPath(src, dest);
                    PaintShortedPath = true;
                    JOptionPane.showMessageDialog(this, "The dist between " + src + " and " + dest + " is: " + dist);
                    clearText();
                    repaint();
                }
            }


        } else if (e.getSource() == addEdgeBtn) {
            try {
                src = Integer.parseInt(fieldSrc.getText());
                dest = Integer.parseInt(filedDest.getText());
                weight = Double.parseDouble(filedWeight.getText());
            } catch (Exception exception){
                JOptionPane.showMessageDialog(this, "please enter new numbers!\n (src, dest, weight)");
                clearText();
                return;
            }
            if(correctInput()) {
                EdgeData edge = new Edge(src, dest, weight);
                graph.connect(src, dest, weight);
                graph_algo.init(graph);
                System.out.println(graph.getNode(src));
                DrawEdge(edge, (Graphics2D) getGraphics());
                clearText();
            }

        } else if (e.getSource() == removeEdgeBtn) {
            if (setJtext() && correctInput()){
                graph.removeEdge(src, dest);
                graph_algo.init(graph);
                System.out.println("graph:"+graph);
                System.out.println("algo:"+graph_algo.getGraph());
                removed = true;
                repaint();
            }

        }
        else if(e.getSource()==is_connected){
            graph_algo.init(graph);
            if(graph_algo.isConnected()) {
                JOptionPane.showMessageDialog(this, "This Graph is Connected!");
            }
            else {
                JOptionPane.showMessageDialog(this, "This Graph is not Connected!");
            }
        }
        else if(e.getSource()==tsp){
            graph_algo.init(graph);
            List<NodeData> input= new ArrayList<>();
            String getMessage = JOptionPane.showInputDialog(this, "Enter keys Of Cities: (Example:1 2 3 4)");
            String[] tmp= getMessage.split(" ");
            for(String n: tmp){
                input.add(graph.getNode(Integer.parseInt(n)));
            }
            TSP=graph_algo.tsp(input);
            PaintTSP=true;
            repaint();
        }
        else if (e.getSource() == removeNode){
            String getKey = JOptionPane.showInputDialog(this, "Enter key of Node to remove:");
            int key = Integer.parseInt(getKey);
            graph.removeNode(key);
            graph_algo.init(graph);
            removed = true;
            repaint();
        }
        else if (e.getSource() == SgeoLocation){
            isGeoloc = (isGeoloc == 0) ? 1 : 0;
            repaint();
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int key = graph.nodeSize();
        GeoLocation geo = new Geo_Location(e.getX(), e.getY(), 0.0);
        NodeData node = new Node(geo, key);
        graph.addNode(node);
        DrawNode(node, R, (Graphics2D) getGraphics());
        System.out.println(graph);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}




