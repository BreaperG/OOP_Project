package Comands;

import Automaton.Automaton;
import java.io.*;

public class OpenOperation {

    public Automaton execute(String filename) throws IOException, ClassNotFoundException {
        File file = new File(filename);


        if (!file.exists()) {
            file.createNewFile();
            return null;
        }

        if (file.length() == 0) {
            return null;
        }

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Automaton) objectIn.readObject();
        }
    }
}
