package Command;

import DataBase.*;
import Iterator.IteratorSurprise;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SurpriseRecommendCommand implements Command{
    @Override
    public boolean checkCommand(String[] line) {
        Database database = Database.getInstance();
        if(line.length == 3){
            if(database.searchUser(Integer.parseInt(line[0])) && line[1].matches("SURPRISE") &&
                    line[2].matches("SONG|PODCAST|AUDIOBOOK")){
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(String[] line) {
        Database database = Database.getInstance();
        Integer userId = Integer.parseInt(line[0]);
        Integer streamType = selectStreamType(line[2]);
        User user = database.getUser(userId);
        IteratorSurprise iterator = new IteratorSurprise(user,streamType);

        JsonArray allStreams = new JsonArray();
        Integer numberStreamSelected = 0;
        while(iterator.hasNext() && numberStreamSelected < 3){
            Stream stream = iterator.getNext();
            Streamer streamer = database.getStreamer(stream.getStreamerId());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id",Integer.toString(stream.getId()));
            jsonObject.addProperty("name",stream.getName());
            jsonObject.addProperty("streamerName",streamer.getName());
            jsonObject.addProperty("noOfListenings", Long.toString(stream.noOfListenings()));
            jsonObject.addProperty("length",stream.getLength());
            jsonObject.addProperty("dateAdded",stream.getDate());

            allStreams.add(jsonObject);
            numberStreamSelected++;
        }

        System.out.println(allStreams);
    }

    private Integer selectStreamType(String streamType){
        if(streamType.equals("SONG"))
            return 1;
        if(streamType.equals("PODCAST"))
            return 2;
        if(streamType.equals("AUDIOBOOK"))
            return 3;
        return null;
    }
}
