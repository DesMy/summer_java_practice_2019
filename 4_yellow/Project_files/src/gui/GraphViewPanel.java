package src.gui;

import src.draw.*;
import src.source.*;
import javax.swing.*;
import java.awt.*;

public class GraphViewPanel extends JPanel {
    private Drawing graphDraw;
    private Graph graph;
    private boolean key;

    public GraphViewPanel(){
        graphDraw = new Drawing();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //обязательная проверка
        if(graph == null)
            return;

        if(!key) {
            graphDraw.setCenterCoordinates(new Point(1500 / 2, 700 / 2));
            graphDraw.setGraph(graph);
        }
        graphDraw.draw(g2);
    }

    public Graph getGraph(){
        return graph;
    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }

    public Drawing getGraphDraw(){ return graphDraw; }


}
