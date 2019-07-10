package io.desmy.project;

import org.junit.Test;

import static org.junit.Assert.*;

public class AhoCorTest {

    @Test
    public void processText() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("s t d");

        AhoCor aho = new AhoCor();
        aho.processText("ssttdd", bohr);

        assertEquals(aho.answer.size(), 6);
        assertEquals(aho.answer.get(0).getPosition(), 1);
        assertEquals(aho.answer.get(1).getPosition(), 2);
        assertEquals(aho.answer.get(2).getPosition(), 3);
        assertEquals(aho.answer.get(3).getPosition(), 4);
        assertEquals(aho.answer.get(4).getPosition(), 5);
        assertEquals(aho.answer.get(5).getPosition(), 6);

        assertEquals(aho.answer.get(0).getTemplate(), 0);
        assertEquals(aho.answer.get(1).getTemplate(), 0);
        assertEquals(aho.answer.get(2).getTemplate(), 1);
        assertEquals(aho.answer.get(3).getTemplate(), 1);
        assertEquals(aho.answer.get(4).getTemplate(), 2);
        assertEquals(aho.answer.get(5).getTemplate(), 2);
    }

    @Test
    public void stepRight() {
        Bohr bohr = new Bohr();
        bohr.stringToBohr("s t d");
        AhoCor aho = new AhoCor();
        aho.stepRight('d', bohr, 0);

        assertEquals(bohr.getNow(), bohr.getHead().getChild('d'));

        Bohr B = new Bohr();
        B.stringToBohr("he ");
        AhoCor ahoCor = new AhoCor();
        ahoCor.stepRight('h', B, 0);
        ahoCor.stepRight('e', B, 1);

        assertEquals(B.getNow(), B.getHead().getChild('h').getChild('e'));
    }
}