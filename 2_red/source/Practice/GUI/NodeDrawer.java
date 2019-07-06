package Practice.GUI;

import javax.swing.*;
import java.awt.*;

public class NodeDrawer extends JPanel {

    Graph graph;
    /**
     * saves reference on graph
     */
    public NodeDrawer(Graph _graph){
        graph = _graph;
    }

    /**
     * draws all graph nodes
     */
    @Override
    public void paint(Graphics g) {
        for (Node node : graph.nodes) {

            g.setColor(node.getColor());
            g.fillOval(node.getPosX(), node.getPosY(), node.getDiameter(), node.getDiameter());
            g.setFont(new Font("Arial", Font.BOLD, node.getDiameter()/3));
            g.setColor(Color.RED);
            g.drawString(node.getName(), node.getPosX()+node.getDiameter()/4, node.getPosY() +node.getDiameter()/2+3);
        }
    }
}

