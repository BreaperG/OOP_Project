package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.util.HashSet;
import java.util.Set;

/**
 * Операция за проверка дали езикът на автомата е краен (не съдържа полезни цикли).
 */
public class IsFiniteOperation {

    /**
     * Изпълнява проверката.
     *
     * @param automaton Автоматът за проверка.
     * @return true, ако езикът е краен, иначе false.
     */
    public boolean execute(Automaton automaton) {
        Vertex start = automaton.getStartVertex();
        if (start == null) return true;
        Set<Vertex> usefulVertices = getVerticesThatReachFinal(automaton);

        if (!usefulVertices.contains(start)) {
            return true;
        }

        Set<Vertex> visited = new HashSet<>();
        Set<Vertex> recursionStack = new HashSet<>();

        boolean hasInfiniteLoop = hasCycle(start, visited, recursionStack, usefulVertices);
        return !hasInfiniteLoop;
    }

    /**
     * Рекурсивен DFS метод за търсене на цикли в графа.
     *
     * @param current Текущият връх.
     * @param visited Посетените върхове.
     * @param recursionStack Стек на рекурсията за засичане на цикъл.
     * @param useful Множеството от полезни върхове (които водят към финал).
     * @return true, ако намери цикъл, иначе false.
     */
    private boolean hasCycle(Vertex current, Set<Vertex> visited, Set<Vertex> recursionStack, Set<Vertex> useful) {
        if (!useful.contains(current)) {
            return false;
        }

        if (recursionStack.contains(current)) {
            return true;
        }

        if (visited.contains(current)) {
            return false;
        }

        visited.add(current);
        recursionStack.add(current);

        for (Arc arc : current.getArcs()) {
            if (hasCycle(arc.getPointedVertex(), visited, recursionStack, useful)) {
                return true;
            }
        }

        recursionStack.remove(current);
        return false;
    }

    /**
     * Намира всички върхове, от които има път до поне един финален връх.
     *
     * @param a Автоматът.
     * @return Множество от полезни върхове.
     */
    private Set<Vertex> getVerticesThatReachFinal(Automaton a) {
        Set<Vertex> useful = new HashSet<>();
        for (Vertex v : a.getVertices()) {
            if (canReachFinal(v, new HashSet<>())) {
                useful.add(v);
            }
        }
        return useful;
    }

    /**
     * Помощен метод, проверяващ дали даден връх достига до финал.
     *
     * @param v Върхът.
     * @param visited Множество от посетени върхове за предпазване от безкрайни цикли.
     * @return true, ако достига до финал.
     */
    private boolean canReachFinal(Vertex v, Set<Vertex> visited) {
        if (v.isFinal()) return true;

        visited.add(v);
        for (Arc arc : v.getArcs()) {
            Vertex next = arc.getPointedVertex();
            if (!visited.contains(next)) {
                if (canReachFinal(next, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
}