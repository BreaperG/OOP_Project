package Comands;

import Automaton.Automaton;
import Automaton.Vertex;

/**
 * Операция за добавяне на дъга (преход) между два върха в автомата.
 */
public class AddArcOperation {

    /**
     * Изпълнява добавянето на дъгата.
     *
     * @param automaton Автоматът, към който се добавя дъгата.
     * @param fromVertexId ID на началния връх.
     * @param symbol Символ на прехода.
     * @param toVertexId ID на целевия връх.
     */
    public void execute(Automaton automaton, int fromVertexId, char symbol, int toVertexId) {
        Vertex fromVertex = getVertexById(automaton, fromVertexId);
        Vertex toVertex = getVertexById(automaton, toVertexId);

        fromVertex.addArc(symbol, toVertex);
    }

    /**
     * Помощен метод за намиране на връх по неговото ID.
     *
     * @param automaton Автоматът, в който търсим.
     * @param vertexId ID-то на търсения връх.
     * @return Намереният връх.
     * @throws IllegalArgumentException Ако върхът не бъде намерен.
     */
    private Vertex getVertexById(Automaton automaton, int vertexId) {
        for (Vertex v : automaton.getVertices()) {
            if (v.getId() == vertexId) {
                return v;
            }
        }
        throw new IllegalArgumentException("Връх с ID " + vertexId + " не съществува в автомата.");
    }
}