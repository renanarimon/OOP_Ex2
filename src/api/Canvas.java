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
import java.util.Iterator;

public class Canvas extends JFrame implements ActionListener, MouseListener {
    final double EPSILON = 1E-5;
    int Width = 500;
    int Height = 500;
    DirectedWeightedGraph graph;
    int R = 5;
    Graphics graphics;
    MenuBar menuBar;
    MenuItem Edit, geo, save, load, is_connected, shorted_path, shorted_path_distance, center, tsp;
    Menu menu, File, Algo;

    public Canvas(DirectedWeightedGraph g) {
        this.graph = g;
        graphics = getGraphics();
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

        menu.add(File);
        menu.add(Algo);
        menu.add(Edit);
        menu.add(geo);
        menuBar.add(menu);
        setMenuBar(menuBar);
//        menuBar.setVisible(true);

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
    }


    private void DrawNode(NodeData node, int r, Graphics2D g) {
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
        if (e.getSource() == center) {
//            NodeData cen=
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        int key = graph.nodeSize();
        GeoLocation geo = new Geo_Location(e.getX(), e.getY(), 0.0);
        NodeData node = new Node(geo, key);
        graph.addNode(node);
        DrawNode(node, R, (Graphics2D) getGraphics());
        System.out.println(node);
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




