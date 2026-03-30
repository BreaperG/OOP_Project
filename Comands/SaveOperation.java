package Comands;

import Automaton.Automaton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveOperation {
    public void execute(Automaton automaton,String filename){
        try(FileOutputStream fileOut =new FileOutputStream(filename);
            ObjectOutputStream objectOut=new ObjectOutputStream(fileOut)){
                objectOut.writeObject(automaton);
            System.out.println("Успех: Автомат с ID " + automaton.getId() + " беше успешно запазен във файл '" + filename + "'.");
        } catch (IOException e) {
            System.out.println("Грешка при опит за записване във файл: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
