package ComandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    private Map<String, Command> commands;
    private AutomatonManager manager;
    private String currentOpenedFile;

    public CommandLineInterface() {
        this.commands = new HashMap<>();
        this.manager = new AutomatonManager();
        this.currentOpenedFile = null;


        commands.put("add", args -> {
            int newId = manager.createEmptyAutomaton();
            System.out.println("Успешно създаден нов празен автомат с ID: " + newId);
        });
        commands.put("add_vertex", args -> {
            if (args.length < 2) throw new IllegalArgumentException("Използвайте: add_vertex <aut_id> <is_final(true/false)>");

            int autId = Integer.parseInt(args[0]);
            boolean isFinal = Boolean.parseBoolean(args[1]);

            try {
                int newVertexId = manager.addVertexToAutomaton(autId, isFinal);
                System.out.println("Успешно добавен връх " + newVertexId + " (финален: " + isFinal + ") към автомат " + autId);
            } catch (Exception e) {
                System.out.println("Грешка: " + e.getMessage());
            }
        });
        commands.put("add_arc", args -> {
            if (args.length < 4) throw new IllegalArgumentException("Използвайте: add_arc <aut_id> <from_id> <symbol> <to_id>");

            int autId = Integer.parseInt(args[0]);
            int fromId = Integer.parseInt(args[1]);
            char symbol = args[2].charAt(0);
            int toId = Integer.parseInt(args[3]);

            try {
                manager.addArcToAutomaton(autId, fromId, symbol, toId);
                System.out.println("Успешно добавена дъга [" + fromId + " --(" + symbol + ")--> " + toId + "] в автомат " + autId);
            } catch (Exception e) {
                System.out.println("Грешка: " + e.getMessage());
            }
        });
        commands.put("list", args -> {
            java.util.Set<Integer> ids = manager.listAutomata();

            if (ids.isEmpty()) {
                System.out.println("В момента няма създадени или заредени автомати в паметта.");
            } else {
                System.out.println("Налични автомати (IDs): " + ids);
            }
        });
        commands.put("print", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: print <id>");

            int id = Integer.parseInt(args[0]);
            try {
                // Взимаме готовия текст от Мениджъра и го принтираме
                String result = manager.printAutomaton(id);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Грешка: " + e.getMessage());
            }
        });
        commands.put("save", args -> {
            if (args.length < 1) {
                throw new IllegalArgumentException("Недостатъчни аргументи. Използвайте: save <id>");
            }
            int id = Integer.parseInt(args[0]);

            try {
                manager.saveAutomaton(id, currentOpenedFile);
                System.out.println("Успех: Автомат " + id + " е запазен в текущия файл '" + currentOpenedFile + "'.");
            } catch (Exception e) {
                System.out.println("Грешка при запазване: " + e.getMessage());
            }
        });
        commands.put("saveas", args -> {
            if (args.length < 2) {
                throw new IllegalArgumentException("Недостатъчни аргументи. Използвайте: saveas <id> <ново_име_на_файл>");
            }
            int id = Integer.parseInt(args[0]);
            String newFilename = args[1];

            try {
                manager.saveAutomaton(id, newFilename);
                System.out.println("Успех: Автомат " + id + " е запазен като нов файл '" + newFilename + "'.");
            } catch (Exception e) {
                System.out.println("Грешка при запазване: " + e.getMessage());
            }
        });
        commands.put("open", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: open <file>");
            currentOpenedFile = args[0];
            try {
                manager.openFile(currentOpenedFile);
                System.out.println("Файлът '" + currentOpenedFile + "' е отворен успешно.");
            } catch (Exception e) {
                System.out.println("Грешка при отваряне на файла: " + e.getMessage());
                currentOpenedFile = null; // Рестартираме при грешка
            }
        });
        commands.put("close", args -> {
            if (currentOpenedFile == null) {
                System.out.println("Няма отворен файл, който да бъде затворен.");
                return;
            }
            manager.closeDocument();
            System.out.println("Успешно затворен файл '" + currentOpenedFile + "' и паметта е изчистена.");
            currentOpenedFile = null;
        });
        commands.put("exit", args -> {
            System.out.println("Излизане от програмата...");
            System.exit(0);
        });
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Системата е стартирана. Въведете 'open <име_на_файл>' за начало.");

        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine().trim();
            if (userInput.isEmpty()) {
                continue;
            }

            String[] tokens = userInput.split("\\s+");
            String userCommand = tokens[0].toLowerCase();

            String[] args = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, args, 0, tokens.length - 1);

            Command command = commands.get(userCommand);

            if (command != null) {

                if (currentOpenedFile == null && !userCommand.equals("open")
                        && !userCommand.equals("help") && !userCommand.equals("exit")) {
                    System.out.println("Моля, първо отворете файл с командата 'open <file>'.");
                    continue;
                }

                try {
                    command.execute(args);
                } catch (Exception e) {
                    System.out.println("Грешка: " + e.getMessage());
                }
            } else {
                System.out.println("Непозната команда. Напишете 'help' за списък с командите.");
            }
        }
    }
}
