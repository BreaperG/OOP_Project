package Comands;

import Automaton.Automaton;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Операция за запазване на автомата в бинарен файл.
 */
public class SaveOperation {
    /**
     * Записва обекта на автомата във файл на диска.
     *
     * @param automaton Автоматът за запазване.
     * @param filename Името на файла.
     * @throws IOException При възникване на проблем с файловата система.
     */
    public void execute(Automaton automaton, String filename) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(automaton);
        }
    }
}
