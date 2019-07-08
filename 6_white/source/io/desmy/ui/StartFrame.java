package io.desmy.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import javax.swing.*;
import io.desmy.graph.*;
import io.desmy.project.*;

public class StartFrame extends JFrame {

    private AhoCor aho = null;
    private StringBuilder st = null;
    public StartFrame() throws Exception {
        super("Выбор входных данных");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        final JLabel label2 = new JLabel("Выбранный файл шаблонов");
        label2.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label2);

        panel.add(Box.createRigidArea(new Dimension(10, 10)));

        JTextArea textArea = new JTextArea(10, 1);
        textArea.setCaretPosition(0);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.NORTH);

        JButton button2 = new JButton("Выбрать файл шаблонов");
        button2.setAlignmentX(CENTER_ALIGNMENT);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen2 = new JFileChooser();
                int ret = fileopen2.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen2.getSelectedFile();
                    try (Scanner sc = new Scanner(file))  {
                        StringBuilder stringBuilder = new StringBuilder();
                        while (sc.hasNextLine()) {
                            stringBuilder.append(sc.nextLine());
                            stringBuilder.append(" ");
                        }
                        textArea.setText(stringBuilder.toString());
                    } catch (FileNotFoundException en) {
                        System.err.println("File not found. Please scan in new file.");
                    }
                    label2.setText(file.getName());
                }
            }
        });


        panel.add(label2);
        panel.add(button2);

        final JLabel label = new JLabel("Выбранный файл с текстом");
        label.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(10, 10)));

        JTextArea textArea1 = new JTextArea(10, 1);
        textArea1.setCaretPosition(0);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane1, BorderLayout.CENTER);

        panel.add(label);

        JButton button = new JButton("Выбрать текст");
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    Scanner sc = null;
                    try {
                        sc = new Scanner(file);
                    } catch (FileNotFoundException en) {
                        System.err.println("File not found. Please scan in new file.");
                    }
                    try {
                        String textString1 = sc.next();
                        textArea1.setText(textString1);
                    } catch (NullPointerException ex){
                        System.err.println("File is empty. Pleas choose another file.");
                    }
                    label.setText(file.getName());
                }
            }
        });
        panel.add(button);
        panel.add(Box.createVerticalGlue());

        JButton button3 = new JButton("Найти вхождения");
        button2.setAlignmentX(CENTER_ALIGNMENT);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AlgoritmTest newTest = new AlgoritmTest();
                newTest.test1(textArea.getText(), textArea1.getText());
            }
        });

        JButton button4 = new JButton("Визуализация");
        button2.setAlignmentX(CENTER_ALIGNMENT);
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame obj = new JFrame();
                Bohr testBohr = new Bohr();
                testBohr.stringToBohr(textArea.getText());
                Graph graph = new Graph(testBohr);
                obj.setBounds(0,0,515,369);
                obj.setTitle("Test");
                obj.setResizable(false);
                obj.setVisible(true);
                obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                obj.add(graph);
                setVisible(false);
                JPanel panel = new JPanel();
                panel.setLayout(null);

                JButton prevSt = new JButton("Previous");
                JButton nextSt = new JButton("Next");
                JButton inTheEnd = new JButton("Result");

                prevSt.setBounds(0, 300, 100, 30);
                nextSt.setBounds(100, 300, 100, 30);
                inTheEnd.setBounds(200, 300, 100, 30);

                prevSt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(aho != null && !st.equals(textArea1.getText())) {
                            st.insert(0, testBohr.getNow().parentChar);
                            testBohr.setNow(testBohr.getNow().parentNode != null ? testBohr.getNow().parentNode : testBohr.getHead());
                            graph.rePaint();
                        }
                    }
                });

                nextSt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        if(aho == null) {
                            aho = new AhoCor();
                            st = new StringBuilder(textArea1.getText());
                        }
                        if(st.length() != 0) {
                            aho.stepRight(st.charAt(0), testBohr, 0);//i нужно переписать, а вообще ещё добавить постепеное добавление шаблонов
                            st.deleteCharAt(0);
                            graph.rePaint();
                            if(st.length() == 0)
                                button3.doClick();
                        }
                        else
                            button3.doClick();
                    }
                });

                inTheEnd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        button3.doClick();
                    }
                });

                JTextField forShape = new JTextField();
                forShape.setBounds(300, 0, 200, 20);
                JButton addShape = new JButton("Вставить");
                addShape.setBounds(300, 20, 100, 20);
                JButton delShape = new JButton("Удалить");
                delShape.setBounds(400, 20, 100, 20);

                addShape.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //
                    }
                });

                delShape.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //
                    }
                });

                JTextField forText = new JTextField(textArea1.getText());
                forText.setBounds(300, 40, 200, 20);
                forText.setEnabled(false);
                panel.add(forText);

                panel.add(prevSt);
                panel.add(nextSt);
                panel.add(inTheEnd);

                panel.add(forShape);
                panel.add(addShape);
                panel.add(delShape);

                obj.add(panel);
            }
        });

        panel.add(button3);
        panel.add(button4);

        getContentPane().add(panel);

        setPreferredSize(new Dimension(600, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}