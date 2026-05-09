package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.io.*;

/**
 * Операция за конкатениране (слепване) на два автомата по алгоритъма на Томпсън.
 */
public class ConcatOperation {

    /**
     * Изпълнява конкатенацията, създавайки напълно нов автомат.
     *
     * @param a1 Първият автомат.
     * @param a2 Вторият автомат.
     * @param newId ID на новосъздадения автомат.
     * @return Готовият конкатениран автомат.
     * @throws Exception Ако възникне грешка при клонирането на обектите.
     */
    public Automaton execute(Automaton a1, Automaton a2, int newId) throws Exception {
        Automaton copy1 = deepCopy(a1);
        Automaton copy2 = deepCopy(a2);

        Automaton result = new Automaton(newId);
        Vertex newStart = result.getStartVertex();

        newStart.addArc(Arc.EPSILON, copy1.getStartVertex());

        for (Vertex v : copy1.getVertices()) {
            if (v.isFinal()) {
                v.addArc(Arc.EPSILON, copy2.getStartVertex());
                v.setFinal(false);
            }
        }

        int currentVertexId = 1;

        for (Vertex v : copy1.getVertices()) {
            v.setId(currentVertexId++);
            result.getVertices().add(v);
        }
        for (Vertex v : copy2.getVertices()) {
            v.setId(currentVertexId++);
            result.getVertices().add(v);
        }

        return result;
    }

    /**
     * Прави дълбоко копие на автомат чрез сериализация в паметта, за да запази оригинала.
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