package ComandLine;

import Automaton.Automaton;
import Comands.OpenOperation;
import Comands.SaveOperation;

import java.util.HashMap;
import java.util.Map;

public class AutomatonManager {
    private Map<Integer, Automaton> automata;
    private int nextAutomatonId;

    public AutomatonManager(){
        this.automata=new HashMap<>();
        this.nextAutomatonId = 1;
    }

    public void openFile(String filename) {
        OpenOperation openOp = new OpenOperation();
        Automaton loadedAutomaton = openOp.execute(filename);

        if (loadedAutomaton != null) {
            int newId = nextAutomatonId++;
            loadedAutomaton.setId(newId);

            this.automata.put(newId, loadedAutomaton);
            System.out.println("Успешно зареден автомат. Присвоено ново ID: " + newId);
        }
    }

    public void closeDocument() {
        this.automata.clear();
        this.nextAutomatonId = 1;
        System.out.println("Паметта е изчистена успешно.");
    }

    public void saveAutomaton(int id, String filename){
        Automaton automatonToSave=this.automata.get(id);
        if(automatonToSave==null){
            System.out.println("Грешка: Автомат с ID " + id + " не съществува в паметта.");
            return;
        }
        SaveOperation saveOp = new SaveOperation();
        saveOp.execute(automatonToSave, filename);
    }

    public void addAutomata(Automaton automaton){

    }

    public void listAutomata(){

    }

    public void printAutomaton(int id){

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
