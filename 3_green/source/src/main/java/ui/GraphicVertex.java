package ui;

import java.awt.*;
import java.util.ArrayList;

public class GraphicVertex
{
	private static final int RADIUS = 10;
	private Color COLOR;
	private final char name;
	private Point p;
	private Rectangle b;
	private ArrayList<GraphicEdge> edges;
	private boolean selected;


	public GraphicVertex(char name, Point p) {
		this.name = name;
		this.p = p;
		b = new Rectangle();
		setBoundary();
		COLOR = Color.GREEN;
		edges = new ArrayList<>();
		selected = false;
	}

	//Getters:
	public final char getName() {
		return name;
	}
	public Point getP() {
		return p;
	}
	public Rectangle getB() {
		return b;
	}
	public ArrayList<GraphicEdge> getEdges() {
		return edges;
	}
	//Setters:
	public void setSelected(boolean f) {
		selected = f;
	}

	public void draw(Graphics g) {

		if(selected) {
			g.setColor(Color.PINK);

		} else {
			g.setColor(COLOR);
			g.fillOval(b.x, b.y, b.width, b.height);
		}
		g.fillOval(b.x, b.y, b.width, b.height);
		g.setColor(Color.BLACK);
		g.drawString(String.format("%s", name), p.x, p.y);
	}

	public void updatePosition(Point d) {
		p.x += d.x;
		p.y += d.y;
		setBoundary();
	}

	private final void setBoundary() {
		b.setBounds(p.x - RADIUS, p.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
	}

	public boolean contains(Point p) {
		return b.contains(p);
	}

	public void setColor(Color color) {
		COLOR = color;
	}
}