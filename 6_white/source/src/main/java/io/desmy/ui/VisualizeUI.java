package io.desmy.ui;

import io.desmy.graph.Graph;
import io.desmy.project.AhoCor;
import io.desmy.project.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizeUI extends JFrame {
    private AhoCor aho = null;
    private StringBuilder st = null;
    private int i = 0;

    public VisualizeUI(JTextArea textArea, JTextArea textArea1, JButton open) {

        super("Визуализация алгоритма Ахо-Корасик");

        String OS = System.getProperty("os.name").toLowerCase();
        setBounds(300, 300, 600+16*(OS.contains("win") ? 1:0), 369);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Bohr testBohr = new Bohr();
        textArea.setText(StringProcessor.stringCorrecting(textArea.getText()));
        testBohr.stringToBohr(textArea.getText());
        testBohr.stringToPair(textArea.getText());
        Graph graph = new Graph(testBohr);
        JScrollPane scrollGraph = new JScrollPane(graph);
        scrollGraph.setSize(300,300);
        add(scrollGraph);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        JButton prevSt = new JButton("Шаг назад");
        JButton nextSt = new JButton("Шаг вперёд");
        JButton inTheEnd = new JButton("Результат");
        JButton addShape = new JButton("Вставить");
        JButton delShape = new JButton("Удалить");
        JButton forOpen = new JButton("К началу");
        JTextField forShape = new JTextField();
        JTextArea forText = new JTextArea(textArea1.getText());
        JTextArea forResult = new JTextArea();
        JScrollPane scrollText = new JScrollPane(forText);
        JScrollPane scrollResult = new JScrollPane(forResult);

        forOpen.setBounds(300, 300, 150, 30);
        prevSt.setBounds(0, 300, 150, 30);
        nextSt.setBounds(150, 300, 150, 30);
        inTheEnd.setBounds(450, 300, 150, 30);
        forShape.setBounds(300, 0, 300, 20);
        addShape.setBounds(300, 20, 150, 20);
        delShape.setBounds(450, 20, 150, 20);
        scrollText.setBounds(300, 40, 300, 130);
        scrollResult.setBounds(300, 170, 300, 130);
        forText.setEditable(false);
        forResult.setEditable(false);

        forText.setLineWrap(true);
        forText.setWrapStyleWord(true);
        forResult.setLineWrap(true);
        forResult.setWrapStyleWord(true);


        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollResult.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JButton back = new JButton("");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
            }
        });
        prevSt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (aho != null && i > 0){
                    testBohr.setNow(aho.way.get(--i));
                    if(i==0)
                        forText.setText(textArea1.getText());
                    aho.way.remove(i);

                    if(i > 0 && i < textArea1.getText().length()) {
                        StringBuilder tex = new StringBuilder(textArea1.getText());
                        StringBuilder buf = new StringBuilder("");

                        buf.append(tex.substring(0,i-1));
                        buf.append("[");
                        buf.append(tex.charAt(i-1));
                        buf.append("]");
                        buf.append(tex.substring(i, tex.length()));

                        forText.setText(buf.toString());
                    }
                    while(aho.counter > 0 && aho.answer.get(aho.counter - 1).getPosition() == i+1) {
                        aho.answer.remove(--aho.counter);
                    }
                    StringBuilder no = new StringBuilder("");
                    aho.answer.forEach((key, value) -> {
                        no.append("[");
                        no.append(value.getPosition() - testBohr.getPair().get(value.getTemplate()).getLen() + 1);
                        no.append(" - ");
                        no.append(testBohr.getPair().get(value.getTemplate()).getShape());
                        no.append("]\n");
                    });
                    forResult.setText(no.toString());

                    graph.rePaint();
                }
                else
                    forText.setText(textArea1.getText());
            }
        });
        nextSt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (aho == null) {
                    aho = new AhoCor();
                    st = new StringBuilder(textArea1.getText());
                }
                if (i < st.length()) {
                    aho.stepRight(st.charAt(i), testBohr, i++);
                    graph.rePaint();
                    if (i == st.length()) {
                        StringBuilder tex = new StringBuilder(textArea1.getText());
                        StringBuilder buf = new StringBuilder("");
                        buf.append(tex.substring(0,i-1));
                        buf.append("[");
                        buf.append(tex.charAt(i-1));
                        buf.append("]");
                        forText.setText(buf.toString());
                        inTheEnd.doClick();

                    }
                } else
                    inTheEnd.doClick();
                StringBuilder no = new StringBuilder("");
                aho.answer.forEach((key, value) -> {
                    no.append("[");
                    no.append(value.getPosition() - testBohr.getPair().get(value.getTemplate()).getLen() + 1);
                    no.append(" - ");
                    no.append(testBohr.getPair().get(value.getTemplate()).getShape());
                    no.append("]\n");
                });
                forResult.setText(no.toString());
                if(i > 0 && i < textArea1.getText().length()) {
                    StringBuilder tex = new StringBuilder(textArea1.getText());
                    StringBuilder buf = new StringBuilder("");

                    buf.append(tex.substring(0,i-1));
                    buf.append("[");
                    buf.append(tex.charAt(i-1));
                    buf.append("]");
                    buf.append(tex.substring(i, tex.length()));

                    forText.setText(buf.toString());
                }

            }
        });
        inTheEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AlgorithmTest newTest = new AlgorithmTest();
                textArea.setText(StringProcessor.stringCorrecting(textArea.getText()));
                newTest.test1(textArea.getText(), textArea1.getText(), back);
                setVisible(false);
            }
        });
        addShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.setText(textArea.getText() + (textArea.getText().length() != 0 ? " " : "") + forShape.getText());
                new VisualizeUI(textArea, textArea1, open);
                dispose();
            }
        });
        delShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textArea.setText(StringProcessor.stringDelShape(textArea.getText(), forShape.getText()));
                new VisualizeUI(textArea, textArea1, open);
                dispose();
            }
        });
        forOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open.doClick();
                dispose();
            }
        });

        panel.add(prevSt);
        panel.add(nextSt);
        panel.add(inTheEnd);
        panel.add(forShape);
        panel.add(addShape);
        panel.add(delShape);
        panel.add(scrollText);
        panel.add(scrollResult);
        panel.add(forOpen);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
