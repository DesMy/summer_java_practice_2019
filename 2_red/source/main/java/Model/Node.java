package Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private Color color = Color.PINK;
    private int posX;
    private int posY;
    private static int size = 30;
    private String name;
    private List<Edge> listEdge = new ArrayList<Edge>();

    public Node(String name, int posX, int posY) {
        JPanel.getDefaultLocale();
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public int getDiameter() {
        return size;
    }
    public void setDiameter(int size) {
        this.size=size;
    }

    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    public void setPosX(int x) {
        posX=x;
    }

    public void setPosY(int y) {
        posY=y;
    }

    public void addListEdge(Edge edge) {
        listEdge.add(edge);
    }

    public List<Edge> getListEdge() {
        return listEdge;
    }

    public void changeColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return this.color;
    }

    public Node moving_vertices(Point point) {
        int center_of_the_circleX = posX + size/2;
        int center_of_the_circleY = posY + size/2;
        if (Math.sqrt(Math.pow(center_of_the_circleX - point.x, 2) + Math.pow(center_of_the_circleY - point.y, 2)) <= size/2) return this;
        else return null;
    }

}
