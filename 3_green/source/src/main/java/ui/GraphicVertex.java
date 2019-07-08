package ui;

import java.awt.*;

public class GraphicVertex extends GraphicElement
{
	private static final int RADIUS = 10;
	private static final Color COLOR = Color.GREEN;

	public GraphicVertex(char name, Point p) {
	    super(p);
		this.name = name;
	}

	@Override
	public void draw(Graphics g) {
		if(selected) {
		    g.setColor(Color.pink);

        } else {
            g.setColor(COLOR);
            g.fillOval(b.x, b.y, b.width, b.height);
        }
		g.fillOval(b.x, b.y, b.width, b.height);
		g.setColor(Color.BLACK);
		g.drawString(String.format("%s", name), p.x+10, p.y+10);
	}
	@Override
    public void updatePosition(Point d) {
        p.x += d.x;
        p.y += d.y;
        setBoundary();
    }
    @Override
	protected final void setBoundary() {
		b.setBounds(p.x - RADIUS, p.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
	}

	public final char name() {
		return name;
	}

	private final char name;
}