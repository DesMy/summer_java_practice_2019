package com.practice.blueTeam.GameState;

import com.practice.blueTeam.DataBase.DataBase;

public class GameState {
    private static GameState state = new GameState();
    public static GameState getState() {
        return state;
    }
    // код секретного уровня
    private static String secretCode = "aezakmi";
    private static int correctLength = 0;
    private static int codeLength = secretCode.length();
    // ввод кода
    public static void secretTyped(char c) {
        if (secretCode.charAt(correctLength) == c) {
            correctLength++;
        } else {
            correctLength = 0;
        }
    }
    // проверка кода
    public static boolean isCodeActivated() {
        if (correctLength == codeLength) {
            correctLength = 0;
            return true;
        } else {
            return false;
        }
    }

    public static boolean getIsAvailable(int i) {
        return isAvailable[i];
    }

    // массив пройденный уровней;
    private static boolean isAvailable[];
    private GameState() {
        isAvailable = new boolean[DataBase.getNumberOfLevels()];
        isAvailable[0] = true;
        for (int i = 1; i < DataBase.getNumberOfLevels(); i++) {
            isAvailable[i] = false;
        }
    }
}
