package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.util.HashSet;
import java.util.Set;

public class IsFiniteOperation {

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

    private Set<Vertex> getVerticesThatReachFinal(Automaton a) {
        Set<Vertex> useful = new HashSet<>();
        for (Vertex v : a.getVertices()) {
            if (canReachFinal(v, new HashSet<>())) {
                useful.add(v);
            }
        }
        return useful;
    }

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