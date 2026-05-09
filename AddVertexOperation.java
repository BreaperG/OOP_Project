package Comands;

import Automaton.Automaton;
import Automaton.Vertex;

/**
 * Операция за добавяне на нов връх към съществуващ автомат.
 */
public class AddVertexOperation {

    /**
     * Изпълнява добавянето на върха и определя дали той е финален.
     *
     * @param automaton Автоматът, към който се добавя върха.
     * @param isFinal true, ако върхът трябва да е приемащо състояние.
     * @return ID-то на новосъздадения връх.
     */
    public int execute(Automaton automaton, boolean isFinal) {
        Vertex newVertex = automaton.createVertex();
        newVertex.setFinal(isFinal);
        return newVertex.getId();
    }
}