package com.practice.blueTeam.UI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelSelectWindow extends JFrame {
    private static LevelSelectWindow selectWindow = new LevelSelectWindow();
    // кнопка выбора уровня
    private class levelButton extends JButton{
        public int getLevelNumber() {
            return levelNumber;
        }

        public void setLevelNumber(int levelNumber) {
            this.levelNumber = levelNumber;
        }

        private int levelNumber;
        public levelButton(int levelNumber, String name) {
            super(name);
            this.levelNumber = levelNumber;
            this.addMouseListener(mouseAdapter);
        }
        public  levelButton(){
            super();
            this.levelNumber = 0;
            this.addMouseListener(mouseAdapter);
        }
        public  levelButton(int levelNumber){
            super();
            this.levelNumber = levelNumber;
            this.addMouseListener(mouseAdapter);

        }
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectWindow.setVisible(false);
                levelWindows[levelNumber] = new LevelWindow();
                levelWindows[levelNumber].setVisible(true);
            }
        };
    }
    private void createLevelButtons(){
        for (int i = 0; i < DataBase.getNumberOfLevels(); ++i) {
            levelButtons[i] = new levelButton(i);
        }
    }
    private LevelWindow[]  levelWindows = new LevelWindow[DataBase.getNumberOfLevels()];
    private levelButton[] levelButtons = new levelButton[DataBase.getNumberOfLevels()];


    public static LevelSelectWindow getSelectWindow() {
        return selectWindow;
    }

    private LevelSelectWindow() {
        //настройки основного окна
        super("Пятнашки");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 800;
        int sizeHeight = 700;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        this.setBounds(locationX, locationY, sizeWidth, sizeHeight);

        // инициализация массива levelButtons
        createLevelButtons();
        //// Настройки внешнего вида окна
        //Основная панель, на ней размещены другие панели
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setSize(this.getSize());
        // Панель с кнопками
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(mainPanel.getBounds());
        GridBagLayout layout = new GridBagLayout();
        buttonsPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 300;
        gbc.ipady = 300;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);

        //Добавление кнопок на панель
        for (int i = 0; i < levelButtons.length; i++) {
            buttonsPanel.add(levelButtons[i]);
            if (i % 2 == 1) {
                buttonsPanel.add(levelButtons[i], gbc);
                gbc.gridx = 0;
                gbc.gridy++;
            } else {
                buttonsPanel.add(levelButtons[i], gbc);
                gbc.gridx++;
                ;
            }
        }
        JScrollPane buttonsScroll = new JScrollPane(buttonsPanel);
        buttonsScroll.setBounds(buttonsPanel.getBounds());
        buttonsScroll.createVerticalScrollBar();
        buttonsScroll.getVerticalScrollBar().setUnitIncrement(10);
        buttonsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(buttonsScroll,BorderLayout.CENTER);
        this.getContentPane().add(mainPanel);
    }
}
