package Comands;

import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.io.*;

public class UnionOperation {

    public Automaton execute(Automaton a1, Automaton a2, int newId) throws Exception {

        Automaton copy1 = deepCopy(a1);
        Automaton copy2 = deepCopy(a2);

        Automaton result = new Automaton(newId);
        Vertex newStart = result.getStartVertex();

        Vertex newFinal = result.createVertex();
        newFinal.setFinal(true);

        newStart.addArc(Arc.EPSILON, copy1.getStartVertex());
        newStart.addArc(Arc.EPSILON, copy2.getStartVertex());

        for (Vertex v : copy1.getVertices()) {
            if (v.isFinal()) {
                v.addArc(Arc.EPSILON, newFinal);
                v.setFinal(false);
            }
        }
        for (Vertex v : copy2.getVertices()) {
            if (v.isFinal()) {
                v.addArc(Arc.EPSILON, newFinal);
                v.setFinal(false);
            }
        }

        int currentVertexId = 2;

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
