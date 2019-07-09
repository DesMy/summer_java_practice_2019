package io.desmy.graph;

public class Point {
    private int pointX;
    private int pointY;

    public Point(int X, int Y) {
        pointX = X;
        pointY = Y;
    }

    public int getX() {
        return pointX;
    }
    public int getY() {
        return pointY;
    }
    public void setX(int X) { pointX = X; }
    public void setY(int Y) { pointY = Y; }
}
