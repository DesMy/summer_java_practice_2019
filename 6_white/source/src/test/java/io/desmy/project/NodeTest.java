package io.desmy.project;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void getNextLevelNode() {
        HashMap<Character, Node> actual = new HashMap<>();
        HashMap<Character, Node> expected = new Node().getNextLevelNode();

        assertEquals(expected, actual);

        Bohr bohrOne = new Bohr();
        bohrOne.stringToBohr("");
        expected = bohrOne.getHead().getNextLevelNode();

        assertEquals(expected, actual);

        Bohr bohrTwo = new Bohr();
        bohrTwo.stringToBohr("shape");
        Node weHaveAChildNode = new Node(bohrTwo.getHead(), 's');
        expected = bohrTwo.getHead().getNextLevelNode();
        actual.put('s', weHaveAChildNode);

        assertEquals(expected, actual);

        Bohr bohrThree = new Bohr();
        bohrThree.stringToBohr("shape test");
        Node nodeOne = new Node(bohrThree.getHead(), 's');
        Node nodeTwo = new Node(bohrThree.getHead(), 't');
        actual.remove('s');
        actual.put('s', nodeOne);
        actual.put('t', nodeTwo);
        expected = bohrThree.getHead().getNextLevelNode();

        assertEquals(expected, actual);

        Bohr bohrFour = new Bohr();
        bohrFour.stringToBohr("super shape");
        Node node = new Node(bohrFour.getHead(), 's');
        actual.remove('s');
        actual.remove('t');
        actual.put('s', node);
        expected = bohrFour.getHead().getNextLevelNode();

        assertEquals(expected, actual);
    }

    @Test
    public void getIndex() {
        int actual = -1;
        int expected = new Node().getIndex();

        assertEquals(expected, actual);

        actual = 5;
        expected = new Node(new Node(), 'c', actual).getIndex();

        assertEquals(expected, actual);
    }

    @Test
    public void setTransfer() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("a");
        AhoCor aho = new AhoCor();
        aho.processText("aaaaa", bohr);
        Node actual = bohr.getHead().getTransfer('a');
        bohr.getHead().setTransfer(bohr.getHead().getChild('a'));
        Node expected = bohr.getHead().getTransfer('a');

        assertEquals(expected, actual);
    }

    @Test
    public void getChild() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("set");
        Node actual = bohr.getHead().getNextLevelNode().get('s');
        Node expected = bohr.getHead().getChild('s');

        assertEquals(expected, actual);
    }

    @Test
    public void getTransfer() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("sh st");
        Node s = bohr.getHead().getNextLevelNode().get('s');
        Node actual = s.getGo().get('h');
        Node expected = s.getTransfer('h');  //Но важно понимать, что в обоих случаях null

        assertEquals(expected, actual);

        AhoCor aho = new AhoCor();
        aho.processText("stix", bohr);

        actual = s.getGo().get('h');
        expected = s.getTransfer('h');

        assertEquals(expected, actual);
    }

    @Test
    public void setIndex() {
        int actual = 5;
        Node node = new Node();
        node.setIndex(actual);
        int expected = node.getIndex();

        assertEquals(expected, actual);
    }

    @Test
    public void childExist() {
        boolean actual;
        boolean expected;
        Bohr bohrOne = new Bohr();
        bohrOne.stringToBohr("s t d");
        expected = bohrOne.getHead().childExist('t');
        actual = true;

        assertEquals(expected, actual);

        expected = bohrOne.getHead().childExist('i');
        actual = false;

        assertEquals(expected, actual);

        Bohr bohrTwo = new Bohr();
        expected = bohrTwo.getHead().childExist('a');
        actual = false;

        assertEquals(expected, actual);
    }

    @Test
    public void transferExist() {
        boolean actual;
        boolean expected;
        Bohr bohrOne = new Bohr();
        bohrOne.stringToBohr("s t d");
        expected = bohrOne.getHead().transferExist('t');
        actual = false;  //До прогона алгоритма переходы по transfer не определены

        assertEquals(expected, actual);

        AhoCor aho = new AhoCor();
        aho.processText("stix", bohrOne);
        expected = bohrOne.getHead().transferExist('t');
        actual = true;

        assertEquals(expected, actual);

        expected = bohrOne.getHead().transferExist('i');
        actual = false;

        assertEquals(expected, actual);

        Bohr bohrTwo = new Bohr();
        expected = bohrTwo.getHead().childExist('a');
        actual = false;

        assertEquals(expected, actual);

        AhoCor ahoCor = new AhoCor();
        ahoCor.processText("aaaaab", bohrTwo);
        expected = bohrTwo.getHead().childExist('a');
        actual = false;

        assertEquals(expected, actual);
    }

    @Test
    public void getGo() {
        LinkedHashMap<Character, Node> actual = new LinkedHashMap<>();
        LinkedHashMap<Character, Node> expected = new Node().getGo();

        assertEquals(expected, actual);

        Bohr bohr = new Bohr();
        bohr.stringToBohr("s t d");
        expected = bohr.getHead().getGo();

        assertEquals(expected, actual);

        AhoCor aho = new AhoCor();
        aho.processText("stand alone", bohr);

        actual.put('s', bohr.getHead().getChild('s'));
        actual.put('t', bohr.getHead().getChild('t'));
        actual.put('d', bohr.getHead().getChild('d'));
        LinkedHashMap<Character, Node> expectedQ = bohr.getHead().getGo();

        assertEquals(expectedQ, actual);
    }
}