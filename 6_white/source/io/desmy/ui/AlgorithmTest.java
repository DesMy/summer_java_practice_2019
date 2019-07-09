package io.desmy.ui;

import io.desmy.project.*;
import javax.swing.*;

public class AlgorithmTest {
    public AlgorithmTest() {}
    public void test1(String str, String text, JButton open) {
        Bohr testBohr = new Bohr();
        testBohr.stringToBohr(str);
        testBohr.stringToPair(str);
        AhoCor test1 = new AhoCor();
        test1.processText(text, testBohr);
        new AnswerFrame(test1, testBohr, open);
    }
}
