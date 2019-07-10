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
                        + "Состав<br>" +
                        "Мищенко М.А. 7303<br>" +
                        "Батурин И. 7303 <br>" +
                        "Швец А.А. 7303"),
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
        add(new JLabel("<html><h1><i>Task</i></h1><hr>" + "Задача \"Алгоритм Форда — Фалкерсона\"<br>" +
                        "Задача: Визуализация алгоритма Форда-Фалкерсона, который используется для нахождения максимального потока в сети<br>" +
                        "Решение: Создание программы, с помощью которой можно задать граф и выполнить алгоритм Форда-Фалкерсона в нескольких<br>"+ "режимах(пошаговое выполнение, просмотр хода выполнения алгоритма, мгновенное получение ответа)<br>"
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