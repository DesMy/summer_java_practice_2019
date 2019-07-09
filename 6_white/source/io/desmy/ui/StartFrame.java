package io.desmy.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import javax.swing.*;
import io.desmy.project.StringProcessor;

public class StartFrame extends JFrame {
    public StartFrame() {
        super("Выбор входных данных");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JTextArea textArea = new JTextArea(10, 1);
        JTextArea textArea1 = new JTextArea(10, 1);
        JScrollPane scrollShape = new JScrollPane(textArea);
        JScrollPane scrollText = new JScrollPane(textArea1);
        JLabel shapeLabel = new JLabel("Выбранный файл шаблонов");
        JLabel textLabel = new JLabel("Выбранный файл с текстом");

        JButton shapeButton = new JButton("Выбрать файл шаблонов");
        JButton textButton = new JButton("Выбрать текст");

        JButton resultButton = new JButton("Найти вхождения");
        JButton visualButton = new JButton("Визуализация");

        JButton b = new JButton("");

        shapeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    try (Scanner sc = new Scanner(file,"Cp1251"))  {
                        StringBuilder stringBuilder = new StringBuilder();
                        while (sc.hasNextLine()) {
                            stringBuilder.append(sc.nextLine());
                            stringBuilder.append(" ");
                        }
                        textArea.setText(stringBuilder.toString());
                    } catch (FileNotFoundException en) {
                        System.err.println("File not found. Please scan in new file.");
                    }
                    shapeLabel.setText(file.getName());
                }
            }
        });
        textButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    try (Scanner sc = new Scanner(file, "Cp1251"))  {
                        StringBuilder stringBuilder = new StringBuilder();
                        while (sc.hasNextLine()) {
                            stringBuilder.append(sc.nextLine());
                            stringBuilder.append(" ");
                        }
                        textArea1.setText(stringBuilder.toString());
                    } catch (FileNotFoundException en) {
                        System.err.println("File not found. Please scan in new file.");
                    }
                    textLabel.setText(file.getName());
                }
            }
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(true);
            }
        });
        visualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                VisualizeUI VI = new VisualizeUI(textArea, textArea1, b);
                setVisible(false);
            }
        });
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StringBuilder str = new StringBuilder(textArea.getText());
                while(str.length() != 0 && str.charAt(0) == ' ')
                    str.deleteCharAt(0);
                textArea.setText(str.toString());
                AlgorithmTest newTest = new AlgorithmTest();
                textArea.setText(StringProcessor.stringCorrecting(textArea.getText()));
                newTest.test1(textArea.getText(), textArea1.getText(), b);
                setVisible(false);
            }
        });

        scrollShape.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textArea1.setCaretPosition(0);
        textArea.setCaretPosition(0);
        textArea1.setLineWrap(true);
        textArea.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea.setWrapStyleWord(true);

        scrollShape.setBounds(0,0,400, 100);
        shapeButton.setBounds(5,105, 190, 25);
        shapeLabel.setBounds(205,110,190, 15);
        scrollText.setBounds(0,135,400,100);
        textButton.setBounds(5,240,190,25);
        textLabel.setBounds(205,245,190, 15);
        resultButton.setBounds(0,280,200,30);
        visualButton.setBounds(200, 280, 200, 30);

        panel.add(shapeLabel);
        panel.add(textLabel);
        panel.add(scrollText);
        panel.add(scrollShape);
        panel.add(shapeButton);
        panel.add(textButton);
        panel.add(resultButton);
        panel.add(visualButton);

        add(panel);
        String OS = System.getProperty("os.name").toLowerCase();
        setBounds(300, 300, 400+16*(OS.contains("win") ? 1:0), 310+39*(OS.contains("win") ? 1:0));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}