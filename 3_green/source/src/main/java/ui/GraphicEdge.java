package ui;

import java.awt.*;

public class GraphicEdge extends GraphicElement
{
	public GraphicEdge(GraphicVertex from, GraphicVertex to, int capacity, int flow) {
		super(new Point((from.p.x + to.p.x)/2, (from.p.y + to.p.y)/2));
		this.from = from;
		this.to = to;
		this.capacity = capacity;
		this.flow = flow;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(from.p.x, from.p.y, to.p.x, to.p.y);
		double arc = Math.atan2(from.p.x - to.p.x, from.p.y - to.p.y);
		g.drawLine(to.p.x, to.p.y, (int)(to.p.x+20*Math.sin(arc + Math.PI/12)), (int)(to.p.y+20*Math.cos(arc + Math.PI/12)));
		g.drawLine(to.p.x, to.p.y, (int)(to.p.x+20*Math.sin(arc - Math.PI/12)), (int)(to.p.y+20*Math.cos(arc - Math.PI/12)));
		g.drawString(String.format("{%d/%d}", capacity, flow), (from.p.x + to.p.x)/2, (from.p.y + to.p.y)/2);
	}
	@Override
	public void updatePosition(Point d) {
		from.p.x += d.x;
		from.p.y += d.y;
		to.p.x += d.x;
		to.p.y += d.y;
		p.x += d.x;
		p.y += d.x;
	}

	private final GraphicVertex from;
	private final GraphicVertex to;
	private int capacity;
	private int flow;
}