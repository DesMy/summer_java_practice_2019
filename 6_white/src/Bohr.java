public class Bohr {
    public final Node head;
    private Node now;
    private int index;

    public Bohr() {
        head = new Node();
        now = head;
        index = 0;
    }

    private void newNode(char ch, boolean isEndOfShape) {
        if(isEndOfShape) {
            new Node(now, ch, index);
            now = head;
        }
        else
            now = new Node(now, ch);
    }

    private void oldNode(char ch, boolean isEndOfShape) {
        now = now.getChild(ch);
        if(isEndOfShape) {
            now.getChild(ch).setIndex(index);
            now = head;
        }
    }

    public void stringToBohr(String str) {
        str += ' ';
        int i = 1;
        char ch;
        while(i < str.length()) {
            ch = str.charAt(i-1);
            if (now.childExist(ch))
                oldNode(ch, str.charAt(i) == ' ');
            else
                newNode(ch, str.charAt(i) == ' ');
            if(str.charAt(i) == ' ')
                index++;
            while(i < str.length() && str.charAt(i) == ' ')
                i++;
            i++;
        }
    }




}