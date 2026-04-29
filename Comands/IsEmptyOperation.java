package Comands;

import Automaton.Automaton;
import Automaton.Vertex;
import Automaton.Arc;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IsEmptyOperation {

    public boolean execute(Automaton automaton) {
        Vertex start = automaton.getStartVertex();
        if (start == null) return true;

        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();

            if (current.isFinal()) {
                return false;
            }

            for (Arc arc : current.getArcs()) {
                Vertex neighbor = arc.getPointedVertex();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    stack.push(neighbor);
                }
            }
        }

        return true;
    }
}
