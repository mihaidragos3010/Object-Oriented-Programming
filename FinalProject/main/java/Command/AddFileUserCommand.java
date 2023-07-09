package Command;

import DataBase.*;

public class AddFileUserCommand implements Command {
    @Override
    public boolean checkCommand(String[] line) {
        if(line.length == 3){
            if(line[0].matches("[0-9]*") && line[1].matches("[.'():, a-zA-Z0-9]*")){
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(String[] line) {
        Database database = Database.getInstance();
        User user = new User(line);
        database.addUser(user);
        String[] listOfStreams = line[2].split(" ");
        for(int i=0;i<listOfStreams.length;i++){
            Integer streamId = Integer.parseInt(listOfStreams[i]);
            Stream stream = database.getStream(streamId);
            user.addStream(stream);
        }
    }
}
