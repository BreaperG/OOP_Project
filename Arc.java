package Automaton;

import java.io.Serializable;

/**
 * Класът Arc представлява насочена дъга (преход) в графа на автомата.
 * Всяка дъга има символ от азбуката или епсилон (празна дума) и сочи към конкретен връх.
 * Реализира интерфейса Serializable, за да може да бъде запазван във файл.
 */
public class Arc implements Serializable {
    /**
     * Константа, представляваща епсилон преход (преход без прочитане на символ).
     */
    public static final char EPSILON = 'ε';

    private char symbol;
    private Vertex pointedVertex;

    /**
     * Конструктор за създаване на нова дъга.
     *
     * @param symbol Символът, при който се извършва преходът.
     * @param pointedVertex Върхът, към който сочи тази дъга.
     * @throws IllegalArgumentException Ако символът не е валиден.
     */
    public Arc(char symbol, Vertex pointedVertex){
        if (!isValidSymbol(symbol)) {
            throw new IllegalArgumentException("Етикетът трябва да е малка латинска буква, цифра или ε.");
        }
        this.symbol = symbol;
        this.pointedVertex = pointedVertex;
    }

    /**
     * Връща символа на прехода.
     *
     * @return Символът, присвоен на дъгата.
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Връща върха, към който сочи дъгата.
     *
     * @return Целевият връх на прехода.
     */
    public Vertex getPointedVertex() {
        return pointedVertex;
    }

    /**
     * Проверява дали подаденият символ е валиден (малка буква, цифра или епсилон).
     *
     * @param c Символът за проверка.
     * @return true, ако символът е валиден, иначе false.
     */
    private boolean isValidSymbol(char c) {
        return c == EPSILON || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }
}