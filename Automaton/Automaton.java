package Automaton;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Класът Automaton представлява целия недетерминиран краен автомат (НКА).
 * Той съхранява всички върхове и пази референция към началния връх.
 */
public class Automaton implements Serializable {
    private int id;
    private int vertexCounter;
    private Vertex startVertex;
    private Set<Vertex> vertices;

    public Automaton(int id){
        this.id=id;
        this.vertices=new HashSet<>();
        this.vertexCounter=0;
        this.startVertex=createVertex();
    }

    public Vertex createVertex(){
        Vertex newVertex= new Vertex(vertexCounter++);
        vertices.add(newVertex);
        return newVertex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public Vertex getStartVertex(){
        return startVertex;
    }
    public void setStartVertex(Vertex startVertex){
        this.startVertex=startVertex;
    }
}
