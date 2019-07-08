package io.desmy.graph;

import io.desmy.project.*;
import java.util.HashMap;

public class CoordinateGenerator {
    private Point headPoint;
    private int diam;
    private HashMap<Node, Point> list;
    private Node now;
    private Node head;
    private int offset = 50;
    private HashMap<Integer, Integer> off;

    public CoordinateGenerator(Bohr graphic) {
        off = new HashMap<>();
        list = new HashMap<>();
        headPoint = new Point(diam, diam);
        head = graphic.getHead();
        diam = 15;
        list.put(head, headPoint);
        off.put(1, 0);
        ForEach(head, 1);
    }

    private void ForEach(Node val, int level) {
        if(!off.containsKey(level+1))
            off.put(level+1, 0);
        val.getNextLevelNode().forEach((key, value) -> {
            int buf = off.get(level);
            Point v = new Point(headPoint.getX() + buf, headPoint.getY() + offset*level);
            off.replace(level, buf+offset);
            list.put(value, v);
            ForEach(value, level + 1);
        });
    }

    public Node getH() { return head; }
    public Point getHead() { return headPoint; }
    public int getDiam() { return diam; }
    public HashMap<Node, Point> getList() { return list; }
}
