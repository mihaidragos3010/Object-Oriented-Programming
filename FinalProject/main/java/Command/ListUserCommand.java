package Command;

import DataBase.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ListUserCommand implements Command {

//    Methods
    @Override
    public boolean checkCommand(String[] line) {
        Database database = Database.getInstance();
        if(line.length == 2) {
            if (database.searchUser(Integer.parseInt(line[0])) && line[1].matches("LIST"))
                return true;
        }
        return false;
    }

    @Override
    public void execute(String[] line) {
        Database database = Database.getInstance();
        Integer userId = Integer.parseInt(line[0]);
        User user = database.getUser(userId);


        JsonArray allStreams = new JsonArray();
        for(Stream stream : user.getAllStreams()){
            Streamer streamer = database.getStreamer(stream.getStreamerId());
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("id",Integer.toString(stream.getId()));
            jsonObject.addProperty("name",stream.getName());
            jsonObject.addProperty("streamerName",streamer.getName());
            jsonObject.addProperty("noOfListenings", Long.toString(stream.noOfListenings()));
            jsonObject.addProperty("length",stream.getLength());
            jsonObject.addProperty("dateAdded",stream.getDate());

            allStreams.add(jsonObject);
        }

        System.out.println(allStreams);
    }
}
