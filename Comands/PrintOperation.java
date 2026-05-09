package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Операция за генериране на четима текстова репрезентация на автомата.
 */
public class PrintOperation {

    /**
     * Извлича всички върхове и дъги и ги форматира като текст.
     *
     * @param automaton Автоматът за отпечатване.
     * @return Форматиран String, описващ структурата на графа.
     */
    public String execute(Automaton automaton) {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Автомат ID: ").append(automaton.getId()).append(" ===\n");


        Vertex start = automaton.getStartVertex();
        sb.append("Начален връх: ").append(start != null ? start.getId() : "Няма").append("\n");


        List<Integer> finalStates = new ArrayList<>();
        for (Vertex v : automaton.getVertices()) {
            if (v.isFinal()) {
                finalStates.add(v.getId());
            }
        }
        sb.append("Финални върхове: ").append(finalStates.isEmpty() ? "Няма" : finalStates).append("\n");

        sb.append("Преходи:\n");
        boolean hasArcs = false;

        for (Vertex v : automaton.getVertices()) {
            for (Arc arc : v.getArcs()) {
                hasArcs = true;
                sb.append("  [")
                        .append(v.getId())
                        .append("] --(")
                        .append(arc.getSymbol())
                        .append(")--> [")
                        .append(arc.getPointedVertex().getId())
                        .append("]\n");
            }
        }

        if (!hasArcs) {
            sb.append("  (няма добавени преходи)\n");
        }

        sb.append("=========================");

        return sb.toString();
    }
}