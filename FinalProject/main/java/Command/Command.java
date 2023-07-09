package Command;

public interface Command {

//    Methods
    boolean checkCommand(String[] line);

    void execute(String[] line);

}
