import java.util.HashMap;

/**
 * функция processText проходит по тексту, использует по одному символу из него.
 * каждый раз происходит вызов поиска перехода и поиска сжатой суффиксной ссылки.
 */

public class AhoCor {
    public class resultPair {
        int position;
        int template;

        public resultPair(int pos, int temp) {
            position = pos;
            template = temp;
        }
    }
    public HashMap <Integer, resultPair> answer;
    public AhoCor(){
        answer = new HashMap<>();
    }

    public void processText(String text, Bohr trie) {
        Node cur = trie.head;
        Node dsl;
        int resultCounter = 0;
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            cur = getLink(cur, trie, c); //поиск перехода
            dsl = cur;
            do {
                if (dsl.getIndex() != -1) {
                    answer.put(resultCounter, new resultPair( i + 1, dsl.getIndex()));
                    resultCounter++;
                }
                dsl = getDictSuffLink(dsl, trie); //поиск сжатой суффиксной ссылки
            } while (dsl != trie.head);
        }
    }

    /**
     *  Переход к ребёнку
     *  * Если есть - возвращаем на него указатель
     *  * Если нет:
     *  * 1 Если родитель - корень, возвращаем корень
     *  * 2 Если нет - находим суффикс поменьше
     * @param cur - текущий узел
     * @param trie - бор
     * @param c - символ ребенка
     * @return
     */
    public Node getLink(Node cur, Bohr trie, char c) {
        if (!cur.transferExist(c)){
            if (cur.childExist(c)){
                cur.setTransfer(cur.getChild(c));
            }
            else if (cur == trie.head) {
                cur.setTransfer(trie.head);
            }
            else {
                cur.setTransfer(getLink(getSuffLink(cur, trie),trie, c));
            }
        }
        if (cur.transferExist(c)){
            return cur.getTransfer(c);
        }
        else{
            return cur.getLastTransfer();
        }
    }

    /**  Поиск суффиксной ссылки
     * Пока не найден ребенок по символу
     * исследуем суффиксы пока не дошли до корня
     */
    public Node getSuffLink(Node cur, Bohr trie) {
        if (cur.suffixLink == null){
            if (cur == trie.head || cur.parentNode == trie.head){
                cur.suffixLink = trie.head;
            }
            else{
                cur.suffixLink = getLink(getSuffLink(cur.parentNode, trie), trie, cur.parentChar);
            }
        }
        return cur.suffixLink;
    }

    /* Поиск сжатой суффиксной ссылки
     * пока очередная суффиксная ссылка не привела в терминал или корень
     */
    public Node getDictSuffLink(Node cur, Bohr trie) {
        if (cur.goodSuffixLink == null)
        {
            if (getSuffLink(cur, trie).indexOfShape != -1){
                cur.goodSuffixLink = getSuffLink(cur, trie);
            }
            else if (getSuffLink(cur, trie) == trie.head){
                cur.goodSuffixLink = trie.head;
            }
            else{
                cur.goodSuffixLink = getDictSuffLink(getSuffLink(cur, trie), trie);
            }
        }
        return cur.goodSuffixLink;
    }
}
