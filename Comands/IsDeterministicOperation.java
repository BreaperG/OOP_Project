package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.util.HashSet;
import java.util.Set;

public class IsDeterministicOperation {

    public boolean execute(Automaton automaton) {
        for (Vertex v : automaton.getVertices()) {
            Set<Character> seenSymbols = new HashSet<>();

            for (Arc arc : v.getArcs()) {
                char symbol = arc.getSymbol();

                if (symbol == Arc.EPSILON) {
                    return false;
                }

                if (seenSymbols.contains(symbol)) {
                    return false;
                }

                seenSymbols.add(symbol);
            }
        }

        return true;
    }
}
