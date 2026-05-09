import ComandLine.CommandLineInterface;

/**
 * Главният клас на приложението.
 * Служи единствено за инициализиране и стартиране на конзолния интерфейс на системата за крайни автомати.
 */
public class Main {
    /**
     * Главният метод, който се изпълнява при стартиране на програмата.
     * Създава инстанция на командния интерфейс (CommandLineInterface) и извиква
     * неговия метод start(), който стартира безкрайния цикъл за четене на команди.
     *
     * @param args Аргументи от командния ред (не се използват в текущата реализация на проекта).
     */
    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }
}