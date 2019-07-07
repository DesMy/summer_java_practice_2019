public class AlgoritmTest {
    AlgoritmTest() {}
    public void test1(String str, String text) {
        //String str = "she he shed um es";
        //String text = "tshedateumaesu";
        Bohr testBohr = new Bohr();
        testBohr.stringToBohr(str);
        AhoCor test1 = new AhoCor();
        test1.processText(text, testBohr);
        test1.answer.forEach((key, value) -> {

            System.out.print("text position: "+ value.position);

            System.out.println(", template: "+ (value.template + 1));

        });
    }
}
