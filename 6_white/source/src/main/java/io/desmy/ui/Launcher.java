package io.desmy.ui;

public class Launcher {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new StartFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}