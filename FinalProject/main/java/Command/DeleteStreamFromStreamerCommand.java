package Command;

import DataBase.*;
import Observer.Event;
import Observer.NotificationService;

public class DeleteStreamFromStreamerCommand implements Command{

    @Override
    public boolean checkCommand(String[] line) {
        if(line.length == 3) {
            if (line[0].matches("[0-9]{4}") && line[1].matches("DELETE") && line[2].matches("[0-9]{5}"))
                return true;
        }
        return false;
    }

    @Override
    public void execute(String[] line) {
        Database database = Database.getInstance();
        Stream stream = database.getStream(Integer.parseInt(line[2]));
        Streamer streamer = database.getStreamer(Integer.parseInt(line[0]));

        if(streamer.getId().equals(stream.getStreamerId())) {
            NotificationService notificationService = NotificationService.getInstance();
            notificationService.notify(Event.DeleteStream, stream);
        }
    }

}
