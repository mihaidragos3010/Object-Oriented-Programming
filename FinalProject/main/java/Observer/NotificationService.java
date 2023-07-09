package Observer;

import DataBase.*;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Observer.Event.*;

public class NotificationService {
    private static NotificationService instance;
    private final Map<Event, List<Subscriber>> subscribers;

    private NotificationService() {
        subscribers = new HashedMap();
        subscribers.put(DeleteStream,new ArrayList<>());
    }

    public void subscribe(Event eventType, Subscriber subscriber){
        subscribers.get(eventType).add(subscriber);
    }

    public void unsubscribe(Event eventType, Subscriber subscriber){
        subscribers.get(eventType).remove(subscriber);
    }

    public void notify(Event eventType, Stream stream){
        subscribers.get(eventType).forEach(subscriber -> subscriber.update(stream));
    }

    public static NotificationService getInstance(){
        if(instance == null){
            instance = new NotificationService();
        }
        return instance;
    }

    public void clear(){

        instance = null;
    }
}
