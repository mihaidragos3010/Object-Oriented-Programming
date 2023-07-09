package DataBase;

public class Streamer {

//    Arguments
    Integer type;
    Integer Id;
    String name;
//    Builders
    public Streamer(String[] line){
        this.type = Integer.parseInt(line[0]);
        this.Id = Integer.parseInt(line[1]);
        this.name = line[2];
    }
//    Methods

    public Integer getId() {
        return Id;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
