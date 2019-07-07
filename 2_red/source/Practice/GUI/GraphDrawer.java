package Practice.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphDrawer extends JPanel {
    Graph graph;
    NodeDrawer nodeDrawer;
    EdgeDrawer edgeDrawer;
    Node node = null;
    private final InitEdge initEdge=new InitEdge(this);

    public GraphDrawer(Graph _graph) {
        graph = _graph;
        nodeDrawer = new NodeDrawer(graph);
        edgeDrawer = new EdgeDrawer(graph);
        addMouseMotionListener(new MyMove());
        addMouseListener(new MyMouse());
        addMouseWheelListener(new MyMouse());
    }

    @Override
    public void paint(Graphics g) {
        edgeDrawer.paint(g);
        nodeDrawer.paint(g);

    }

    public Node moving_vertices(Point point) {
        for (Node elm : graph.getNodes()) {
            if (elm.moving_vertices(point) != null)
                return elm;
        }
        return null;
    }
    public void CallWindowNode(MouseEvent e){
        JDialog dialog = new InitNode(this,e);
    }
    private class MyMouse extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1)
                node = moving_vertices(e.getPoint());
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getButton() == MouseEvent.BUTTON3) {
                node = moving_vertices(e.getPoint());
                if (node == null) {
                    CallWindowNode(e);
                }
                if (e.getClickCount()>1 && node!=null){
                    initEdge.addNodetoEdge(node);
                }
            }



        }


        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //Zoom in
            if (e.getWheelRotation() > 0) {
                for (Node node : graph.getNodes()) {
                    node.setPosX((int) (node.getPosX() + 1));
                    node.setPosY((int) (node.getPosY() + 1));
                    node.setDiameter((int) (node.getDiameter() + 1));

                }
                repaint();

            }
            //Zoom out
            if (e.getWheelRotation() < 0) {
                for (Node node : graph.getNodes()) {
                    if (node.getDiameter() > 2) {
                        node.setPosX((int) (node.getPosX() - 1));
                        node.setPosY((int) (node.getPosY() - 1));
                        node.setDiameter((int) (node.getDiameter() - 1));
                    }

                }
                repaint();
            }
        }
    }

    private class MyMove implements MouseMotionListener {

        public void mouseDragged(MouseEvent e) {
            if (node != null) {
                node.setPosY(e.getY());
                node.setPosX(e.getX());
                repaint();
            }
        }

        public void mouseMoved(MouseEvent e) {

        }

    }
}
