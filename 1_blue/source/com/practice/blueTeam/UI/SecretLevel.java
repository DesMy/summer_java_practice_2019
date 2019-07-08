package com.practice.blueTeam.UI;

import com.practice.blueTeam.DataBase.DataBase;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SecretLevel extends JFrame {
    private JButton closeButton = new JButton("Close");
    private JButton randomButton = new JButton("Random");
    private JButton buildButton = new JButton("Show Solution");
    private JButton nextButton = new JButton("Next");
    private JButton prevButton = new JButton("Prev");
    private JButton returnButton = new JButton("Return to Main menu");
    private JPanel tilesPanel = new JPanel();

    private int levelNumber;
    private Tile[] tiles;
    public void setTiles() {
        for (int i = 0; i < 16; i++)
        {
            tiles[i] = new Tile(DataBase.getSecretTilesOrderOnTheScreen()[i].numberOfTile, levelNumber);
            tiles[i].setIcon(DataBase.getSecretTiles()[DataBase.getSecretTilesOrderOnTheScreen()[i].numberOfTile]);
            tiles[i].setBorderPainted(false);
            tiles[i].setFocusPainted(false);
        }
        tilesPanel.removeAll();
        for (int i = 0; i < 16; i++) {
            tilesPanel.add(tiles[i]);
            tiles[i].setIcon(new ImageIcon(DataBase.getSecretTiles()[tiles[i].numberOfTile].getImage().getScaledInstance(tilesPanel.getWidth()/4, tilesPanel.getHeight()/4, Image.SCALE_SMOOTH)));
        }
    }

    public SecretLevel() {
        super("Пятнашки");

        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        tilesPanel = new JPanel();
        levelNumber = 4;
        tiles = new Tile[16];
        int sizeWidth = 800;
        int sizeHeight = 700;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        this.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        JPanel mainPanel = new JPanel();
        this.setContentPane(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setSize(this.getSize());
        tilesPanel.setLayout(new GridLayout(4,4,0,0));
        tilesPanel.setLocation(mainPanel.getWidth()/16,mainPanel.getHeight()/6 );
        tilesPanel.setSize(400, 400);
        mainPanel.add(tilesPanel);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLocation(tilesPanel.getWidth() + 100, tilesPanel.getY() -10);
        buttonsPanel.setSize(200,300);
        buttonsPanel.setLayout(new GridLayout(5,1,5,5));
        buttonsPanel.add(randomButton);
        buttonsPanel.add(buildButton);
        JPanel nextPrevPanel = new JPanel();
        nextPrevPanel.setLayout(new GridLayout(1,2,0,0));
        nextPrevPanel.add(prevButton);
        nextPrevPanel.add(nextButton);
        buttonsPanel.add(nextPrevPanel);
        buttonsPanel.add(closeButton);
        buttonsPanel.add(returnButton);
        mainPanel.add(buttonsPanel);
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DataBase.getSecretLevel().setVisible(false);
                MainWindow.getUI().setVisible(true);
            }
        });
        randomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DataBase.randomizePuzzle(levelNumber);
            }
        });
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DataBase.secretNextStep();
            }
        });
        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DataBase.secretPrevStep();
            }
        });
    }
}
