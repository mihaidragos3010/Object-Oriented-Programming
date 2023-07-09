package Iterator;

import DataBase.Database;
import DataBase.Stream;

import java.util.ArrayList;

public class IteratorStreamerList implements Iterator{
    private Integer streamerId;
    private static int index;
    private ArrayList<Stream> streams;

    public IteratorStreamerList(Integer streamerId){
        this.streamerId = streamerId;
        Database database = Database.getInstance();
        streams = database.getAllStreams();
        index = 0;
    }

    @Override
    public boolean hasNext() {
        for(int i=index ; i < streams.size();i++){
            Stream stream = streams.get(i);
            if(streamerId.equals(stream.getStreamerId()))
                return true;
        }
        return false;
    }

    @Override
    public Stream getNext() {
        Stream stream = streams.get(index++);
        if(streamerId.equals(stream.getStreamerId()))
            return stream;
        return getNext();
    }

    @Override
    public void reset() {

    }
}
