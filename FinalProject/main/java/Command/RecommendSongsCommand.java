package Command;

import DataBase.*;
import Iterator.IteratorRecommend;
import Iterator.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RecommendSongsCommand implements Command{
    @Override
    public boolean checkCommand(String[] line) {
        Database database = Database.getInstance();
        if(line.length == 3){
            if(database.searchUser(Integer.parseInt(line[0])) && line[1].matches("RECOMMEND") &&
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
        User user = database.getUser(userId);
        Integer streamType = selectStreamType(line[2]);
        IteratorRecommend iterator = new IteratorRecommend(user,streamType);

        JsonArray allStreams = new JsonArray();
        Integer numberStreamSelected = 0;
        while(iterator.hasNext() && numberStreamSelected < 5){
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
