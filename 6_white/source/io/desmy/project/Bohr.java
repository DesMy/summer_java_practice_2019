package io.desmy.project;

import java.util.HashMap;

public class Bohr {
    private Node head;
    private Node now;
    private int index;

    private HashMap<Integer, Pair> pair;
    public class Pair{
        private int len;
        private String shape;

        public Pair(String str){
            shape = str;
            len = shape.length();
        }

        public int getLen() { return len; }
        public String getShape() { return shape; }
    }

    public Bohr() {
        head = new Node();
        pair = new HashMap<>();
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
            now.setIndex(index);
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

    public void stringToPair(String str) {
        StringBuilder text = new StringBuilder(str);
        text.append(' ');
        int j, k = 0;
        for(int i = 0; i < text.length(); i++)
            if(text.charAt(i) != ' ') {
                for (j = i; text.charAt(j) != ' '; j++);
                pair.put(k++, new Pair(text.substring(i, j)));
                i = j;
            }
    }


    public HashMap<Integer, Pair> getPair() {
        return pair;
    }

    public Node getHead() { return head; }
    public void setNow(Node n) { now = n; }
    public Node getNow() { return now; }
}
