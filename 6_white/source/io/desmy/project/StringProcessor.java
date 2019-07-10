package io.desmy.project;

import java.util.HashMap;

public class StringProcessor {
    static public String stringCorrecting(String str) {
        HashMap<String, Integer> pr = new HashMap<>();
        StringBuilder text = new StringBuilder(str.replaceAll("[^A-Za-zА-Яа-я0-9]", " "));
        text.append(' ');
        int j, k = 0;
        for(int i = 0; i < text.length(); i++)
            if(text.charAt(i) != ' ') {
                for (j = i; text.charAt(j) != ' '; j++);
                pr.put(text.substring(i, j), k++);
                i = j;
            }
        StringBuilder txt = new StringBuilder("");
        pr.forEach((key, value) -> {
            txt.append(key);
            txt.append(" ");
        });
        return txt.toString();
    }

    static public String stringDelShape(String str, String del) {
        HashMap<String, Integer> pr = new HashMap<>();
        StringBuilder text = new StringBuilder(str.replaceAll("[^A-Za-zА-Яа-я0-9]", " "));
        text.append(' ');
        int j, k = 0;
        for(int i = 0; i < text.length(); i++)
            if(text.charAt(i) != ' ') {
                for (j = i; text.charAt(j) != ' '; j++);
                pr.put(text.substring(i, j), k++);
                i = j;
            }

        HashMap<String, Integer> prDel = new HashMap<>();
        StringBuilder textDel = new StringBuilder(del.replaceAll("[^A-Za-zА-Яа-я0-9]", " "));
        textDel.append(' ');
        k = 0;
        for(int i = 0; i < textDel.length(); i++)
            if (textDel.charAt(i) != ' ') {
                for (j = i; textDel.charAt(j) != ' '; j++) ;
                prDel.put(textDel.substring(i, j), k++);
                i = j;
            }

        StringBuilder txt = new StringBuilder("");

        prDel.forEach((keyDel, valueDel) -> {
            pr.remove(keyDel);
        });

        pr.forEach((key, value) -> {
            txt.append(key);
            txt.append(" ");
        });

        return txt.toString();
    }
}
