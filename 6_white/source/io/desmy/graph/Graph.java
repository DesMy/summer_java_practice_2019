package io.desmy.graph;

import io.desmy.project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Graph extends JPanel implements MouseListener, ActionListener, MouseWheelListener {
    private CoordinateGenerator gen;
    private Bohr FH;
    public Graph(Bohr for_head) {
        gen = new CoordinateGenerator(for_head);
        FH = for_head;
        addMouseListener(this);
        setFocusable(true);
        setBounds(0, 0, 300, 300);
    }

    @Override
    public void paint(Graphics g) {
        int d = gen.getDiam();
        g.setColor(Color.white);
        g.fillRect(0,0, 300,300);
        //Every element
        gen.getList().forEach((key, value) -> {
            key.getNextLevelNode().forEach((keyN, valueN) -> {
                Point start = gen.getList().get(key);
                Point finish = gen.getList().get(valueN);
                g.setColor(Color.green);
                g.drawLine(start.getX() + d/2, start.getY() + d/2, finish.getX() + d/2, finish.getY() + d/2);
                g.setColor(Color.black);
                g.drawString("" + keyN, (start.getX() + finish.getX() + d/2) / 2 + 1 , (start.getY() + finish.getY() + d) / 2 + 5);
            });
        });
        gen.getList().forEach((key, value) -> {
            g.setColor(Color.yellow);
            g.fillOval(value.getX(), value.getY(), gen.getDiam(), gen.getDiam());
            g.setColor(Color.black);
            g.drawOval(value.getX(), value.getY(), gen.getDiam(), gen.getDiam());
            if(key.getIndex() != -1)
                g.drawString("" + key.getIndex(), value.getX() + d/2 - 2, value.getY() + d/2 + 5);
        });

        //Head
        g.setColor(Color.red);
        g.fillOval(gen.getHead().getX(), gen.getHead().getY(), gen.getDiam(), gen.getDiam());
        //HeadBord
        g.setColor(Color.black);
        g.drawOval(gen.getHead().getX(), gen.getHead().getY(), gen.getDiam(), gen.getDiam());

        Node now = FH.getNow();
        if(now != gen.getH()) {  //now - это указатель на текущий посещённый элемент
            do {
                g.setColor(Color.green);
                HashMap<Node, Point> io = gen.getList();
                g.fillOval(io.get(now).getX(), io.get(now).getY(), gen.getDiam(), gen.getDiam());
                //HeadBord
                g.setColor(Color.black);
                g.drawOval(io.get(now).getX(), io.get(now).getY(), gen.getDiam(), gen.getDiam());
                if (now.getIndex() != -1)
                    g.drawString("" + now.getIndex(), io.get(now).getX() + d / 2 - 2, io.get(now).getY() + d / 2 + 5);
                now = now.suffixLink;
            } while(now != gen.getH());
        }
    }

    public void rePaint() { repaint(); }
    public CoordinateGenerator getGen() { return gen; }

    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}