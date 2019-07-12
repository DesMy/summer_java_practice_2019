package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.Scanner;
import java.lang.StringBuilder;
import java.util.ArrayList;

import controller.Controller;

public class Frame extends JFrame {
    public Frame(final Controller controller) {
        //Controller:
        this.controller = controller;
        //Menu bar:
        JMenuBar menuBar = new JMenuBar();
        JMenu graphMenu = new JMenu("Graph");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem randomItem = new JMenuItem("Generate graph");
        JMenuItem fileItem = new JMenuItem("Load from file...");
        JMenuItem resetItem = new JMenuItem("Reset");
        JMenuItem helpItem = new JMenuItem("Help");
        //Fonts:
        Font font = new Font("TimesNewRoman", Font.PLAIN, 11);
        graphMenu.setFont(font);
        helpMenu.setFont(font);
        randomItem.setFont(font);
        fileItem.setFont(font);
        resetItem.setFont(font);
        helpItem.setFont(font);
        //Add
        graphMenu.add(randomItem);
        graphMenu.add(fileItem);
        graphMenu.addSeparator();
        graphMenu.add(resetItem);
        helpMenu.add(helpItem);
        menuBar.add(graphMenu);
        menuBar.add(helpMenu);
        //

        //Graph panel:
        graphPanel = new GraphPanel(this);
        JScrollPane graphPanelScroll = new JScrollPane(graphPanel);
        //

        //Buttons:
        JButton toEnd = new JButton(new ImageIcon(Frame.class.getResource("images/end.png")));
        toEnd.setPreferredSize(new Dimension(20,20));
        toEnd.setToolTipText("To end");
        JButton stepForward = new JButton(new ImageIcon(Frame.class.getResource("images/forward.png")));
        stepForward.setPreferredSize(new Dimension(20,20));
        stepForward.setToolTipText("Step forward");
        JButton stepBack = new JButton(new ImageIcon(Frame.class.getResource("images/back.png")));
        stepBack.setPreferredSize(new Dimension(20,20));
        stepBack.setToolTipText("Step back");
        JButton toStart = new JButton(new ImageIcon(Frame.class.getResource("images/start.png")));
        toStart.setPreferredSize(new Dimension(20,20));
        toStart.setToolTipText("To start");

        //Console:
        console = new JTextArea("console@FF $: ");
        console.setFont(new Font("Consolas", Font.PLAIN, 15));
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.setCaretPosition(consoleStr.length());
        console.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 0x0A) {
                    console.setText(console.getText() + consoleStr);
                    console.moveCaretPosition(console.getText().length());
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
            }
        });
        JScrollPane consolePane = new JScrollPane(console);
        //

        //Context menu:

        //

        //Frame:
        setBounds(150, 10, 1000, 650);
        setTitle("Ford-Fulkerson application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(graphPanelScroll)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(toEnd)
                        .addComponent(stepForward)
                        .addComponent(stepBack)
                        .addComponent(toStart))
                .addComponent(consolePane)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, toEnd, stepForward, stepBack, toStart);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(graphPanelScroll)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(toEnd)
                        .addComponent(stepForward)
                        .addComponent(stepBack)
                        .addComponent(toStart))
                .addComponent(consolePane)
        );

        layout.linkSize(SwingConstants.VERTICAL, toEnd, stepForward, stepBack, toStart);

        //Actions
        fileItem.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reset();
                console.setText(consoleStr);

                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    Scanner sc;
                    StringBuilder input = new StringBuilder();
                    try {
                        sc = new Scanner(file);
                        for (int i = 0; i < 2; i++) {
                            String line = sc.nextLine();
                            input.append(line).append(System.getProperty("line.separator"));
                        }
                        while (sc.hasNextLine()) {
                            String line = sc.nextLine();
                            input.append(line).append(" 0 ").append(System.getProperty("line.separator"));
                        }
                        sc.close();
                    } catch (FileNotFoundException ex) {
                        return;
                    }
                    controller.makeGraph(input.toString());
                }
            }
        });

        resetItem.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reset();
                console.setText(consoleStr);
            }
        });

        randomItem.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reset();
                console.setText(consoleStr);
                controller.randomGraph();
            }
        });

        toEnd.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flow = controller.toEnd();
                if (flow == -1) {
                    return;
                }
                print("Max flow: " + flow);
            }
        });

        stepForward.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flow = controller.stepForward();
                if (flow == -1) {
                    return;
                }
                print("Cur flow: " + flow);

            }
        });

        stepBack.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flow = controller.stepBack();
                if (flow == -1) {
                    return;
                }
                print("Cur flow: " + flow);
            }
        });

        toStart.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flow = controller.toStart();
                if (flow == -1) {
                    return;
                }
                print("Cur flow: 0");
            }
        });

        helpItem.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Определение способа завершения работы диалогового окна
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setBounds(400, 80, 600, 600);
                JTextArea helpText = new JTextArea();
                Font font = new Font("TimesNewRoman", Font.PLAIN, 13);
                helpText.setFont(font);
                helpText.setText("  Чтобы создать случайный граф, выберите пункт Generate graph в меню Graph."
                        + System.getProperty("line.separator") +
                        "  Чтобы построить граф по данным из файла, выберите пункт Load from file в меню Graph."
                        + System.getProperty("line.separator") +
                        "  Чтобы перейти в начальное состояние графа, нажмите кнопку in start."
                        + System.getProperty("line.separator") +
                        "  Чтобы перейти в конечное состояние графа, нажмите кнопку in end."
                        + System.getProperty("line.separator") +
                        "  Чтобы перейти в следующее состояние алгоритма состояние графа, нажмите кнопку step forward."
                        + System.getProperty("line.separator") +
                        "  Чтобы перейти в предыдущее состояние алгоритма состояние графа, нажмите кнопку step back."
                        + System.getProperty("line.separator") +
                        "  Для сброса состояния программы выберите пункт Reset в меню Graph."
                        + System.getProperty("line.separator") +
                        "  Добавление и удаление вершин/ребер производится через контекстное меню."
                        + System.getProperty("line.separator") +
                        "  Информация о состоянии графа и выполнении алгоритма выводится на консоль."
                        + System.getProperty("line.separator"));
                dialog.add(new JScrollPane(helpText));
                helpText.setEditable(false);
                dialog.setVisible(true);
                dialog.pack();
            }
        });
    }

    public void addVertex(GraphicVertex e) {
        if(graphPanel.findVertex(e.getName()) == null) {
            graphPanel.addVertex(e);
        } else {
            print("Vertex with name '" + e.getName() + "' already exists.");
        }
    }

    public void addEdge(char from, char to, int capacity, int flow) {
        graphPanel.addEdge(from, to, capacity, flow);
    }

    public void deleteVertex(char name) {
        graphPanel.deleteVertex(name);
    }

    public void deleteEdge(char from, char to) {
        graphPanel.deleteEdge(from, to);
    }

    public void clearPanel() {
        graphPanel.clear();
    }

    public final Controller getController() {
        return controller;
    }

    public void print(String str) {
        console.setText(console.getText() + System.getProperty("line.separator")+ consoleStr + str);
    }

    public ArrayList <GraphicVertex> getVertices(){
        return graphPanel.getVertices();
    }

    private final GraphPanel graphPanel;
    private final JTextArea console;
    final String consoleStr = "console@FF $: ";
    private final Controller controller;
    private JDialog dialog = new JDialog(this, "Help");
}