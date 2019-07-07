/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import javax.swing.JPanel;
import model.Vertex;

/**
 *
 * @author theph
 */
public class GraphVertex extends GraphElement {

    LinkedList<VertexPositionChangedListener> listeners;
    
    private Vertex data;
        //variables for drag and drop vertex
    private boolean dragging = false;
    private int mouseX = 0;
    private int mouseY = 0;
    private int dragStartX = 0;
    private int dragStartY = 0;
    
    public GraphVertex(JPanel parent, int x, int y, Vertex data) {
        super(parent);
        this.data = data;
        this.setLocation(x, y);
        this.setSize(radius * 4, radius * 2);
        listeners = new LinkedList<>();
        
                this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    GraphVertex.this.selected = !GraphVertex.this.selected;
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    mouseX = e.getXOnScreen();
                    mouseY = e.getYOnScreen();

                    dragStartX = getX();
                    dragStartY = getY();
                    
                    dragging = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 3) {
                    dragging = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    int deltaX = e.getXOnScreen() - mouseX;
                    int deltaY = e.getYOnScreen() - mouseY;

                    int newX = dragStartX + deltaX;
                    int newY = dragStartY + deltaY;
                    setLocation(newX, newY );
                    
                    for(VertexPositionChangedListener listener : listeners){
                        listener.onPositionChanged(newX, newY);
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }

        });
    }
    public void addVertexPositionChangedListener(VertexPositionChangedListener listener){
        listeners.add(listener);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!this.selected) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.red);
        }
        g.fillOval(0, 0, radius * 2, radius * 2);

        g.setColor(Color.black);
        g.drawString(toString(), radius, radius);
    }

    @Override
    public String toString() {
        if(data!=null) return data.getName()+"";
        else return id+"";
    }

    public Vertex getData() {
        return data;
    }

    public void setData(Vertex data) {
        this.data = data;
    }
    
    

    
}
