package io.desmy.ui;

import io.desmy.project.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnswerFrame extends JFrame {
    public AnswerFrame(AhoCor test1, Bohr testBohr, JButton open) {
        super("Результат");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JTextArea textArea = new JTextArea(10, 1);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setSize(new Dimension(400,200));
        panel.add(scrollPane);
        test1.answer.forEach((key, value) -> {
            textArea.append("[" + (value.getPosition() - testBohr.getPair().get(value.getTemplate()).getLen() + 1)  + " - " + testBohr.getPair().get(value.getTemplate()).getShape() + "]\n");
        });

        JButton prev = new JButton("Назад");
        prev.setBounds(100,205, 200, 20);
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open.doClick();
                dispose();
            }
        });
        panel.add(prev);
        String OS = System.getProperty("os.name").toLowerCase();
        setBounds(300, 300, 400+15*(OS.contains("win") ? 1:0), 269);
        setResizable(false);
        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}