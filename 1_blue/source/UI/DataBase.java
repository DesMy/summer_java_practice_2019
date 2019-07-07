package com.practice.blueTeam.UI;

import javax.swing.*;

// Используется синглтон, чтобы на протижении всего процесса везде использовалась одна база данных
public class DataBase {
    private static DataBase ourInstance = new DataBase();
    public static DataBase getInstance() {
        return ourInstance;
    }
    // количество уровней
    private static int numberOfLevels = 5;
    public static int getNumberOfLevels() {
        return numberOfLevels;
    }
    public static void setNumberOfLevels(int numberOfLevels) {
        DataBase.numberOfLevels = numberOfLevels;
    }

    // Иконки уровней
    private static ImageIcon[] levelIcons = new ImageIcon[numberOfLevels];
    public static ImageIcon[] getLevelIcons() {
        return levelIcons;
    }
    public static void setLevelIcons(ImageIcon[] levelIcons) {
        DataBase.levelIcons = levelIcons;
    }

    // Рисунки Тайлов
    private  static ImageIcon[][] tiles = new ImageIcon[numberOfLevels][15];
    public static ImageIcon[][] getTiles() {
        return tiles;
    }
    public static void setTiles(ImageIcon[][] tiles) {
        DataBase.tiles = tiles;
    }
    public static ImageIcon[] getLevelTiles(int levelNubmer) {
        return tiles[levelNubmer];
    }
    public static void setLevelTiles(int levelNumber, ImageIcon[] tiles) {
        DataBase.tiles[levelNumber] = tiles;
    }

    private DataBase() {


    }
}
