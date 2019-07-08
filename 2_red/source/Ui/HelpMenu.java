package Ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpMenu {
}

class HelpMenuAbout extends JDialog {
    public HelpMenuAbout(JFrame owner) {
        super(owner, "About", true);

        // Метка с HTML-форматированием выравнивается по центру.
        add(new JLabel("<html><h1><i>Task</i></h1><hr>"
                        + "Правильная скобочная конструкция с тремя видами скобок опреде¬ляется как<br>" +
                        "  < текст > ::= < пусто > | < элемент > < текст ><br>" +
                        "  < элемент > ::= < символ > | (< текст > ) | [ < текст > ] | { < текст > }<br>" +
                        "  где < символ > - любой символ, кроме (,),[.]>{>}• Проверить, является ли текст, содержащийся в заданном файле F,<br>правильной скобочной конструк¬цией; если нет, то указать номер ошибочной позиции.<br>" +
                        "  В решении задачи использовать стек.</html>"),
                BorderLayout.CENTER);

        // При активизации кнопки ОК диалогове окно закрывается.
        JButton ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });

        // Кнопка ОК помещается в нижнюю часть окна.
        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(500, 400);
    }
}

class HelpMenuTask extends JDialog {
    public HelpMenuTask(JFrame owner) {
        super(owner, "Task", true);
        // Метка с HTML-форматированием выравнивается по центру.
        add(new JLabel("<html><h1><i>About</i></h1><hr>" + "Cтудент группы: 7303<br>" + "Мищенко Матвей"
                ),
                BorderLayout.CENTER);
        // При активизации кнопки ОК диалогове окно закрывается.
        JButton ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });
        // Кнопка ОК помещается в нижнюю часть окна.
        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(500, 400);
    }
}