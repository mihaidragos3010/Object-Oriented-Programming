
import Command.*;
import DataBase.Database;
import Read.ReadCommand;

import java.util.ArrayList;
import java.util.List;

public class ProiectPOO {

    static List<Command> commands = new ArrayList<>();

    public static void main(String[] args) {

        if(args == null) {
            System.out.println("Nothing to read here");
            return;
        }

        CommandRunner.addNewCommandSystem(new ListStreamerCommand());
        CommandRunner.addNewCommandSystem(new ListUserCommand());
        CommandRunner.addNewCommandSystem(new DeleteStreamFromStreamerCommand());
        CommandRunner.addNewCommandSystem(new ListenUserCommand());
        CommandRunner.addNewCommandSystem(new RecommendSongsCommand());
        CommandRunner.addNewCommandSystem(new SurpriseRecommendCommand());
        CommandRunner.addNewCommandSystem(new AddFileStreamerCommand());
        CommandRunner.addNewCommandSystem(new AddFileStreamCommand());
        CommandRunner.addNewCommandSystem(new AddFileUserCommand());
        CommandRunner.addNewCommandSystem(new AddStreamCommand());

        String streamersFile = "src/main/resources/" + args[0];
        String streamsFile = "src/main/resources/" + args[1];
        String usersFile = "src/main/resources/" + args[2];
        Database database = Database.getInstance();
        database.load(streamersFile,streamsFile,usersFile);

        String commandFile = "src/main/resources/" + args[3];
        CommandRunner commandRunner = new CommandRunner(commandFile, new ReadCommand());
        commandRunner.runCommands();

        database.clear();

    }
}
