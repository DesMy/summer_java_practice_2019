/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author theph
 */
public abstract class GraphElement extends JComponent {

    private static int id_cur = 0;
    protected int id;

    static final int radius = 20;
    
    protected boolean selected;
    
    protected JPanel parent;



    public GraphElement(JPanel parent) {
        super();
        this.parent = parent;
        this.id = id_cur;
        id_cur++;
        selected = false;
    }

    @Override
    public String toString() {
        return "{" + id + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
