package ComandLine;

import Automaton.Automaton;
import Comands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AutomatonManager {
    private Map<Integer, Automaton> automata;
    private int nextAutomatonId;

    public AutomatonManager() {
        this.automata = new HashMap<>();
        this.nextAutomatonId = 1;
    }

    public void openFile(String filename) throws Exception {
        OpenOperation openOp = new OpenOperation();
        Automaton loadedAutomaton = openOp.execute(filename);

        if (loadedAutomaton != null) {
            int newId = nextAutomatonId++;
            loadedAutomaton.setId(newId);
            this.automata.put(newId, loadedAutomaton);
        }
    }

    public void closeDocument() {
        this.automata.clear();
        this.nextAutomatonId = 1;
    }

    public void saveAutomaton(int id, String filename) throws Exception {
        Automaton automatonToSave = this.automata.get(id);
        if (automatonToSave == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува в паметта.");
        }
        SaveOperation saveOp = new SaveOperation();
        saveOp.execute(automatonToSave, filename);
    }

    public int createEmptyAutomaton() {
        int newId = nextAutomatonId++;
        Automaton newAutomaton = new Automaton(newId);
        this.automata.put(newId, newAutomaton);
        return newId;
    }

    public int addVertexToAutomaton(int autId, boolean isFinal) {
        Automaton automaton = this.automata.get(autId);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + autId + " не съществува.");
        }

        AddVertexOperation addVertexOp = new AddVertexOperation();
        return addVertexOp.execute(automaton, isFinal);
    }

    public void addArcToAutomaton(int autId, int fromVertexId, char symbol, int toVertexId) {
        Automaton automaton = this.automata.get(autId);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + autId + " не съществува.");
        }

        AddArcOperation addArcOp = new AddArcOperation();
        addArcOp.execute(automaton, fromVertexId, symbol, toVertexId);
    }

    public Set<Integer> listAutomata() {
        return this.automata.keySet();
    }

    public String printAutomaton(int id) {
        Automaton automaton = this.automata.get(id);

        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува в паметта.");
        }

        PrintOperation printOp = new PrintOperation();
        return printOp.execute(automaton);
    }

    public boolean recognizeWord(int id, String word) throws Exception {
        Automaton automaton = this.automata.get(id);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        RecognizeOperation recognizeOp = new RecognizeOperation();
        return recognizeOp.execute(automaton, word);
    }

    public boolean isLanguageEmpty(int id) {
        Automaton automaton = this.automata.get(id);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        IsEmptyOperation emptyOp = new IsEmptyOperation();
        return emptyOp.execute(automaton);
    }

    public int unionAutomata(int id1, int id2) throws Exception {
        Automaton a1 = this.automata.get(id1);
        Automaton a2 = this.automata.get(id2);

        if (a1 == null || a2 == null) {
            throw new IllegalArgumentException("Един или и двата автомата не съществуват.");
        }

        int newId = nextAutomatonId++;
        UnionOperation unionOp = new UnionOperation();
        Automaton newAutomaton = unionOp.execute(a1, a2, newId);

        this.automata.put(newId, newAutomaton);
        return newId;
    }

    public int concatAutomata(int id1, int id2) throws Exception {
        Automaton a1 = this.automata.get(id1);
        Automaton a2 = this.automata.get(id2);

        if (a1 == null || a2 == null) {
            throw new IllegalArgumentException("Един или и двата автомата не съществуват.");
        }

        int newId = nextAutomatonId++;
        ConcatOperation concatOp = new ConcatOperation();
        Automaton newAutomaton = concatOp.execute(a1, a2, newId);

        this.automata.put(newId, newAutomaton);
        return newId;
    }

    public int positiveClosure(int id) throws Exception {
        Automaton a = this.automata.get(id);

        if (a == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        int newId = nextAutomatonId++;
        PositiveClosureOperation closureOp = new PositiveClosureOperation();
        Automaton newAutomaton = closureOp.execute(a, newId);

        this.automata.put(newId, newAutomaton);
        return newId;
    }

    public int createFromRegex(String regex) throws Exception {
        CreateFromRegexOperation regexOp = new CreateFromRegexOperation();

        Automaton newAutomaton = regexOp.execute(regex);

        int newId = nextAutomatonId++;
        newAutomaton.setId(newId);

        this.automata.put(newId, newAutomaton);

        return newId;
    }

    public int determinizeAutomaton(int id) throws Exception {
        Automaton nfa = this.automata.get(id);

        if (nfa == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        int newId = nextAutomatonId++;
        DeterminizeOperation determOp = new DeterminizeOperation();
        Automaton dfa = determOp.execute(nfa, newId);

        this.automata.put(newId, dfa);
        return newId;
    }
    public boolean isLanguageFinite(int id) {
        Automaton automaton = this.automata.get(id);

        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        IsFiniteOperation finiteOp = new IsFiniteOperation();
        return finiteOp.execute(automaton);
    }

    public boolean isDeterministic(int id) {
        Automaton automaton = this.automata.get(id);

        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        IsDeterministicOperation checkOp = new IsDeterministicOperation();
        return checkOp.execute(automaton);
    }

}