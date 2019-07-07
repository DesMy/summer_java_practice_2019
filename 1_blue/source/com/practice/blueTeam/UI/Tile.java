package com.practice.blueTeam.UI;

import javax.swing.*;

public class Tile extends JButton {
    // является ли последним тайлом
    private boolean last;
    public boolean isLast() {
        return last;
    }
    public void setLast(boolean last) {
        this.last = last;
    }
    // номер тайла
    int numberOfTile;
    //Картинка тайла
    private ImageIcon imageIcon;
    public ImageIcon getImageIcon() {
        return imageIcon;
    }
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
    // конструктор
    public Tile(int numberOfTile) {
        this.numberOfTile = numberOfTile;
        if (numberOfTile == 15)
            this.last = true;
        else
            this.last = false;

        setSize(50,50);
        if (this.last)
            setVisible(false);
        else
            setVisible(true);
    }
}
