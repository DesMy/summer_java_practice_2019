package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.*;

public class GraphPanel extends JPanel 
{
	private static final int WIDTH = 950;
	private static final int HEIGHT = 300;

	public GraphPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.WHITE);
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());

		elements = new ArrayList<>();
		mouseP = new Point(WIDTH/2, HEIGHT/2);
		selected = null;
	}

	public void addElement(GraphicElement el) {
		elements.add(el);
	}

	public void deleteElements() {
		elements.clear();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0,0, getWidth(), getHeight());
		for(GraphicElement el : elements) {
			el.draw(g);
		}
	}

	private ArrayList<GraphicElement> elements;
	private Point mouseP;
	private GraphicElement selected;

	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(selected != null) {
				selected.setSelected(false);
				selected = null;
			}
			e.getComponent().repaint();
		}
		@Override
		public void mousePressed(MouseEvent e) {
			mouseP = e.getPoint();
			for(GraphicElement v : elements) {
				if(v.contains(mouseP)) {
					selected = v;
					selected.setSelected(true);
					e.getComponent().repaint();
					break;
				}
			}
		}
	}

	private class MouseMotionHandler extends MouseMotionAdapter {
		Point delta = new Point();

		@Override
		public void mouseDragged(MouseEvent e) {
			if(selected != null) {
				delta.setLocation(e.getX() - mouseP.x, e.getY() - mouseP.y);
				e.getComponent().repaint();
				selected.updatePosition(delta);
				mouseP = e.getPoint();
			}
			e.getComponent().repaint();
		}
	}
}