package Automaton;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класът Vertex представлява единичен връх (състояние) в графа на автомата.
 * Пази информация за своя идентификатор, дали е финален връх и списък от своите изходящи дъги.
 */
public class Vertex implements Serializable {
    private int id;
    private ArrayList<Arc> arcs;
    private boolean isFinal;

    /**
     * Конструктор за създаване на нов връх.
     *
     * @param id Уникален идентификатор на върха в рамките на автомата.
     */
    public Vertex(int id){
        this.id = id;
        this.arcs = new ArrayList<>();
        this.isFinal = false;
    }

    /**
     * Връща идентификатора на върха.
     *
     * @return ID на върха.
     */
    public int getId() {
        return id;
    }

    /**
     * Проверява дали върхът е финален.
     *
     * @return true, ако върхът е финален, иначе false.
     */
    public boolean isFinal(){
        return isFinal;
    }

    /**
     * Връща списъка с всички изходящи дъги от този връх.
     *
     * @return Списък от обекти тип Arc.
     */
    public ArrayList<Arc> getArcs(){
        return arcs;
    }

    /**
     * Задава статуса на върха (финален или не).
     *
     * @param isFinal true, за да стане върхът финален, false в противен случай.
     */
    public void setFinal(boolean isFinal){
        this.isFinal = isFinal;
    }

    /**
     * Променя идентификатора на върха (използва се при конструиране на нови автомати).
     *
     * @param id Новото ID на върха.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Създава и добавя нова изходяща дъга към този връх.
     *
     * @param symbol Символът на прехода.
     * @param pointedVertex Върхът, към който ще води преходът.
     */
    public void addArc(char symbol, Vertex pointedVertex){
        arcs.add(new Arc(symbol, pointedVertex));
    }
}