package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.io.*;

public class ConcatOperation {

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