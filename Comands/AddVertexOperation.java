package Comands;

import Automaton.Automaton;
import Automaton.Vertex;

public class AddVertexOperation {

    public int execute(Automaton automaton, boolean isFinal) {
        Vertex newVertex = automaton.createVertex();
        newVertex.setFinal(isFinal);
        return newVertex.getId();
    }
}
