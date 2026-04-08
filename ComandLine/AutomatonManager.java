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

    public void isLanguageEmpty(int id){

    }

    public void isDeterministic(int id){

    }

    public void recognizeWord(int id, String word){

    }

    public void unionAutomata(int id1, int id2){

    }

    public void concatAutomata(int id1, int id2){

    }

    public void positiveClosure(int id){

    }

    public void createFromRegex(String regex){

    }

    public void determinizeAutomaton(int id){

    }

    public void isLanguageFinite(int id){

    }
} // <--- Едва тук е истинският край на класа