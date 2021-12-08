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
    private static final Polygon ARROW_HEAD = new Polygon();

    static {
        ARROW_HEAD.addPoint(0, 0);
        ARROW_HEAD.addPoint(-5, -10);
        ARROW_HEAD.addPoint(5, -10);
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
        Graphics2D g2 = (Graphics2D) g;

        // Calcula o ângulo da seta.
        double angle = Math.atan2(endY - y, endX - x);

        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));

        // Desenha a linha. Corta 10 pixels na ponta para a ponta não ficar grossa.
        g2.drawLine(x, y, (int) (endX - 10 * Math.cos(angle)), (int) (endY - 10 * Math.sin(angle)));

        // Obtém o AffineTransform original.
        AffineTransform tx1 = g2.getTransform();

        // Cria uma cópia do AffineTransform.
        AffineTransform tx2 = (AffineTransform) tx1.clone();

        // Translada e rotaciona o novo AffineTransform.
        tx2.translate(endX, endY);
        tx2.rotate(angle - Math.PI / 2);

        // Desenha a ponta com o AffineTransform transladado e rotacionado.
        g2.setTransform(tx2);
        g2.fill(ARROW_HEAD);

        // Restaura o AffineTransform original.
        g2.setTransform(tx1);
    }
}
