package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.*;
import java.util.Scanner;
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
        //Fonts:
        Font font = new Font("TimesNewRoman", Font.PLAIN, 11);
        graphMenu.setFont(font);
        helpMenu.setFont(font);
        randomItem.setFont(font);
        fileItem.setFont(font);
        resetItem.setFont(font);
        //Add
        graphMenu.add(randomItem);
        graphMenu.add(fileItem);
        graphMenu.addSeparator();
        graphMenu.add(resetItem);
        menuBar.add(graphMenu);
        menuBar.add(helpMenu);
        //

        //Graph panel:
        graphPanel = new GraphPanel();
        JScrollPane graphPanelScroll = new JScrollPane(graphPanel);
        //

        //Buttons:
        JButton stepOut = new JButton(/*new ImageIcon(Frame.class.getResource("images/out.png"))*/);
        stepOut.setPreferredSize(new Dimension(20,20));
        stepOut.setToolTipText("Step out");
        JButton stepForward = new JButton(/*new ImageIcon(UI.class.getResource("images/forward.png"))*/);
        stepForward.setPreferredSize(new Dimension(20,20));
        stepForward.setToolTipText("Step forward");
        JButton stepBack = new JButton(/*new ImageIcon(UI.class.getResource("images/back.png"))*/);
        stepBack.setPreferredSize(new Dimension(20,20));
        stepBack.setToolTipText("Step back");
        JButton runAuto = new JButton(/*new ImageIcon(UI.class.getResource("images/run.png"))*/);
        runAuto.setPreferredSize(new Dimension(20,20));
        runAuto.setToolTipText("Run automatically");
        JSlider speed = new JSlider(SwingConstants.HORIZONTAL, 0, 10, 0);
        speed.setMaximumSize(new Dimension(100,20));
        speed.setToolTipText("Set speed (0 to 10)");


        //Console:
        final JTextArea console = new JTextArea("console@FF $: ");;
        final String consoleStr = "console@FF $: ";;
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
                        .addComponent(stepOut)
                        .addComponent(stepForward)
                        .addComponent(stepBack)
                        .addComponent(runAuto)
                        .addComponent(speed))
                .addComponent(consolePane)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, stepOut, stepForward, stepBack, runAuto);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(graphPanelScroll)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(stepOut)
                        .addComponent(stepForward)
                        .addComponent(stepBack)
                        .addComponent(runAuto)
                        .addComponent(speed))
                .addComponent(consolePane)
        );

        layout.linkSize(SwingConstants.VERTICAL, stepOut, stepForward, stepBack, runAuto, speed);

        //Actions
        fileItem.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    Scanner sc;
                    ArrayList<String> lines = new ArrayList<String>();
                    try {
                        sc = new Scanner(file);

                        while (sc.hasNextLine()) {
                            String line = sc.nextLine();
                            lines.add(line);
                        }
                        sc.close();
                    } catch (FileNotFoundException ex) {
                        return;
                    }
                    controller.initGraph(lines);
                }
            }
        });

        stepOut.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flow = controller.runFordFulkerson();
                console.setText(consoleStr + "Max flow: " + Integer.toString(flow));
            }
        });

        stepForward.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                int flow = controller.stepForward();
                if (flow != 0) {
                    console.setText(consoleStr + "Max flow: " + Integer.toString(flow));
                }
            }
        });
        stepBack.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stepBack();
            }
        });
    }

    public void addElement(GraphicElement e) {
        graphPanel.addElement(e);
    }

    public void clearPanel() {
        graphPanel.deleteElements();
    }

    private final GraphPanel graphPanel;
    private final Controller controller;
}