package ComandLine;

import Automaton.Automaton;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineInterface {
    private Map<String,Command> commands=new HashMap<>();
    private AutomatonManager manager=new AutomatonManager();

    public CommandLineInterface(){
        commands.put("list",args ->manager.listAutomata());
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
            System.arraycopy(tokens,1,args,0,   tokens.length-1);
            //Търся командата в map. Ако командата я няма в map ще върне null
            Command command=commands.get(userCommand);
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
