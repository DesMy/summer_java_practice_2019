package src.draw;

import java.awt.*;
import javax.swing.*;
import src.source.*;

public class Drawing implements Drawable {
    private Graph graph;

    public Drawing(Graph graph){
        this.graph = graph;

    }

    @Override
    public void draw(Graphics2D g) {
        g.fillOval(500, 500, 150, 150);
    }

    public void print(Graphics2D g) {

    }
}

//view.setGraphics(new SwingGraphicsAdapter((Graphics2D) canvas.getGraphics()));
