import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
            //AlgoritmTest newTest = new AlgoritmTest();
            //newTest.test1();
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //JFrame.setDefaultLookAndFeelDecorated(true);
                    //JDialog.setDefaultLookAndFeelDecorated(true);
                    try {
                        new StartFrame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }
}
