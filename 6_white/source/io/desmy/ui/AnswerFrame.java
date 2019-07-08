package io.desmy.ui;

import io.desmy.project.*;

import javax.swing.*;
import java.awt.*;

public class AnswerFrame extends JFrame {
    public AnswerFrame(AhoCor test1, Bohr testBohr) {
        super("Результат");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());
        JTextArea textArea = new JTextArea(10, 1);
        textArea.setCaretPosition(0);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.NORTH);
        test1.answer.forEach((key, value) -> {

            textArea.append("text position: [" + (value.getPosition() - testBohr.getPair().get(value.getTemplate()).getLen())  + "] ");

            textArea.append("template: [" + testBohr.getPair().get(value.getTemplate()).getShape() + "]\n");

        });
        textArea.append("\n");
        panel.add(Box.createRigidArea(new Dimension(10, 10)));

        getContentPane().add(panel);

        setPreferredSize(new Dimension(400, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}