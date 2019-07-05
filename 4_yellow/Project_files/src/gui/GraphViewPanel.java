package src.gui;

import src.draw.*;
import src.source.*;
import javax.swing.*;
import java.awt.*;

public class GraphViewPanel extends JPanel {
    private Drawing graphDraw;
    private Graph graph;

    public GraphViewPanel(){
        graphDraw = new Drawing(graph);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphDraw.draw(g2);
        graphDraw.print(g2);
    }

    public Graph getGraph(){
        return graph;
    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }
}
