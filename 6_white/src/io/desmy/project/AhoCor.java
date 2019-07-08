package io.desmy.project;

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

        public int getPosition() { return position; }
        public int getTemplate() { return template; }
    }
    public HashMap<Integer, resultPair> answer;
    public AhoCor(){
        answer = new HashMap<>();
    }
    int counter = 0;

    public void processText(String text, Bohr trie) {
        Node cur = trie.getHead();
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
            } while (dsl != trie.getHead());
        }
    }

    public void stepRight(char ch, Bohr trie, int i) {
        Node cur = getLink(trie.getNow(), trie, ch);
        if(cur == trie.getNow()) {
            cur = trie.getHead();
            return;
        }
        trie.setNow(cur);
        Node dsl = cur;
        do {
            if(dsl.getIndex() != -1)
                answer.put(counter++, new resultPair(i+1, dsl.getIndex()));
            dsl = getDictSuffLink(dsl, trie); //поиск сжатой суффиксной ссылки
        } while (dsl != trie.getHead());
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
            else if (cur == trie.getHead()) {
                cur.setTransfer(trie.getHead());
            }
            else {
                cur.setTransfer(getLink(getSuffLink(cur, trie),trie, c));
            }
        }
        if (cur.transferExist(c)){
            return cur.getTransfer(c);
        }
        else{
            return trie.getHead();
        }
    }

    /**  Поиск суффиксной ссылки
     * Пока не найден ребенок по символу
     * исследуем суффиксы пока не дошли до корня
     */
    public Node getSuffLink(Node cur, Bohr trie) {
        if (cur.suffixLink == null){
            if (cur == trie.getHead() || cur.parentNode == trie.getHead()){
                cur.suffixLink = trie.getHead();
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
            else if (getSuffLink(cur, trie) == trie.getHead()){
                cur.goodSuffixLink = trie.getHead();
            }
            else{
                cur.goodSuffixLink = getDictSuffLink(getSuffLink(cur, trie), trie);
            }
        }
        return cur.goodSuffixLink;
    }
}
