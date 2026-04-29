package Automaton;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класът Vertex представлява единичен връх в графа на автомата.
 * Всеки връх има уникален идентификатор, статус (финален/приемащ) и списък от изходящи дъги към други върхове.
 */
public class Vertex implements Serializable {
    private int id;
    private ArrayList<Arc> arcs;
    private boolean isFinal;

    public Vertex(int id){
        this.id=id;
        this.arcs=new ArrayList<>();
        this.isFinal=false;
    }

    public int getId() {
        return id;
    }
    public boolean isFinal(){
        return isFinal;
    }
    public ArrayList<Arc> getArcs(){
        return arcs;
    }

    public void setFinal(boolean isFinal){
        this.isFinal= isFinal;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void addArc(char symbol,Vertex pointedVertex){
        arcs.add(new Arc(symbol,pointedVertex));
    }

}
