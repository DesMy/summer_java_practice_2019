package ui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class GraphicEdge
{
	private final GraphicVertex from;
	private final GraphicVertex to;
	private int capacity;
	private int flow;


	public GraphicEdge(GraphicVertex from, GraphicVertex to, int capacity, int flow) {
		this.from = from;
		this.to = to;
		this.capacity = capacity;
		this.flow = flow;
	}

	public void draw(Graphics g) {
		if(getFlow() != 0)  g.setColor(Color.RED);
		else g.setColor(Color.BLACK);

		double angle = Math.atan2(to.getP().y - from.getP().y, to.getP().x - from.getP().x);
		double offsetX = 10*Math.cos(angle);
		double offsetY = 10*Math.sin(angle);
		int distance = (int)Math.round(from.getP().distance(to.getP()));

		AffineTransform t = new AffineTransform();
		t.setToIdentity();
		t.translate(from.getP().x, from.getP().y);
		t.rotate(angle);
		Point2D p = new Point(distance/2, 30);
		t.transform(p, p);
		Graphics2D g2d = (Graphics2D)g;
		QuadCurve2D qc2d = new QuadCurve2D.Double(
				from.getP().x,
				from.getP().y,
				p.getX(),
				p.getY(),
				to.getP().x,
				to.getP().y
		);
		g2d.draw(qc2d);

		t.setToIdentity();
		double arrowAngle = Math.atan2(p.getY() - to.getP().y, p.getX() - to.getP().x);
		t.translate(to.getP().x, to.getP().y);
		t.rotate(angle - Math.PI/2 - Math.PI/12);
		Point2D p1 = new Point(-5,-20);
		Point2D p2 = new Point(0,-10);
		Point2D p3 = new Point(5,-20);
		t.transform(p1, p1);
		t.transform(p2, p2);
		t.transform(p3, p3);
		g.fillPolygon(
				new int[]{
						(int)p1.getX(),
						(int)p2.getX(),
						(int)p3.getX()
				},
				new int[]{
						(int) p1.getY(),
						(int) p2.getY(),
						(int) p3.getY()
				},
				3
		);

		g.drawString(String.format("{%s/%s}", this.capacity, this.flow), (int)p.getX(), (int)p.getY());
	}

	public void updatePosition(Point d) {
		from.getP().x += d.x;
		from.getP().y += d.y;
		to.getP().x += d.x;
		to.getP().y += d.y;
	}

	public GraphicVertex getFrom() {
		return from;
	}

	public GraphicVertex getTo() {
		return to;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getFlow() {
		return flow;
	}

}