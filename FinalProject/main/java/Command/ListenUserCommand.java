package Command;

import DataBase.*;

public class ListenUserCommand implements Command{
    @Override
    public boolean checkCommand(String[] line) {  // 137 LISTEN 24013
        Database database = Database.getInstance();
        if(line.length == 3) {
            if (database.searchUser(Integer.parseInt(line[0])) && line[1].matches("LISTEN") && line[2].matches("[0-9]{5}"))
                return true;
        }
        return false;
    }

    @Override
    public void execute(String[] line) {
        Database database = Database.getInstance();
        Integer userId = Integer.parseInt(line[0]);
        Integer streamId = Integer.parseInt(line[2]);
        User user = database.getUser(userId);
        Stream stream = database.getStream(streamId);

        user.addStream(stream);
        stream.listen();
    }
}
