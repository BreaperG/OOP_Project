package Comands;

import Automaton.Automaton;
import Automaton.Vertex;
import Automaton.Arc;

import java.util.HashSet;
import java.util.Set;

/**
 * Операция за проверка дали автоматът разпознава подадена дума (символен низ).
 */
public class RecognizeOperation {

    /**
     * Симулира движението на думата през графа на автомата, вземайки предвид епсилон преходите.
     *
     * @param automaton Автоматът.
     * @param word Думата за разпознаване.
     * @return true, ако думата стига до финален връх, иначе false.
     */
    public boolean execute(Automaton automaton, String word) {
        Set<Vertex> currentStates = new HashSet<>();
        currentStates.add(automaton.getStartVertex());
        currentStates = epsilonClosure(currentStates);

        for (int i = 0; i < word.length(); i++) {
            char symbol = word.charAt(i);
            Set<Vertex> nextStates = new HashSet<>();

            for (Vertex v : currentStates) {
                for (Arc arc : v.getArcs()) {
                    if (arc.getSymbol() == symbol) {
                        nextStates.add(arc.getPointedVertex());
                    }
                }
            }

            currentStates = epsilonClosure(nextStates);

            if (currentStates.isEmpty()) {
                return false;
            }
        }

        for (Vertex v : currentStates) {
            if (v.isFinal()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Намира епсилон обвивката за дадено множество от състояния.
     *
     * @param states Множество от върхове.
     * @return Разширеното множество с върховете, достижими безплатно (с епсилон).
     */
    private Set<Vertex> epsilonClosure(Set<Vertex> states) {
        Set<Vertex> closure = new HashSet<>(states);
        boolean changed = true;

        while (changed) {
            int oldSize = closure.size();
            Set<Vertex> toAdd = new HashSet<>();

            for (Vertex v : closure) {
                for (Arc arc : v.getArcs()) {
                    if (arc.getSymbol() == Arc.EPSILON) {
                        toAdd.add(arc.getPointedVertex());
                    }
                }
            }
            closure.addAll(toAdd);
            changed = (closure.size() > oldSize);
        }
        return closure;
    }
}