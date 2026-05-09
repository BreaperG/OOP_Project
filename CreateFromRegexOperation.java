package Comands;
import Automaton.Automaton;
import Automaton.Vertex;

import java.util.Stack;

/**
 * Операция за конструиране на автомат директно от регулярен израз.
 * Използва парсинг до обратен полски запис и извиква базовите операции на Томпсън.
 */
public class CreateFromRegexOperation {

    /**
     * Изгражда НКА въз основа на подадения регулярен израз.
     *
     * @param regex Регулярният израз като низ.
     * @return Готовият автомат, представляващ израза.
     * @throws Exception Ако изразът е синтактично невалиден.
     */
    public Automaton execute(String regex) throws Exception {
        String withConcat = insertExplicitConcat(regex);
        String postfix = toPostfix(withConcat);
        Stack<Automaton> stack = new Stack<>();

        UnionOperation unionOp = new UnionOperation();
        ConcatOperation concatOp = new ConcatOperation();
        PositiveClosureOperation starOp = new PositiveClosureOperation();

        for (char c : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(c) || c == 'ε') {
                stack.push(createBasicAutomaton(c));
            } else if (c == '*') {
                Automaton a = stack.pop();
                stack.push(starOp.execute(a, 0));
            } else if (c == '.') {
                Automaton right = stack.pop();
                Automaton left = stack.pop();
                stack.push(concatOp.execute(left, right, 0));
            } else if (c == '|') {
                Automaton right = stack.pop();
                Automaton left = stack.pop();
                stack.push(unionOp.execute(left, right, 0));
            }
        }

        return stack.pop();
    }

    /**
     * Създава базов автомат само с една дъга (за единична буква).
     *
     * @param symbol Буквата на прехода.
     * @return Базов автомат.
     */
    private Automaton createBasicAutomaton(char symbol) {
        Automaton a = new Automaton(0);
        Vertex start = a.getStartVertex();
        Vertex end = a.createVertex();
        end.setFinal(true);
        start.addArc(symbol, end);
        return a;
    }

    /**
     * Добавя експлицитен символ за конкатенация (точка) между съседните символи.
     *
     * @param regex Оригиналният израз.
     * @return Изразът с добавени точки.
     */
    private String insertExplicitConcat(String regex) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < regex.length(); i++) {
            char c1 = regex.charAt(i);
            res.append(c1);
            if (i + 1 < regex.length()) {
                char c2 = regex.charAt(i + 1);
                boolean c1IsCharOrStarOrClose = Character.isLetterOrDigit(c1) || c1 == '*' || c1 == ')' || c1 == 'ε';
                boolean c2IsCharOrOpen = Character.isLetterOrDigit(c2) || c2 == '(' || c2 == 'ε';
                if (c1IsCharOrStarOrClose && c2IsCharOrOpen) {
                    res.append('.');
                }
            }
        }
        return res.toString();
    }

    /**
     * Преобразува инфиксен израз в постфиксен запис.
     *
     * @param infix Инфиксен израз.
     * @return Постфиксен израз.
     */
    private String toPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : infix.toCharArray()) {
            if (Character.isLetterOrDigit(c) || c == 'ε') {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

    /**
     * Определя приоритета на операторите.
     *
     * @param c Оператор.
     * @return Приоритетът на оператора.
     */
    private int precedence(char c) {
        if (c == '*') return 3;
        if (c == '.') return 2;
        if (c == '|') return 1;
        return 0;
    }
}
