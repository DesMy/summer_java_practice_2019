package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.event.*;

public class GraphPanel extends JPanel 
{
	private static final int WIDTH = 950;
	private static final int HEIGHT = 300;

	private final Frame frame;

	private ArrayList<GraphicVertex> vertices;

	private Point mouseP;
	private GraphicVertex selected;
	private char prevSelectedName;

	public GraphPanel(final Frame parent) {
		frame = parent;

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.WHITE);
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());

		vertices = new ArrayList<>();
		mouseP = new Point(WIDTH/2, HEIGHT/2);
		selected = null;
		prevSelectedName = '\0';
	}

	public Frame getFrame() {
		return frame;
	}

	public Point getMouseP() {
		return mouseP;
	}

	public void addVertex(GraphicVertex el) {
		vertices.add(el);
		if(el.getName() == vertices.get(0).getName() || el.getName() == vertices.get(1).getName()){
			el.setColor(Color.CYAN);
		}
	}

	public void addEdge(char from, char to, int capacity, int flow) {
		GraphicVertex _from = findVertex(from);
		GraphicVertex _to = findVertex(to);
		if(_from == null || _to == null) {
			return;
		}
		for(GraphicEdge e : _from.getEdges()) {
			if(e.getTo().getName() == _to.getName()) {
				getFrame().print("Edge from '" + _from.getName() + "' to '" + _to.getName() + "' already exists.");
				return;
			}
		}

		_from.getEdges().add(new GraphicEdge(_from, _to, capacity, flow));
	}

	public void deleteVertex(char name) {
		GraphicVertex v = findVertex(name);
		if(v != null) {
			for(GraphicVertex vt : vertices) {
				Iterator<GraphicEdge> it = vt.getEdges().iterator();
				while(it.hasNext()) {
					GraphicEdge e = it.next();
					if(e.getTo().getName() == v.getName()) {
						it.remove();
					}
				}
			}
			Iterator<GraphicEdge> it = v.getEdges().iterator();
			while(it.hasNext()) {
				GraphicEdge e = it.next();
				if(e.getTo().getName() == v.getName()) {
					it.remove();
				}
			}
			vertices.remove(v);
		}
	}

	public void deleteEdge(char from, char to) {
		GraphicVertex fr = findVertex(from);
		GraphicVertex t = findVertex(to);
		if(fr != null && t != null) {
			Iterator<GraphicEdge> it = fr.getEdges().iterator();
			while(it.hasNext()) {
				GraphicEdge e = it.next();
				if(e.getTo().getName() == t.getName()) {
					it.remove();
					break;
				}
			}
		}
	}

	public void clear() {
		vertices.clear();
	}

	public GraphicVertex findVertex(char name) {
		for(GraphicVertex v : vertices) {
			if(v.getName() == name) {
				return v;
			}
		}
		return null;
	}

	public ArrayList<GraphicVertex> getVertices() {
		return vertices;
	}

	private void doPop(MouseEvent e) {
		PopupMenu menu = new PopupMenu(this);
		if(selected == null) {
			menu.addVertexMenu.setEnabled(true);
			menu.addEdgeMenu.setEnabled(false);
			menu.deleteVertexItem.setEnabled(false);
			menu.deleteEdgeMenu.setEnabled(false);
		} else {
			menu.addVertexMenu.setEnabled(false);
			menu.addEdgeMenu.setEnabled(true);
			menu.deleteVertexItem.setEnabled(true);
			if(selected.getEdges().size() == 0) {
				menu.deleteEdgeMenu.setEnabled(false);
			} else {
				menu.deleteEdgeMenu.setEnabled(true);
			}
		}
		menu.show(e.getComponent(), e.getX(), e.getY());
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0,0, getWidth(), getHeight());
		for(GraphicVertex v : vertices) {
			for(GraphicEdge e : v.getEdges()) {
				e.draw(g);
			}
		}
		for(GraphicVertex v : vertices) {
			v.draw(g);
		}
	}

	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(selected != null) {
				selected.setSelected(false);
				prevSelectedName = selected.getName();
				selected = null;
			}
			e.getComponent().repaint();
		}
		@Override
		public void mousePressed(MouseEvent e) {
			mouseP = e.getPoint();
			for(GraphicVertex v : vertices) {
				if(v.contains(mouseP)) {
					selected = v;
					selected.setSelected(true);
					break;
				}
			}
			if(e.getButton() == MouseEvent.BUTTON3 && vertices.size() != 0) {
				doPop(e);
			}

			e.getComponent().repaint();
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

	class PopupMenu extends JPopupMenu {
		GraphPanel graphPanel;
		JMenu addVertexMenu = new JMenu("Add vertex...");
		JMenu addEdgeMenu = new JMenu("Add edge...");
		JMenuItem deleteVertexItem = new JMenuItem("Delete vertex");
		JMenu deleteEdgeMenu = new JMenu("Delete edge...");

		JTextField addVertexName = new JTextField(1);
		JList<String> addEdgeList = new JList<>();
		JTextField addEdgeCap = new JTextField(1);
		JList<String> deleteEdgeList = new JList<>();

		PopupMenu(GraphPanel parent) {
			graphPanel = parent;

			Font font = new Font("TimesNewRoman", Font.PLAIN, 11);
			addVertexMenu.setFont(font);
			addEdgeMenu.setFont(font);
			deleteVertexItem.setFont(font);
			deleteEdgeMenu.setFont(font);

			JLabel addVertexNameLabel = new JLabel("Name:");
			addVertexNameLabel.setFont(font);
			addVertexMenu.add(addVertexNameLabel);
			addVertexMenu.add(addVertexName);
			addEdgeMenu.add(addEdgeList);
			JLabel addEdgeNameLabel = new JLabel("Capacity:");
			addEdgeNameLabel.setFont(font);
			addEdgeMenu.add(addEdgeNameLabel);
			addEdgeMenu.add(addEdgeCap);
			deleteEdgeMenu.add(deleteEdgeList);

			add(addVertexMenu);
			add(addEdgeMenu);
			add(deleteVertexItem);
			add(deleteEdgeMenu);

			GroupLayout layout = new GroupLayout(this);
			setLayout(layout);


			layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(addVertexMenu)
					.addComponent(addEdgeMenu)
					.addComponent(deleteVertexItem)
					.addComponent(deleteEdgeMenu)
			);

			layout.linkSize(SwingConstants.HORIZONTAL, addVertexMenu, addEdgeMenu, deleteVertexItem, deleteEdgeMenu);

			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(addVertexMenu)
					.addComponent(addEdgeMenu)
					.addComponent(deleteVertexItem)
					.addComponent(deleteEdgeMenu)
			);

			addVertexName.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (addVertexName.getText().length() == 1 ) {
						graphPanel.getFrame().getController().restoreGraph();
						graphPanel.getFrame()
								.getController()
								.addVertex(addVertexName.getText().charAt(0), getMouseP());
						graphPanel.getFrame().repaint();
					}
				}
			});
			addVertexName.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (addVertexName.getText().length() >= 1 ) {
						e.consume();
					}
				}
			});

			addEdgeMenu.addMenuListener(new MenuListener() {
				@Override
				public void menuSelected(MenuEvent e) {
					String[] arr = new String[graphPanel.getVertices().size()];
					for(int i=0; i<graphPanel.getVertices().size(); ++i) {
						if(graphPanel.getVertices().get(i).getName() == prevSelectedName)
							continue;
						arr[i] = String.valueOf(graphPanel.getVertices().get(i).getName());
					}
					addEdgeList.setListData(arr);
				}

				@Override
				public void menuDeselected(MenuEvent e) {

				}

				@Override
				public void menuCanceled(MenuEvent e) {

				}
			});
			addEdgeCap.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(addEdgeList.getSelectedValue() == null) return;
					graphPanel.getFrame().getController().restoreGraph();
					graphPanel.getFrame().getController().addEdge(
							prevSelectedName,
							addEdgeList.getSelectedValue().charAt(0),
							Integer.parseInt(addEdgeCap.getText()),
							0
					);
					graphPanel.getFrame().repaint();
				}
			});
			addEdgeCap.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() < 48 || e.getKeyChar() > 57)
						e.consume();
					else if (addEdgeCap.getText().length() >= 2)
						e.consume();
					else if(addEdgeCap.getText().length() == 1)
					{
						if(addEdgeCap.getText().charAt(0) == 48) {
							addEdgeCap.setText("");
						}
					}
				}
			});

			deleteVertexItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(prevSelectedName == graphPanel.getVertices().get(0).getName()) {
						graphPanel.getFrame().print("Unable to delete source.");
						return;
					}
					if(prevSelectedName == graphPanel.getVertices().get(1).getName()) {
						graphPanel.getFrame().print("Unable to delete sink.");
						return;
					}
					graphPanel.getFrame().getController().restoreGraph();
					graphPanel.getFrame().getController().deleteVertex(prevSelectedName);
					prevSelectedName = '\0';
					graphPanel.getFrame().repaint();
				}
			});

			deleteEdgeMenu.addMenuListener(new MenuListener() {
				@Override
				public void menuSelected(MenuEvent e) {
					GraphicVertex v = graphPanel.findVertex(prevSelectedName);
					String[] arr = new String[v.getEdges().size()];
					for(int i=0; i<v.getEdges().size(); ++i) {
						arr[i] = String.valueOf(v.getEdges().get(i).getTo().getName());
					}
					deleteEdgeList.setListData(arr);
				}

				@Override
				public void menuDeselected(MenuEvent e) {

				}

				@Override
				public void menuCanceled(MenuEvent e) {

				}
			});
			deleteEdgeList.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(deleteEdgeList.getSelectedValue() == null) return;
					graphPanel.getFrame().getController().restoreGraph();
					graphPanel.getFrame()
							.getController()
							.deleteEdge(
									prevSelectedName,
									deleteEdgeList.getSelectedValue().charAt(0)
							);
					graphPanel.getFrame().repaint();
				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}
			});
		}
	}
}