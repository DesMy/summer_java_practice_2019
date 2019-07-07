package Practice.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class DialogView {

}

class InitNode extends JDialog {
    public InitNode(GraphDrawer graphDrawer, MouseEvent e) {
        this.setVisible(true);
        add(new JLabel("<html><h1><i>Task</i></h1><hr>" + "InitNode"),
                BorderLayout.CENTER);
        this.setLocation(e.getX(), e.getY());
        JTextField textField = new JTextField();
        add(textField);
        // При активизации кнопки ОК диалогове окно закрывается.
        JButton ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                if (textField.getText().length() > 0) {
                    graphDrawer.graph.addNode(new Node(textField.getText(), e.getX(), e.getY()));
                    graphDrawer.repaint();
                }
            }
        });

        // Кнопка ОК помещается в нижнюю часть окна.

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(100, 100);
    }
}

class InitEdge extends JDialog {
    private Node start = null;
    private Node end = null;
    private GraphDrawer drawer;
    private Color colorStart;
    private Color colorEnd;
    private Color color = Color.green;

    public InitEdge(GraphDrawer drawer) {
        this.drawer = drawer;
    }

    private boolean checkEdge(Node node1, Node node2) {
        for (Edge edge1 : node1.getListEdge()) {
            for (Edge edge2 : node2.getListEdge()) {
                if (edge1 == edge2)
                    return false;
            }
        }
        return true;
    }

    public void addNodetoEdge(Node node) {
        if (start == null) {
            start = node;
            colorStart = start.getColor();
            node.changeColor(color);
            drawer.repaint();
        } else if (start != node) {
            if (!checkEdge(start, node)) {
                JOptionPane.showMessageDialog(drawer,"There is already an edge between the vertices!!!!!");
                return;
            }
            end = node;
            colorEnd = end.getColor();
            end.changeColor(color);
            drawer.repaint();
            paintEdge();
        }

    }

    public void paintEdge() {
        this.setVisible(true);
        add(new JLabel("<html><h1><i>Task</i></h1><hr>" + "InitEdge"),
                BorderLayout.CENTER);
        this.setLocation((start.getPosX() + end.getPosX()) / 2,
                (start.getPosY() + end.getPosY()) / 2);
        JPanel panelText = new JPanel();
        JTextField textField = new JTextField(2);
        JTextField textField1 = new JTextField(2);

        panelText.add(textField);
        panelText.add(textField1);
        add(panelText, BorderLayout.CENTER);
        // При активизации кнопки ОК диалогове окно закрывается.
        JButton ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                if (textField.getText().length() > 0 && textField1.getText().length() > 0) {
                    drawer.graph.addEdge(new Edge(start, end, Integer.parseInt(textField.getText()), Integer.parseInt(textField1.getText())));

                }

                start.changeColor(colorStart);
                end.changeColor(colorEnd);
                drawer.repaint();
                textField.setText("");
                textField1.setText("");
                start = null;
                end = null;
            }
        });

        // Кнопка ОК помещается в нижнюю часть окна.

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(100, 100);
    }
}