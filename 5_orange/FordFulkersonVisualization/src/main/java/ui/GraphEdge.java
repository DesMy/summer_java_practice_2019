/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import model.Edge;

/**
 *
 * @author theph
 */
public class GraphEdge extends GraphElement {

    private Edge data;

    private GraphVertex v1, v2;

    public GraphEdge(JPanel parent, GraphVertex v1, GraphVertex v2, Edge data) {
        super(parent);
        this.data = data;
        this.v1 = v1;
        this.v2 = v2;
        setLocation(0, 0);
        setSize(9999, 9999);
    }

    @Override
    public void paint(Graphics g) {
        double angle = Math.atan2(v2.getY() - v1.getY(), v2.getX() - v1.getX());
        int length = (int) (Point.distance(v1.getX(), v1.getY(), v2.getX(), v2.getY()) - 2 * radius);
        if (data.getPrevFlow() == data.getFlow()) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.red);
        }

        double offsetX = radius * Math.cos(angle);
        double offsetY = radius * Math.sin(angle);

        AffineTransform tx = new AffineTransform();
        tx.setToIdentity();
        tx.translate(v2.getX() + radius - offsetX, v2.getY() + radius - offsetY);
        tx.rotate((angle - Math.PI / 2));

        //arrow line
        Point2D p0 = new Point(0, -length);
        
        //arrow head
        Point2D p1 = new Point(0, 0);
        Point2D p2 = new Point(-5, -10);
        Point2D p3 = new Point(5, -10);
        
        //text location
        Point2D p4 = new Point(10, -length/2);

        p0 = tx.transform(p0, null);
        p1 = tx.transform(p1, null);
        p2 = tx.transform(p2, null);
        p3 = tx.transform(p3, null);
        p4 = tx.transform(p4, null);

        // draw arrow
        g.drawLine((int)p0.getX(), (int)p0.getY(), (int)p1.getX(), (int)p1.getY());
        g.fillPolygon(new int[]{(int) p1.getX(), (int) p2.getX(), (int) p3.getX()}, new int[]{(int) p1.getY(), (int) p2.getY(), (int) p3.getY()}, 3);
        // write text
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(toString(), (int)p4.getX(), (int)p4.getY());
    }

    public Edge getData() {
        return data;
    }

    public void setData(Edge data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (data != null) {
            if (data.getPrevFlow() == data.getFlow()) {
                return data.getFlow() + "/" + data.getCapacity();
            } else {
                return data.getPrevFlow() + "→" + data.getFlow() + "/" + data.getCapacity();
            }

        } else {
            return id + "";
        }
    }

    public GraphVertex getV1() {
        return v1;
    }

    public void setV1(GraphVertex v1) {
        this.v1 = v1;
    }

    public GraphVertex getV2() {
        return v2;
    }

    public void setV2(GraphVertex v2) {
        this.v2 = v2;
    }

}
