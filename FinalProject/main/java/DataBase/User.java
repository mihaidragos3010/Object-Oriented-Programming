package DataBase;

import Observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class User implements Subscriber {

//    Arguments
    Integer Id;
    String name;
    ArrayList<Stream> streamList = new ArrayList<>();

//    Builders
    public User (){}
    public User(String[] line){
        this.Id = Integer.parseInt(line[0]);
        this.name = line[1];
    }

//    Methods
    public boolean searchStream(Stream stream){
        int index = streamList.indexOf(stream);
        if(index != -1)
            return true;
        return false;
    }

    public boolean searchStreamer(Streamer streamer){
        for(Stream stream : streamList){
            if(stream.getStreamerId().equals(streamer.getId()))
                return true;
        }
        return false;
    }
    public void addStream(Stream stream){
        streamList.add(stream);
    }

    public Integer getId() {
        return Id;
    }

    public ArrayList<Stream> getAllStreams(){
        return streamList;
    }


    @Override
    public void update(Stream stream) {
        streamList.remove(stream);
    }
}
