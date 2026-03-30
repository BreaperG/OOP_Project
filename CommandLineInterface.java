package ComandLine;

import Automaton.Automaton;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    private Map<String,Command> commands;
    private AutomatonManager manager;
    private String currentOpenedFile;

    public CommandLineInterface(){
        this.commands = new HashMap<>();
        this.manager = new AutomatonManager();
        this.currentOpenedFile = null;

        commands.put("list",args ->manager.listAutomata());
        commands.put("save",args ->{
            if(args.length<2){
                throw new IllegalArgumentException("Недостатъчни аргументи. Използвайте: save <id> <име_на_файл>");
            }
            int id=Integer.parseInt(args[0]);
            String filename=args[1];

            manager.saveAutomaton(id,filename);
        });
        commands.put("open", args -> {
            if (args.length < 1) throw new IllegalArgumentException("Използвайте: open <file>");
            currentOpenedFile = args[0]; // Запазваме името на отворения файл
            System.out.println("Отваряне на файл: " + currentOpenedFile);
            manager.openFile(currentOpenedFile);
        });
        commands.put("close", args -> {
            if (currentOpenedFile == null) {
                System.out.println("Няма отворен файл, който да бъде затворен.");
                return;
            }
            manager.closeDocument();
            System.out.println("Successfully closed " + currentOpenedFile); // [cite: 95]
            currentOpenedFile = null; // Това "заключва" останалите команди
        });
        commands.put("exit",args->{
            System.out.println("Излизане от програмата...");
            System.exit(0);
        });
    }

    public void start(){
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println(">");
            //Използвам трим за да махна празните места в началото и края, nextLine приема всичко написано от потребителя докато не натисне enter
            String userInput=scanner.nextLine().trim();
            if(userInput.isEmpty()){
                continue;
            }
            //Създавам масив в който се намира командата и параметрите. С split("//s+") разделям входните данни навсякъде където има един или повече space
            String[] tokens=userInput.split("//s+");
            String userCommand=tokens[0].toLowerCase();

            String[] args=new String[tokens.length-1];
            //Използвам arraycopy за да копирам масива tokens започвайки от втората позиция в масива args започвайки от първата позиция
            System.arraycopy(tokens,1,args,0,tokens.length-1);
            //Търся командата в map. Ако командата я няма в map ще върне null
            Command command=commands.get(userCommand);

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
            }

            if(command!=null){
                try{
                    command.execute(args);
                } catch (Exception e) {
                    System.out.println("Грешка: " + e.getMessage());
                }
            }
            else{
                System.out.println("Непозната команда. Напишете 'help' за списък с командите.");
            }
        }
    }

}
