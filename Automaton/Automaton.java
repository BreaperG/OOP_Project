package Automaton;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Класът Automaton представлява цялостния краен автомат (НКА или ДКА).
 * Той управлява колекцията от върхове и съхранява референция към началното състояние.
 */
public class Automaton implements Serializable {
    private int id;
    private int vertexCounter;
    private Vertex startVertex;
    private Set<Vertex> vertices;

    /**
     * Конструктор за създаване на нов празен автомат.
     * Автоматично генерира първоначален връх (връх 0), който става начален.
     *
     * @param id Уникален идентификатор на автомата.
     */
    public Automaton(int id){
        this.id = id;
        this.vertices = new HashSet<>();
        this.vertexCounter = 0;
        this.startVertex = createVertex();
    }

    /**
     * Създава нов връх в автомата и автоматично инкрементира брояча за ID-та на върховете.
     *
     * @return Новосъздаденият връх.
     */
    public Vertex createVertex(){
        Vertex newVertex = new Vertex(vertexCounter++);
        vertices.add(newVertex);
        return newVertex;
    }

    /**
     * Променя идентификатора на автомата.
     *
     * @param id Новото ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Връща идентификатора на автомата.
     *
     * @return ID на автомата.
     */
    public int getId() {
        return id;
    }

    /**
     * Връща множеството от всички върхове в този автомат.
     *
     * @return Множество (Set) от върхове.
     */
    public Set<Vertex> getVertices() {
        return vertices;
    }

    /**
     * Връща началния връх на автомата.
     *
     * @return Началният връх.
     */
    public Vertex getStartVertex(){
        return startVertex;
    }

    /**
     * Задава нов начален връх на автомата.
     *
     * @param startVertex Върхът, който трябва да стане начален.
     */
    public void setStartVertex(Vertex startVertex){
        this.startVertex = startVertex;
    }
}