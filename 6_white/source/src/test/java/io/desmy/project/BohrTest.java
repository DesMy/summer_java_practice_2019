package io.desmy.project;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class BohrTest {

    @Test
    public void stringToBohr() {
        Bohr expected = new Bohr();
        expected.stringToBohr("he she it");

        Node head = new Node();
        Node s = new Node(head, 's');
        Node h = new Node(head, 'h');
        Node i = new Node(head, 'i');
        Node he = new Node(h, 'e', 0);
        Node sh = new Node(s, 'h');
        Node it = new Node(i, 't', 2);
        Node she = new Node(sh, 'e', 1);
        head.getNextLevelNode().put('s', s);
        head.getNextLevelNode().put('h', h);
        head.getNextLevelNode().put('i', i);

        s.getNextLevelNode().put('h', sh);
        h.getNextLevelNode().put('e', he);
        i.getNextLevelNode().put('t', it);

        sh.getNextLevelNode().put('e', she);

        assertEquals(expected.getHead().getNextLevelNode().size(), head.getNextLevelNode().size());
        assertEquals(expected.getHead().getNextLevelNode().get('s').getNextLevelNode().size(), s.getNextLevelNode().size());
        assertEquals(expected.getHead().getNextLevelNode().get('h').getNextLevelNode().size(), h.getNextLevelNode().size());
        assertEquals(expected.getHead().getNextLevelNode().get('i').getNextLevelNode().size(), i.getNextLevelNode().size());

        assertEquals(expected.getHead().getNextLevelNode().get('s').getNextLevelNode().get('h').getNextLevelNode().size(), sh.getNextLevelNode().size());
        assertEquals(expected.getHead().getNextLevelNode().get('h').getNextLevelNode().get('e').getNextLevelNode().size(), he.getNextLevelNode().size());
        assertEquals(expected.getHead().getNextLevelNode().get('i').getNextLevelNode().get('t').getNextLevelNode().size(), it.getNextLevelNode().size());
        assertEquals(expected.getHead().getNextLevelNode().get('s').getNextLevelNode().get('h').getNextLevelNode().get('e').getNextLevelNode().size(), she.getNextLevelNode().size());

        assertEquals(expected.getHead().getNextLevelNode().get('s').parentChar, s.parentChar);
        assertEquals(expected.getHead().getNextLevelNode().get('h').parentChar, h.parentChar);
        assertEquals(expected.getHead().getNextLevelNode().get('i').parentChar, i.parentChar);

        assertEquals(expected.getHead().getNextLevelNode().get('h').getNextLevelNode().get('e').indexOfShape, he.indexOfShape);
        assertEquals(expected.getHead().getNextLevelNode().get('i').getNextLevelNode().get('t').indexOfShape, it.indexOfShape);
        assertEquals(expected.getHead().getNextLevelNode().get('s').getNextLevelNode().get('h').getNextLevelNode().get('e').indexOfShape, she.indexOfShape);
    }

    @Test
    public void stringToPair() {
        HashMap<Integer, Bohr.Pair> actual = new HashMap<>();
        Bohr bohr = new Bohr();

        assertEquals(bohr.getPair(), actual);

        bohr.stringToPair("he she it");
        actual.put(0, bohr.getPair().get(0));
        actual.put(1, bohr.getPair().get(1));
        actual.put(2, bohr.getPair().get(2));

        assertEquals(bohr.getPair(), actual);

        assertEquals(actual.get(0).getShape().equals(bohr.getPair().get(0).getShape()), true);
        assertEquals(actual.get(1).getShape().equals(bohr.getPair().get(1).getShape()), true);
        assertEquals(actual.get(2).getShape().equals(bohr.getPair().get(2).getShape()), true);

    }

    @Test
    public void getPair() {
        HashMap<Integer, Bohr.Pair> actual = new HashMap<>();
        Bohr bohr = new Bohr();

        assertEquals(bohr.getPair(), actual);

        bohr.stringToPair("he she it");
        actual.put(0, bohr.getPair().get(0));
        actual.put(1, bohr.getPair().get(1));
        actual.put(2, bohr.getPair().get(2));

        assertEquals(bohr.getPair(), actual);

        assertEquals(actual.get(0).getShape().equals(bohr.getPair().get(0).getShape()), true);
        assertEquals(actual.get(1).getShape().equals(bohr.getPair().get(1).getShape()), true);
        assertEquals(actual.get(2).getShape().equals(bohr.getPair().get(2).getShape()), true);
    }

    @Test
    public void getHead() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("s p d");

        assertEquals(bohr.getHead().getNextLevelNode().size(), 3);

        Bohr bohrQ = new Bohr();
        bohrQ.stringToBohr("s p d i m b");

        assertEquals(bohrQ.getHead().getNextLevelNode().size(), 6);
    }

    @Test
    public void setNow() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("shhh sttt sddd");
        bohr.setNow(bohr.getHead().getNextLevelNode().get('s'));

        Node node = new Node(bohr.getHead(), 's');
        node.getNextLevelNode().put('h', bohr.getHead().getNextLevelNode().get('s').getNextLevelNode().get('h'));
        node.getNextLevelNode().put('t', bohr.getHead().getNextLevelNode().get('s').getNextLevelNode().get('t'));
        node.getNextLevelNode().put('d', bohr.getHead().getNextLevelNode().get('s').getNextLevelNode().get('d'));

        assertEquals(node.getNextLevelNode().size(), bohr.getNow().getNextLevelNode().size());
    }

    @Test
    public void getNow() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("ab bc cd");

        assertEquals(bohr.getNow(), bohr.getHead());

        AhoCor aho = new AhoCor();
        aho.stepRight('b', bohr, 0);

        assertEquals(bohr.getNow(), bohr.getHead().getNextLevelNode().get('b'));
        assertEquals(bohr.getNow(), bohr.getHead().getChild('b'));
    }
}