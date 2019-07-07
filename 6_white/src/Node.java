
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

public class Node { //json
    private LinkedHashMap<Character, Node> go = new LinkedHashMap<>();
    private HashMap<Character, Node> nextLevelNode = new HashMap<>();  //Создание с нуля
    public int indexOfShape;  //Вход, если последний символ
    public Node parentNode;  //Вход
    public char parentChar;  //Вход (получаем по parentNode)
    public Node suffixLink;  //Пустое значение
    public Node goodSuffixLink;  //Пустое значение

    public Node() {
        indexOfShape = -1;
    }

    public Node(Node parent, char ch) {
        this();
        parentNode = parent;
        parentChar = ch;
        parent.setChild(this);
    }

    public Node(Node parent, char ch, int index) {
        this(parent, ch);
        indexOfShape = index;
    }

    private void setChild(Node child) {
        nextLevelNode.put(child.getChar(), child);
    }

    public void setTransfer(Node child) {
        go.put(child.getChar(), child);
    }

    public Node getChild(char ch) {
        return nextLevelNode.get(ch);
    }

    public Node getTransfer(char ch) {
        return go.get(ch);
    }

    public Node getLastTransfer() {
        Node last = null ; for( Node item : go.values() ) last = item;
        return last;
    }

    private char getChar() {
        return parentChar;
    }

    public void setIndex(int index) {
        indexOfShape = index;
    }

    public int getIndex() {
        return indexOfShape;
    }

    public boolean childExist(char ch) {
        return nextLevelNode.get(ch) != null;
    }

    public boolean transferExist(char ch) {
        return go.get(ch) != null;
    }

}