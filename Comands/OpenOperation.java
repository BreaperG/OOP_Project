package Comands;

import Automaton.Automaton;

import java.io.*;

public class OpenOperation {
    public Automaton execute(String filename) {
        File file = new File(filename);


        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Файлът не съществуваше. Създаден е нов празен файл: " + filename);
                }
            } catch (IOException e) {
                System.out.println("Грешка при създаване на нов файл: " + e.getMessage());
            }
            return null;
        }

        if (file.length() == 0) {
            System.out.println("Файлът е празен.");
            return null;
        }

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {


            Automaton loadedAutomaton = (Automaton) objectIn.readObject();
            return loadedAutomaton;

        } catch (FileNotFoundException e) {
            System.out.println("Грешка: Файлът не беше намерен.");
        } catch (IOException e) {
            System.out.println("Грешка при четене. Възможно е файлът да е повреден: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Грешка: Структурата на данните не беше разпозната.");
        }

        return null;
    }
}
