package Comands;
import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.io.*;

/**
 * Операция за прилагане на Звезда на Клини върху автомат по Томпсън.
 */
public class PositiveClosureOperation {

    /**
     * Изпълнява унарната операция, позволявайки думата да се повтаря безброй пъти.
     *
     * @param a Оригиналният автомат.
     * @param newId ID на новия автомат.
     * @return Създаденият автомат със Звезда на Клини.
     * @throws Exception Ако възникне грешка при копирането на автомата.
     */
    public Automaton execute(Automaton a, int newId) throws Exception {
        Automaton copy = deepCopy(a);
        Automaton result = new Automaton(newId);
        Vertex newStart = result.getStartVertex();
        Vertex newFinal = result.createVertex();
        newFinal.setFinal(true);

        newStart.addArc(Arc.EPSILON, copy.getStartVertex());

        newStart.addArc(Arc.EPSILON, newFinal);

        for (Vertex v : copy.getVertices()) {
            if (v.isFinal()) {
                v.addArc(Arc.EPSILON, copy.getStartVertex());
                v.addArc(Arc.EPSILON, newFinal);
                v.setFinal(false);
            }
        }

        int currentVertexId = 2;

        for (Vertex v : copy.getVertices()) {
            v.setId(currentVertexId++);
            result.getVertices().add(v);
        }

        return result;
    }

    /**
     * Прави дълбоко копие на автомата чрез сериализация в паметта.
     *
     * @param original Оригиналният автомат.
     * @return Клонираният автомат.
     * @throws Exception При I/O грешка по време на сериализацията.
     */
    private Automaton deepCopy(Automaton original) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Automaton) ois.readObject();
    }
}