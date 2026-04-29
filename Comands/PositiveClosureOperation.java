package Comands;
import Automaton.Automaton;
import Automaton.Arc;
import Automaton.Vertex;

import java.io.*;

public class PositiveClosureOperation {

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