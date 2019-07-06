package Practice.GUI;

import java.awt.event.*;

public abstract class ButtonCommand implements ActionListener {
    public abstract void actionPerformed(ActionEvent e);
}

class StartCommand extends ButtonCommand {
    public void actionPerformed(ActionEvent e) {

    }
}

class PrevCommand extends ButtonCommand {
    public void actionPerformed(ActionEvent e) {

    }
}

class NextCommand extends ButtonCommand {
    public void actionPerformed(ActionEvent e) {

    }
}
