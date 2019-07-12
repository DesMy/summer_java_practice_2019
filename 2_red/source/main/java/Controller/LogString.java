package Controller;

import javax.swing.*;
import java.util.ArrayList;

public class LogString {
    JTextArea textField;
    public ArrayList<String> list = new ArrayList<String>();

    public LogString(JTextArea textField) {
        this.textField = textField;
    }

    public void addString(String str) {
        list.add(str);
    }

    public void chengeTextLog(int count) {
        String log = "";
        for (int i = 0; i <=count; i++) {
         log+=list.get(i)+"\n";
        }
        textField.setText("");
        textField.setText(log);
    }


}
