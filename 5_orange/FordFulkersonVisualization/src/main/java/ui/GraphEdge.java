/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Random;
import javax.swing.JPanel;
import model.Edge;

/**
 *
 * @author theph
 */
public class GraphEdge extends GraphElement implements VertexPositionChangedListener {

    private Edge data;

    private GraphVertex v1, v2;

    public GraphEdge(JPanel parent, GraphVertex v1, GraphVertex v2, Edge data) {
        super(parent);
        this.data= data;
        this.v1 = v1;
        this.v2 = v2;
        v1.addVertexPositionChangedListener(this);
        v2.addVertexPositionChangedListener(this);
        //recalculatePosition();
        setLocation(0, 0);
        setSize(9999, 9999);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw line
        g.setColor(Color.black);
        g.drawLine(v1.getX() - getX() + radius, v1.getY() - getY() + radius, v2.getX() - getX() + radius, v2.getY() - getY() + radius);

        // write text
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        int textX = (v1.getX() + v2.getX()) / 2 - getX() + radius;
        int textY = (v1.getY() + v2.getY()) / 2 - getY() + radius;

        g.drawString(toString(), textX, textY);

        //draw arrow
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 5);
        arrowHead.addPoint(-5, -5);
        arrowHead.addPoint(5, -5);

        double angle = Math.atan2(v2.getY() - v1.getY(), v2.getX() - v1.getX());
        double offsetX = radius * Math.cos(angle);
        double offsetY = radius * Math.sin(angle);

        AffineTransform tx = new AffineTransform();
        tx.setToIdentity();
        tx.translate(v2.getX() + radius - offsetX, v2.getY() + radius - offsetY);
        tx.rotate((angle - Math.PI / 2));

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setTransform(tx);
        g2.fill(arrowHead);
        g2.dispose();

    }

    @Override
    public void onPositionChanged(int newX, int newY) {
        //recalculatePosition();
        parent.repaint();
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
            return data.getFlow() + "/" + data.getCapacity();
        } else {
            return id + "";
        }
    }

}
