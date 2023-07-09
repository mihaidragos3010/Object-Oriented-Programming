package DataBase;

import Command.CommandRunner;
import Observer.Event;
import Observer.NotificationService;
import Observer.Subscriber;
import Read.ReadCSV;

import java.util.ArrayList;

public class Database implements Subscriber {

//    Arguments
    private static Database instance;
    private NotificationService notificationService;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Stream> streams = new ArrayList<>();
    private ArrayList<Streamer> streamers = new ArrayList<>();


//    Builders
    private Database(){
        notificationService = NotificationService.getInstance();
        notificationService.subscribe(Event.DeleteStream,this);
    }

//    Methods

    public void load(String streamersFile, String streamsFile, String usersFile){

        CommandRunner commandRunner;

        if(users.isEmpty() && streams.isEmpty() && streamers.isEmpty()) {
            commandRunner = new CommandRunner(streamersFile, new ReadCSV());
            commandRunner.runCommands();

            commandRunner = new CommandRunner(streamsFile, new ReadCSV());
            commandRunner.runCommands();

            commandRunner = new CommandRunner(usersFile, new ReadCSV());
            commandRunner.runCommands();
        }

}
    public void clear(){
        users.clear();
        streamers.clear();
        streams.clear();
        notificationService.clear();
        instance = null;
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void addStreamer(Streamer streamer){
        streamers.add(streamer);
    }
    public void addStream(Stream stream){
        streams.add(stream);
    }
    public void addUser(User user){
        NotificationService notificationService = NotificationService.getInstance();
        notificationService.subscribe(Event.DeleteStream,user);
        users.add(user);
    }

    public Stream getStream(Integer streamId){
        for(Stream stream : streams){
            if(stream.getId().equals(streamId))
                return stream;
        }
        return null;
    }


    public Streamer getStreamer(Integer streamerId){
        for(Streamer streamer : streamers){
            if(streamer.getId().equals(streamerId))
                return streamer;
        }
        return null;
    }

    public User getUser(Integer userId){
        for(User user : users){
            if(user.getId().equals(userId))
                return user;
        }
        return null;
    }

    public ArrayList<Stream> getAllStreams(){
        return this.streams;
    }
    public ArrayList<User> getAllUsers() {
        return this.users;
    }

    public Integer getNrStreams(){
        return this.streams.size();
    }

    public boolean searchUser(Integer userId){
        for(User user : users){
            if(userId.equals(user.getId()))
                return true;
        }
        return false;
    }

    public boolean searchStreamer(Integer streamerId){
        for(Streamer streamer : streamers){
            if(streamerId.equals(streamer.getId()))
                return true;
        }
        return false;
    }

    @Override
    public void update(Stream stream) {
        streams.remove(stream);
    }
}
