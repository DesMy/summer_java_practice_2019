package io.desmy.ui;

import io.desmy.project.*;

public class AlgoritmTest {
    AlgoritmTest() {}
    public void test1(String str, String text) {
        Bohr testBohr = new Bohr();
        testBohr.stringToBohr(str);
        testBohr.stringToPair(str);
        AhoCor test1 = new AhoCor();
        test1.processText(text, testBohr);
        AnswerFrame answerFrame = new AnswerFrame(test1, testBohr);
    }
}
