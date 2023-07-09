package Command;

import DataBase.Database;
import Read.Read;
import java.util.ArrayList;
import java.util.List;

public class CommandRunner {

//    Arguments
    private final String commandFile;
    private Read reader;
    private static List<Command> commandsSystem = new ArrayList<>();;


//    Builders
    public CommandRunner(String commandFile, Read reader){
        this.commandFile = commandFile;
        this.reader = reader;
    }

    public CommandRunner(String commandFile, List<Command> commandsSystemList){
        this.commandFile = commandFile;
        this.commandsSystem = commandsSystemList;
    }



//    Methods
    public void runCommands(){

        String[] line = reader.getLine(commandFile);
        while(line != null) {
            for (Command command : commandsSystem) {
                if (command.checkCommand(line)) {
                    command.execute(line);
                    break;
                }
            }
            line = reader.getLine(commandFile);
        }

    }

    public static void addNewCommandSystem(Command command){
        commandsSystem.add(command);
    }


}
