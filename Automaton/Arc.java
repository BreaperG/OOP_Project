package Automaton;

import java.io.Serializable;

/**
 * Класът Arc представлява насочена дъга в графа на недетерминирания краен автомат.
 * Всяка дъга има символ от азбуката или епсилон(празна дума) и сочи към конкретен връх.
 */
public class Arc implements Serializable {
    /** * Константа за празна дума.
     */
    public static final char EPSILON = 'ε';

    private char symbol;
    private Vertex pointedVertex;

    public Arc(char symbol,Vertex pointedVertex){
        if (!isValidSymbol(symbol)) {
            throw new IllegalArgumentException("Етикетът трябва да е малка латинска буква, цифра или ε.");
        }
        this.symbol=symbol;
        this.pointedVertex=pointedVertex;
    }

    public char getSymbol() {
        return symbol;
    }

    public Vertex getPointedVertex() {
        return pointedVertex;
    }

    private boolean isValidSymbol(char c) {
        return c == EPSILON || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }
}
