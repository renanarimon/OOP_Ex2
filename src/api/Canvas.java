package api;/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */

import java.awt.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class Canvas extends JFrame implements ActionListener, MouseListener {
    DirectedWeightedGraph graph;

    final double EPSILON = 1E-5;
    int Width = 500;
    int Height = 500;
    int R = 5;

    JTextField fieldSrc, filedDest, filedWeight;
    JPanel scanPanel, graphPanel;
    int src, dest;
    double weight;
    boolean PaintShortedPath = false;
    boolean AfterLoad=false;
    List<NodeData> path;
    double dist;
    JButton shortedPathBtn;
    JButton addEdgeBtn;
    JButton removeEdgeBtn;

    MenuBar menuBar;
    MenuItem Edit, geo, save, load, is_connected, shorted_path, shorted_path_distance, center, tsp;
    Menu menu, File, Algo;
    NodeData centerOn = null;

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
        this.addMouseListener(this);
        scaleGarph();
        setMenu();
        setGraphPanel();
        setScanPanel();
    }

    private void setMenu() {
        menuBar = new MenuBar();
        menu = new Menu("Menu");

        Edit = new MenuItem("Edit Graph");
        geo = new MenuItem("Show/hide Geo Location");
        save = new MenuItem("Save");
        load = new MenuItem("load");
        is_connected = new MenuItem("is connected");
        shorted_path = new MenuItem("shorted path");
        shorted_path_distance = new MenuItem("shorted path");
        center = new MenuItem("center point");
        tsp = new MenuItem("tsp");
        center.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        shorted_path.addActionListener(this);
        shorted_path_distance.addActionListener(this);
        is_connected.addActionListener(this);

        File = new Menu("File");
        Algo = new Menu("Algorithm");
        File.add(save);
        File.add(load);
        Algo.add(is_connected);
        Algo.add(shorted_path_distance);
        Algo.add(shorted_path);
        Algo.add(center);
        Algo.add(tsp);

//        menu.add(File);
//        menu.add(Algo);
//        menu.add(Edit);
//        menu.add(geo);
        menuBar.add(File);
        menuBar.add(Algo);
//        menuBar.add(Edit);

        this.setMenuBar(menuBar);

    }

    private void setGraphPanel(){
        graphPanel = new JPanel();


    }

    private void setScanPanel() {
        scanPanel = new JPanel();
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
        scanPanel.setBounds(0, 0, 100, 100);

        this.add(scanPanel);

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
        Iterator<NodeData> iterN = graph.nodeIter();
        while (iterN.hasNext()) {
            DrawNode(iterN.next(), R, g2D);
        }
        Iterator<EdgeData> iterE = graph.edgeIter();
        while (iterE.hasNext()) {
            DrawEdge(iterE.next(), g2D);
        }
        if (centerOn != null) {
            g.setColor(Color.BLUE);
            g.fillOval((int) centerOn.getLocation().x() - R, (int) centerOn.getLocation().y() - R, R * 3, R * 3);
            centerOn = null;
        }
        if (PaintShortedPath) {
            drawShortPath(g);
            path.clear();
            PaintShortedPath = false;
        }
        if(AfterLoad){
            runCan();
            AfterLoad=false;
        }
    }

    private void drawShortPath(Graphics g) {
        NodeData pre = null;
        int x_p = 0;
        int y_p = 0;
        System.out.println(path);
        for (NodeData n : path) {
            Color color = new Color(58, 213, 131);
            g.setColor(color);
            int x = (int) n.getLocation().x() - R;
            int y = (int) n.getLocation().y() - R;
            g.fillOval(x, y, R * 3, R * 3);
            if (pre != null) {
                g.drawLine(x + R, y + R, x_p, y_p);
            }
            pre = n;
            x_p = x + R;
            y_p = y + R;
        }
    }

    public void DrawNode(NodeData node, int r, Graphics2D g) {
        GeoLocation loc = node.getLocation();
        Node node1 = (Node) node;
        GeoLocation oldLoc = node1.getOldLocation();
        Color pink = new Color(219, 17, 111);
        g.setColor(Color.BLACK);
        g.drawString("" + node.getKey(), (int) loc.x(), (int) (loc.y() - 10));
        String pos = oldLoc.toString();
        g.drawString(pos, (int) loc.x() + 9, (int) loc.y() + 3);
        g.setColor(pink);
        g.fillOval((int) loc.x() - r, (int) loc.y() - r, r * 2, r * 2);

    }

    private void DrawEdge(EdgeData edge, Graphics2D g) {
        Geo_Location locSrc = (Geo_Location) graph.getNode(edge.getSrc()).getLocation();
        Geo_Location locDest = (Geo_Location) graph.getNode(edge.getDest()).getLocation();
        Shape line = new Line2D.Double(locSrc.x(), locSrc.y(), locDest.x(), locDest.y());
        g.setColor(Color.black);
        g.draw(line);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DirectedWeightedGraphAlgorithms graph_algo = new DW_graph_algo();
        if (e.getSource() == center) {
            graph_algo.init(graph);
            this.centerOn = graph_algo.center();
            repaint();
        } else if (e.getSource() == load) {
            JFileChooser fileChooser = new JFileChooser();
            //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
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

        } else if (e.getSource() == shortedPathBtn) {
            src = Integer.parseInt(fieldSrc.getText());
            dest = Integer.parseInt(filedDest.getText());
            System.out.println(src);
            graph_algo.init(graph);
            dist = graph_algo.shortestPathDist(src, dest);
            path = graph_algo.shortestPath(src, dest);
            PaintShortedPath = true;
            repaint();
        } else if (e.getSource() == addEdgeBtn) {
            src = Integer.parseInt(fieldSrc.getText());
            dest = Integer.parseInt(filedDest.getText());
            weight = Double.parseDouble(filedWeight.getText());
            EdgeData edge = new Edge(src, dest, weight);
            graph.connect(src, dest, weight);
            graph_algo.init(graph);
            System.out.println(graph.getNode(src));
            DrawEdge(edge, (Graphics2D) getGraphics());
            fieldSrc.setText("");
            filedDest.setText("");
            filedWeight.setText("");
        } else if (e.getSource() == removeEdgeBtn) {
            src = Integer.parseInt(fieldSrc.getText());
            dest = Integer.parseInt(filedDest.getText());
            graph.removeEdge(src, dest);
            graph_algo.init(graph);
            System.out.println(graph);
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
        int x = e.getX();
        int y = e.getY();

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




