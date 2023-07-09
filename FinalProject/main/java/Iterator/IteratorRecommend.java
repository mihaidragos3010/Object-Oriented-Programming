package Iterator;

import DataBase.*;

import java.util.ArrayList;
import java.util.Collections;

public class IteratorRecommend implements Iterator{

    private User user;
    private Integer streamType;
    private static int index;
    private ArrayList<Stream> streams;

    public IteratorRecommend(User user,Integer streamType){
        this.user = user;
        this.streamType = streamType;
        this.index = 0;
        Database database = Database.getInstance();
        streams = new ArrayList<>(database.getAllStreams());
        Collections.sort(streams,new Stream.ViewsComparator());
    }

    @Override
    public boolean hasNext() {

        Database database = Database.getInstance();
        for(int i = index; i<streams.size();i++){
            Stream stream = streams.get(i);
            Streamer streamer = database.getStreamer(stream.getStreamerId());
            if(!user.searchStream(stream) && user.searchStreamer(streamer))
                return true;
        }
        return false;
    }

    @Override
    public Stream getNext() {

        Stream stream = streams.get(index++);
        Database database = Database.getInstance();
        Streamer streamer = database.getStreamer(stream.getStreamerId());
        if(!user.searchStream(stream) && user.searchStreamer(streamer))
            return stream;
        return getNext();
    }

    @Override
    public void reset() {

    }
}
