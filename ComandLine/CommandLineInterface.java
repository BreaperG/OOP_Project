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
                currentOpenedFile = null;
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
        commands.put("recognize", args -> {
            if (args.length < 2) throw new IllegalArgumentException("Използвайте: recognize <id> <дума>");

            int id = Integer.parseInt(args[0]);
            String word = args[1];

            try {
                boolean isAccepted = manager.recognizeWord(id, word);
                if (isAccepted) {
                    System.out.println("Думата '" + word + "' се разпознава от автомат " + id);
                } else {
                    System.out.println("Думата '" + word + "' не се разпознава от автомат " + id);
                }
            } catch (Exception e) {
                System.out.println("Грешка: " + e.getMessage());
            }
        });
        commands.put("empty", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: empty <id>");

            int id = Integer.parseInt(args[0]);
            try {
                boolean isEmpty = manager.isLanguageEmpty(id);
                if (isEmpty) {
                    System.out.println("Езикът на автомат " + id + " е празен (няма път до финален връх).");
                } else {
                    System.out.println("Езикът на автомат " + id + " не е празен (съществува поне една разпознаваема дума).");
                }
            } catch (Exception e) {
                System.out.println("Грешка: " + e.getMessage());
            }
        });
        commands.put("union", args -> {
            if (args.length < 2) throw new IllegalArgumentException("Използвайте: union <id1> <id2>");

            int id1 = Integer.parseInt(args[0]);
            int id2 = Integer.parseInt(args[1]);

            try {
                int newId = manager.unionAutomata(id1, id2);
                System.out.println("Успешно обединение! Новият автомат е с ID: " + newId);
            } catch (Exception e) {
                System.out.println("Грешка при обединение: " + e.getMessage());
            }
        });
        commands.put("concat", args -> {
            if (args.length < 2) throw new IllegalArgumentException("Използвайте: concat <id1> <id2>");

            int id1 = Integer.parseInt(args[0]);
            int id2 = Integer.parseInt(args[1]);

            try {
                int newId = manager.concatAutomata(id1, id2);
                System.out.println("Успешна конкатенация! Новият автомат е с ID: " + newId);
            } catch (Exception e) {
                System.out.println("Грешка при конкатенация: " + e.getMessage());
            }
        });
        commands.put("un", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: un <id>");

            int id = Integer.parseInt(args[0]);

            try {
                int newId = manager.positiveClosure(id);
                System.out.println("Успешна унарна операция! Новият автомат (Звезда на Клини) е с ID: " + newId);
            } catch (Exception e) {
                System.out.println("Грешка при унарната операция: " + e.getMessage());
            }
        });
        commands.put("reg", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: reg <регулярен_израз>");

            String regex = args[0];
            try {
                int newId = manager.createFromRegex(regex);
                System.out.println("Успех! Създаден е автомат с ID " + newId + " от израза: " + regex);
            } catch (Exception e) {
                System.out.println("Грешка при обработка на регулярния израз: Невалиден синтаксис.");
            }
        });
        commands.put("determ", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: determ <id>");

            int id = Integer.parseInt(args[0]);

            try {
                int newId = manager.determinizeAutomaton(id);
                System.out.println("Успешна детерминизация! Новият ДКА е с ID: " + newId);
            } catch (Exception e) {
                System.out.println("Грешка при детерминизиране: " + e.getMessage());
            }
        });
        commands.put("deterministic", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: deterministic <id>");

            int id = Integer.parseInt(args[0]);
            try {
                boolean isDet = manager.isDeterministic(id);
                if (isDet) {
                    System.out.println("Автомат " + id + " e детерминиран (ДКА).");
                } else {
                    System.out.println("Автомат " + id + " е недетерминиран (НКА).");
                }
            } catch (Exception e) {
                System.out.println("Грешка при проверка: " + e.getMessage());
            }
        });
        commands.put("finite", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: finite <id>");

            int id = Integer.parseInt(args[0]);

            try {
                boolean isFinite = manager.isLanguageFinite(id);
                if (isFinite) {
                    System.out.println("Езикът на автомат " + id + " е краен.");
                } else {
                    System.out.println("Езикът на автомат " + id + " е безкраен (съдържа цикъл).");
                }
            } catch (Exception e) {
                System.out.println("Грешка при проверка за крайност: " + e.getMessage());
            }
        });
        commands.put("help", args -> {
            System.out.println("\n=== НАЛИЧНИ КОМАНДИ ===");

            System.out.println("\n-- Работа с файлове --");
            System.out.println("  open <file>            : Отваря или създава файл и зарежда автоматите.");
            System.out.println("  close                  : Затваря файла и изчиства паметта.");
            System.out.println("  save <id>              : Запазва автомат в текущо отворения файл.");
            System.out.println("  saveas <id> <file>     : Запазва автомат в посочен нов файл.");

            System.out.println("\n-- Създаване и Визуализация --");
            System.out.println("  add                    : Създава нов празен автомат.");
            System.out.println("  add_vertex <id> <fin>  : Добавя връх към автомат. <fin> e true(финален)/false.");
            System.out.println("  add_arc <id> <f> <s> <t>: Добавя дъга: автомат <id>, от връх <f>, символ <s>, до връх <t>.");
            System.out.println("  list                   : Показва ID-тата на всички автомати в паметта.");
            System.out.println("  print <id>             : Извежда върховете и преходите на даден автомат.");

            System.out.println("\n-- Операции по Томпсън и Регулярни изрази --");
            System.out.println("  union <id1> <id2>      : Обединява два автомата.");
            System.out.println("  concat <id1> <id2>     : Конкатенира (слепва) два автомата.");
            System.out.println("  un <id>                : Звезда на Клини (положителна обвивка) на автомат.");
            System.out.println("  reg <regex>            : Създава НКА директно от регулярен израз (напр. (a|b)*c ).");

            System.out.println("\n-- Алгоритми и Проверки --");
            System.out.println("  recognize <id> <word>  : Проверява дали дадена дума се разпознава.");
            System.out.println("  empty <id>             : Проверява дали езикът на автомата е празен.");
            System.out.println("  deterministic <id>     : Проверява дали автоматът е детерминиран (ДКА).");
            System.out.println("  determ <id>            : Детерминизира НКА и създава нов ДКА.");
            System.out.println("  finite <id>            : Проверява дали езикът на автомата е краен (няма цикъл).");

            System.out.println("\n  exit                   : Изход от програмата.");
            System.out.println("=======================\n");
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
