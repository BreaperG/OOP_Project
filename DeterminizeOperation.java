package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.util.*;

/**
 * Операция за преобразуване на Недетерминиран краен автомат (НКА) в Детерминиран (ДКА).
 * Имплементира алгоритъма Subset Construction (Построение чрез подмножества).
 */
public class DeterminizeOperation {

    /**
     * Детерминизира подадения НКА.
     *
     * @param nfa Оригиналният недетерминиран автомат.
     * @param newId Уникалното ID, което ще бъде дадено на новия ДКА.
     * @return Новият детерминиран автомат.
     */
    public Automaton execute(Automaton nfa, int newId) {
        Automaton dfa = new Automaton(newId);
        Set<Character> alphabet = getAlphabet(nfa);

        Set<Vertex> nfaStartSet = new HashSet<>();
        nfaStartSet.add(nfa.getStartVertex());
        Set<Vertex> dfaStartSubset = epsilonClosure(nfaStartSet);

        Map<Set<Integer>, Vertex> subsetToDfaVertex = new HashMap<>();
        Queue<Set<Vertex>> unprocessedSubsets = new LinkedList<>();

        Vertex dfaStartVertex = dfa.getStartVertex();
        dfaStartVertex.setFinal(containsFinal(dfaStartSubset));

        Set<Integer> startSubsetIds = getIds(dfaStartSubset);
        subsetToDfaVertex.put(startSubsetIds, dfaStartVertex);
        unprocessedSubsets.add(dfaStartSubset);

        while (!unprocessedSubsets.isEmpty()) {
            Set<Vertex> currentSubset = unprocessedSubsets.poll();
            Set<Integer> currentSubsetIds = getIds(currentSubset);
            Vertex currentDfaVertex = subsetToDfaVertex.get(currentSubsetIds);

            for (char symbol : alphabet) {
                Set<Vertex> nextSubset = new HashSet<>();

                for (Vertex nfaVertex : currentSubset) {
                    for (Arc arc : nfaVertex.getArcs()) {
                        if (arc.getSymbol() == symbol) {
                            nextSubset.add(arc.getPointedVertex());
                        }
                    }
                }

                nextSubset = epsilonClosure(nextSubset);

                if (!nextSubset.isEmpty()) {
                    Set<Integer> nextSubsetIds = getIds(nextSubset);
                    Vertex nextDfaVertex = subsetToDfaVertex.get(nextSubsetIds);

                    if (nextDfaVertex == null) {
                        nextDfaVertex = dfa.createVertex();
                        nextDfaVertex.setFinal(containsFinal(nextSubset));
                        subsetToDfaVertex.put(nextSubsetIds, nextDfaVertex);
                        unprocessedSubsets.add(nextSubset);
                    }

                    currentDfaVertex.addArc(symbol, nextDfaVertex);
                }
            }
        }

        return dfa;
    }

    /**
     * Извлича всички използвани символи от азбуката на автомата (без епсилон).
     *
     * @param automaton Автоматът.
     * @return Множество от символи.
     */
    private Set<Character> getAlphabet(Automaton automaton) {
        Set<Character> alphabet = new HashSet<>();
        for (Vertex v : automaton.getVertices()) {
            for (Arc arc : v.getArcs()) {
                if (arc.getSymbol() != Arc.EPSILON) {
                    alphabet.add(arc.getSymbol());
                }
            }
        }
        return alphabet;
    }

    /**
     * Намира епсилон обвивката на множество от върхове.
     *
     * @param states Началните върхове.
     * @return Множеството от всички върхове, достижими само по епсилон преходи.
     */
    private Set<Vertex> epsilonClosure(Set<Vertex> states) {
        Set<Vertex> closure = new HashSet<>(states);
        Stack<Vertex> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            for (Arc arc : current.getArcs()) {
                if (arc.getSymbol() == Arc.EPSILON) {
                    Vertex neighbor = arc.getPointedVertex();
                    if (!closure.contains(neighbor)) {
                        closure.add(neighbor);
                        stack.push(neighbor);
                    }
                }
            }
        }
        return closure;
    }

    /**
     * Връща множество от идентификаторите на подадените върхове (използва се за ключ в Map).
     *
     * @param states Върховете.
     * @return Множество от ID-та.
     */
    private Set<Integer> getIds(Set<Vertex> states) {
        Set<Integer> ids = new HashSet<>();
        for (Vertex v : states) {
            ids.add(v.getId());
        }
        return ids;
    }

    /**
     * Проверява дали в дадено подмножество има поне един финален връх.
     *
     * @param states Подмножество от върхове.
     * @return true, ако има финален връх.
     */
    private boolean containsFinal(Set<Vertex> states) {
        for (Vertex v : states) {
            if (v.isFinal()) {
                return true;
            }
        }
        return false;
    }
}