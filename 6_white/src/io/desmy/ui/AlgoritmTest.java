package io.desmy.ui;

import io.desmy.project.*;

public class AlgoritmTest {
    AlgoritmTest() {}
    public void test1(String str, String text) {
        Bohr testBohr = new Bohr();
        testBohr.stringToBohr(str);
        AhoCor test1 = new AhoCor();
        test1.processText(text, testBohr);
        test1.answer.forEach((key, value) -> {

            System.out.print("text position: "+ value.getPosition());

            System.out.println(", template: "+ (value.getTemplate() + 1));

        });
    }
}
