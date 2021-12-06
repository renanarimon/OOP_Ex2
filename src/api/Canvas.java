package api;/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */
import java.awt.Shape;

import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Canvas extends JComponent {
    DirectedWeightedGraph G;
    double Norm;
    int R = 30;
    JMenuBar menuBar;
    JMenu menu, subMenue;
    JMenuItem menuItem;
    JRadioButtonMenuItem radioButtonMenuItem;
    JCheckBoxMenuItem checkBoxMenuItem;

    public Canvas(DirectedWeightedGraph g, int W, int H) {
        this.G=g;
        this.Norm = norm(W, H);
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("my_menu");
        menuBar.add(menu);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Iterator<NodeData> iter = G.nodeIter();
        while (iter.hasNext()){
            DrawNode(iter.next(), R, g2D);
        }
    }

    private double norm(int w, int h){
        double ans = Math.sqrt(w*w + h*h); //1800
        ans = ans/maxDist();
        System.out.println("ans: " + ans);
        return ans;
    }

    private double maxDist(){
        Iterator<NodeData> iter = G.nodeIter();
        double max = Integer.MIN_VALUE;
        while (iter.hasNext()){
            GeoLocation geo = iter.next().getLocation();
            Iterator<NodeData> iter2 = G.nodeIter();
            while (iter2.hasNext()){
                double tmp = geo.distance(iter2.next().getLocation());
                if(tmp > max){
                    max = tmp;
                }
            }
        }
        return  max;
    }

    private void DrawNode(NodeData node, int r, Graphics2D g){
        GeoLocation loc = node.getLocation();
        double x = loc.x() % (int)loc.x();
        double y = loc.y() % (int)loc.y();


        Shape circle = new Ellipse2D.Double(x*7000, y*7000, r*2, r*2);
        System.out.println(x*7000);

        g.draw(circle);
        g.drawString(""+node.getKey(),(int)loc.x(),(int)loc.y());
    }




}
