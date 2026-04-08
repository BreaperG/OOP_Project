package Comands;

import Automaton.Automaton;
import Automaton.Vertex;

public class AddArcOperation {

    public void execute(Automaton automaton, int fromVertexId, char symbol, int toVertexId) {
        Vertex fromVertex = getVertexById(automaton, fromVertexId);
        Vertex toVertex = getVertexById(automaton, toVertexId);

        fromVertex.addArc(symbol, toVertex);
    }

    private Vertex getVertexById(Automaton automaton, int vertexId) {
        for (Vertex v : automaton.getVertices()) {
            if (v.getId() == vertexId) {
                return v;
            }
        }
        throw new IllegalArgumentException("Връх с ID " + vertexId + " не съществува в автомата.");
    }
}