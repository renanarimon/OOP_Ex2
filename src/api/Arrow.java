package api;/*
 *
 * @project Ex2
 * @auther Renana Rimon
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;


public class Arrow {
    private static final Polygon HEAD = new Polygon();

    static {
        HEAD.addPoint(0, 0);
        HEAD.addPoint(-5, -10);
        HEAD.addPoint(5, -10);
    }
    private final int x;
    private final int y;
    private final int endX;
    private final int endY;
    private final Color color;
    private final int thickness;

    public Arrow(int x, int y, int x2, int y2, Color color, int thickness) {
        super();
        this.x = x;
        this.y = y;
        this.endX = x2;
        this.endY = y2;
        this.color = color;
        this.thickness = thickness;
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        double angle = Math.atan2(endY - y, endX - x);

        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(thickness));

        g2D.drawLine(x, y, (int) (endX - 10 * Math.cos(angle)), (int) (endY - 10 * Math.sin(angle)));

        AffineTransform tx1 = g2D.getTransform();

        AffineTransform tx2 = (AffineTransform) tx1.clone();

        tx2.translate(endX, endY);
        tx2.rotate(angle - Math.PI / 2);

        g2D.setTransform(tx2);
        g2D.fill(HEAD);

        g2D.setTransform(tx1);
    }
}
