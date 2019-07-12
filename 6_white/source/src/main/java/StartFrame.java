import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;

import javax.swing.*;

public class StartFrame extends JFrame {

    public StartFrame() throws Exception {
        super("Выбор входных данных");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JTextField textField1;

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
                    Scanner sc = null;
                    try {
                        sc = new Scanner(file);
                        while (sc.hasNextLine()) {
                            String textString1 = sc.next();
                            textArea.append(textString1);
                            textArea.append(" ");
                        }
                    } catch (FileNotFoundException en) {
                        System.err.println("File not found. Please scan in new file.");
                    }
                    try {
                        String textString1 = sc.next();
                    } catch (NullPointerException ex){
                        System.err.println("File is empty. Pleas choose another file.");
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
                //System.out.println(textArea.getText());
                //System.out.println(textArea1.getText());
                newTest.test1(textArea.getText(), textArea1.getText());
            }
        });

        JButton button4 = new JButton("Визуализация");
        button2.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(button3);
        panel.add(button4);

        getContentPane().add(panel);

        setPreferredSize(new Dimension(600, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}