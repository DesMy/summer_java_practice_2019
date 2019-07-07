package Practice.GUI;

import javax.swing.*;
import java.awt.*;

public class EdgeDrawer extends JPanel {

    Graph graph;
    /**
     * saves reference on graph
     */
    public EdgeDrawer(Graph _graph){
        graph = _graph;
    }

    /**
     * draws all graph edges
     */
    @Override
    public void paint(Graphics g) {
        for (Edge edge : graph.edges) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, edge.getStartNode().getDiameter()/3));
            g.drawLine(edge.getStartNode().getPosX()+edge.getStartNode().getDiameter()/2, edge.getStartNode().getPosY()+edge.getStartNode().getDiameter()/2,
                    edge.getEndNode().getPosX()+edge.getEndNode().getDiameter()/2,  edge.getEndNode().getPosY()+edge.getEndNode().getDiameter()/2);

            g.setFont(new Font("Arial", Font.BOLD, edge.getStartNode().getDiameter()/3));
            g.setColor(Color.RED);
            g.drawString(edge.getWeight().toString() + "|"+edge.getBandwidth().toString(),
                    (edge.getEndNode().getPosX() + edge.getStartNode().getPosX()) / 2 - 5 - edge.getWeight().toString().length()+edge.getBandwidth().toString().length(),
                    (edge.getEndNode().getPosY() + edge.getStartNode().getPosY()) / 2);
            
        }
    }
}
