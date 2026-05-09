package Comands;

import Automaton.Automaton;
import java.io.*;

/**
 * Операция за зареждане на автомат от бинарен файл.
 */
public class OpenOperation {

    /**
     * Отваря посочения файл и извлича обекта на автомата.
     *
     * @param filename Името на файла.
     * @return Зареденият автомат или null, ако файлът е нов/празен.
     * @throws IOException При грешка в I/O потоците.
     * @throws ClassNotFoundException Ако десериализираният клас не съвпада с Automaton.
     */
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
