package Controller;

import Model.Edge;
import Model.Graph;
import Model.Node;

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.util.LinkedList;

public class EdgeDrawer extends JPanel {

    public Graph graph;

    /**
     * saves reference on graph
     */
    public EdgeDrawer(Graph _graph) {
        graph = _graph;
    }

    public void setGraph(Graph _graph){
        graph = _graph;
    }

    public int[] Turn (int corn,int x,int y,int cenX,int cenY){
        int  []cord =new int[2];
        cord[0]=(int)((x-cenX)*Math.cos((corn*Math.PI)/180)-(y-cenY)*Math.sin((corn*Math.PI)/180))+cenX;
        cord[1]=(int)((x-cenX)*Math.sin((corn*Math.PI)/180)+(y-cenY)*Math.cos((corn*Math.PI)/180))+cenY;
        return cord;
    }
    public Object[] getCord(int x, int y, Node start){
        LinkedList<Integer> list=new LinkedList<Integer>();
        int vecX = x-(start.getPosX()+(start.getDiameter() / 2));
        int vecY =y-(start.getPosY()+(start.getDiameter() /2)) ;
        double vecSize = Math.sqrt((double) (Math.pow(vecX, 2) + Math.pow(vecY, 2)));
        int posX=(int)(vecX - vecX / vecSize*start.getDiameter() / 2 +start.getPosX() + start.getDiameter() / 2);
        int posY=(int)(vecY - vecY / vecSize*start.getDiameter() / 2 +start.getPosY()+ start.getDiameter() / 2);
        list.add(Turn(30,posX,posY,x,y)[0]);
        list.add(Turn(30,posX,posY,x,y)[1]);
        list.add(Turn(-30,posX,posY,x,y)[0]);
        list.add(Turn(-30,posX,posY,x,y)[1]);


        return list.toArray();
    }
    /**
     * draws all graph edges
     */
    @Override
    public void paint(Graphics g) {
        for (Edge edge : graph.edges) {
            g.setColor(edge.getColor());
            g.setFont(new Font("Arial", Font.BOLD, edge.getStartNode().getDiameter() / 3));
            g.drawLine(edge.getStartNode().getPosX() + edge.getStartNode().getDiameter() / 2, edge.getStartNode().getPosY() + edge.getStartNode().getDiameter() / 2,
                    edge.getEndNode().getPosX() + edge.getEndNode().getDiameter() / 2, edge.getEndNode().getPosY() + edge.getEndNode().getDiameter() / 2);
            int vecX = (edge.getEndNode().getPosX() + (edge.getEndNode().getDiameter() / 2))-(edge.getStartNode().getPosX() + (edge.getStartNode().getDiameter() / 2));
            int vecY =(edge.getEndNode().getPosY() + (edge.getEndNode().getDiameter() / 2))-(edge.getStartNode().getPosY() + (edge.getStartNode().getDiameter() / 2));
            double vecSize = Math.sqrt((double) (Math.pow(vecX, 2) + Math.pow(vecY, 2)));
            g.setColor(Color.BLACK);
            int posX=(int)(vecX - vecX / vecSize*edge.getStartNode().getDiameter() / 2 +edge.getStartNode().getPosX() + edge.getStartNode().getDiameter() / 2);
            int posY=(int) (vecY - vecY / vecSize *edge.getStartNode().getDiameter() / 2+ edge.getStartNode().getPosY() + edge.getStartNode().getDiameter() / 2);

            g.fillPolygon(new int[]{posX, (int)(getCord(posX,posY,edge.getStartNode())[0]),(int)(getCord(posX,posY,edge.getStartNode())[2])},new int[]{posY, (int)(getCord(posX,posY,edge.getStartNode())[1]),(int)(getCord(posX,posY,edge.getStartNode())[3])},3);
            g.setFont(new Font("Arial", Font.BOLD, edge.getStartNode().getDiameter() / 3));
            g.setColor(Color.RED);
            g.drawString(edge.getWeight().toString() + "|" + edge.getBandwidth().toString(),
                    (edge.getEndNode().getPosX() + edge.getStartNode().getPosX()) / 2 - 5 - edge.getWeight().toString().length() + edge.getBandwidth().toString().length(),
                    (edge.getEndNode().getPosY() + edge.getStartNode().getPosY()) / 2);

        }
    }
}
