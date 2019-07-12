package ui;

import javax.swing.*;
import java.awt.*;

public abstract class GraphicElement extends JComponent
{
	protected GraphicElement(Point p) {
		selected = false;
		b = new Rectangle();
		this.p = p;
		setBoundary();
	}

	public void draw(Graphics g) {}
	public void updatePosition(Point d) {}
	protected void setBoundary() {
		b.setBounds(p.x, p.y, 1, 1);
	}
	public void setSelected(boolean f) {
		selected = f;
	}
	public boolean contains(Point p) {
		return b.contains(p);
	}
	public Point getPoint() {
		return p;
	}

	protected boolean selected;
	protected Rectangle b;
	protected Point p;
}