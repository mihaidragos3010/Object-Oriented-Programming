package Command;

import DataBase.*;

public class AddFileStreamerCommand implements Command{

    //    Builders

    public AddFileStreamerCommand(){}

    //    Methods
    @Override
    public boolean checkCommand(String[] line) {
        if(line.length == 3) {
            if (line[0].matches("[1-3]") && line[1].matches("[0-9]*") && line[2].matches("[-.'():,#& a-zA-Z0-9]*")){
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(String[] line) {
        Database database = Database.getInstance();
        Streamer streamer = new Streamer(line);
        database.addStreamer(streamer);
    }

}
