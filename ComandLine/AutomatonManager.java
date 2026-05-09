package ComandLine;

import Automaton.Automaton;
import Comands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класът AutomatonManager играе ролята на диспечер.
 * Той съхранява всички заредени в паметта автомати и пренасочва извикванията към съответните операции.
 */
public class AutomatonManager {
    private Map<Integer, Automaton> automata;
    private int nextAutomatonId;

    /**
     * Конструктор, който инициализира празна колекция за автоматите и задава начално ID.
     */
    public AutomatonManager() {
        this.automata = new HashMap<>();
        this.nextAutomatonId = 1;
    }

    /**
     * Зарежда автомат от посочен файл.
     *
     * @param filename Името на файла.
     * @throws Exception При грешка в четенето на файла.
     */
    public void openFile(String filename) throws Exception {
        OpenOperation openOp = new OpenOperation();
        Automaton loadedAutomaton = openOp.execute(filename);

        if (loadedAutomaton != null) {
            int newId = nextAutomatonId++;
            loadedAutomaton.setId(newId);
            this.automata.put(newId, loadedAutomaton);
        }
    }

    /**
     * Изчиства всички автомати от паметта и нулира брояча на ID-та.
     */
    public void closeDocument() {
        this.automata.clear();
        this.nextAutomatonId = 1;
    }

    /**
     * Запазва даден автомат в бинарен файл.
     *
     * @param id Идентификатор на автомата за запазване.
     * @param filename Името на файла.
     * @throws Exception Ако автоматът не съществува или има грешка при запис.
     */
    public void saveAutomaton(int id, String filename) throws Exception {
        Automaton automatonToSave = this.automata.get(id);
        if (automatonToSave == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува в паметта.");
        }
        SaveOperation saveOp = new SaveOperation();
        saveOp.execute(automatonToSave, filename);
    }

    /**
     * Създава нов празен автомат и го записва в паметта.
     *
     * @return ID-то на новосъздадения автомат.
     */
    public int createEmptyAutomaton() {
        int newId = nextAutomatonId++;
        Automaton newAutomaton = new Automaton(newId);
        this.automata.put(newId, newAutomaton);
        return newId;
    }

    /**
     * Добавя нов връх към съществуващ автомат.
     *
     * @param autId ID на автомата.
     * @param isFinal Дали върхът да бъде финален.
     * @return ID-то на новосъздадения връх.
     */
    public int addVertexToAutomaton(int autId, boolean isFinal) {
        Automaton automaton = this.automata.get(autId);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + autId + " не съществува.");
        }

        AddVertexOperation addVertexOp = new AddVertexOperation();
        return addVertexOp.execute(automaton, isFinal);
    }

    /**
     * Добавя дъга (преход) между два върха в даден автомат.
     *
     * @param autId ID на автомата.
     * @param fromVertexId ID на изходящия връх.
     * @param symbol Символ на прехода.
     * @param toVertexId ID на целевия връх.
     */
    public void addArcToAutomaton(int autId, int fromVertexId, char symbol, int toVertexId) {
        Automaton automaton = this.automata.get(autId);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + autId + " не съществува.");
        }

        AddArcOperation addArcOp = new AddArcOperation();
        addArcOp.execute(automaton, fromVertexId, symbol, toVertexId);
    }

    /**
     * Връща списък с ID-тата на всички налични автомати.
     *
     * @return Множество от ID-та.
     */
    public Set<Integer> listAutomata() {
        return this.automata.keySet();
    }

    /**
     * Генерира текстово представяне на автомат.
     *
     * @param id ID на автомата.
     * @return Форматиран текст с върховете и дъгите.
     */
    public String printAutomaton(int id) {
        Automaton automaton = this.automata.get(id);

        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува в паметта.");
        }

        PrintOperation printOp = new PrintOperation();
        return printOp.execute(automaton);
    }

    /**
     * Проверява дали дадена дума се разпознава от автомата.
     *
     * @param id ID на автомата.
     * @param word Думата за проверка.
     * @return true, ако думата се разпознава, иначе false.
     * @throws Exception Ако автоматът не съществува.
     */
    public boolean recognizeWord(int id, String word) throws Exception {
        Automaton automaton = this.automata.get(id);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        RecognizeOperation recognizeOp = new RecognizeOperation();
        return recognizeOp.execute(automaton, word);
    }

    /**
     * Проверява дали езикът на автомата е празен (няма път до финален връх).
     *
     * @param id ID на автомата.
     * @return true, ако езикът е празен, иначе false.
     */
    public boolean isLanguageEmpty(int id) {
        Automaton automaton = this.automata.get(id);
        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        IsEmptyOperation emptyOp = new IsEmptyOperation();
        return emptyOp.execute(automaton);
    }

    /**
     * Извършва операция обединение (Union) върху два автомата.
     *
     * @param id1 ID на първия автомат.
     * @param id2 ID на втория автомат.
     * @return ID на новосъздадения автомат.
     * @throws Exception При грешка в операцията.
     */
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

    /**
     * Извършва операция конкатенация (слепване) върху два автомата.
     *
     * @param id1 ID на първия автомат.
     * @param id2 ID на втория автомат.
     * @return ID на новосъздадения автомат.
     * @throws Exception При грешка в операцията.
     */
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

    /**
     * Извършва унарна операция Звезда на Клини върху автомат.
     *
     * @param id ID на автомата.
     * @return ID на новия автомат.
     * @throws Exception При грешка в операцията.
     */
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

    /**
     * Създава автомат директно от регулярен израз.
     *
     * @param regex Регулярният израз.
     * @return ID на създадения автомат.
     * @throws Exception Ако регулярният израз е невалиден.
     */
    public int createFromRegex(String regex) throws Exception {
        CreateFromRegexOperation regexOp = new CreateFromRegexOperation();

        Automaton newAutomaton = regexOp.execute(regex);

        int newId = nextAutomatonId++;
        newAutomaton.setId(newId);

        this.automata.put(newId, newAutomaton);

        return newId;
    }

    /**
     * Детерминизира НКА и създава еквивалентен ДКА.
     *
     * @param id ID на автомата за детерминизиране.
     * @return ID на новия ДКА.
     * @throws Exception При грешка в операцията.
     */
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

    /**
     * Проверява дали езикът на даден автомат е краен.
     *
     * @param id ID на автомата.
     * @return true, ако е краен, false, ако е безкраен.
     */
    public boolean isLanguageFinite(int id) {
        Automaton automaton = this.automata.get(id);

        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        IsFiniteOperation finiteOp = new IsFiniteOperation();
        return finiteOp.execute(automaton);
    }

    /**
     * Проверява дали даден автомат е детерминиран (няма епсилон преходи и дублирани символи).
     *
     * @param id ID на автомата.
     * @return true, ако е ДКА, false, ако е НКА.
     */
    public boolean isDeterministic(int id) {
        Automaton automaton = this.automata.get(id);

        if (automaton == null) {
            throw new IllegalArgumentException("Автомат с ID " + id + " не съществува.");
        }

        IsDeterministicOperation checkOp = new IsDeterministicOperation();
        return checkOp.execute(automaton);
    }
}