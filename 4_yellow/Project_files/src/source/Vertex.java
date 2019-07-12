package src.source;

import java.awt.*;

public class Vertex {
    private Point vertexCenter;
    private Color color;
    public final static int radius = 60;

    public Vertex() {
        vertexCenter = new Point();
        color = Color.CYAN;

    }

    public Vertex(int radius) {
        vertexCenter = new Point();
        color = Color.CYAN;

    }

    public Point getVertexCenter() {
        return vertexCenter;
    }

    public void setVertexCenters(int x, int y) {
        vertexCenter.x = x;
        vertexCenter.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }


}
