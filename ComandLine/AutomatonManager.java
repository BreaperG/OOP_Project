package ComandLine;

import Automaton.Automaton;

import java.util.HashMap;
import java.util.Map;

public class AutomatonManager {
    private Map<Integer, Automaton> automata;

    public AutomatonManager(){
        this.automata=new HashMap<>();
    }

    public void addAutomata(Automaton automaton){

    }

    public void clearAll() {

    }

    public void listAutomata(){

    }

    public void printAutomaton(int id){

    }

    public void saveAutomaton(int id, String filename){

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
}
