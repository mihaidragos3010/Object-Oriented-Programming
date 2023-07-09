package Command;

import DataBase.Database;
import DataBase.Stream;

public class    AddStreamCommand implements Command{

    @Override
    public boolean checkCommand(String[] line) {

        if(line.length == 9){
            if(line[0].matches("[0-9]{4}") && line[1].matches("ADD") &&
                    line[2].matches("[1-3]") && line[3].matches("[0-9]*") &&
                    line[4].matches("[1-5]") && line[5].matches("[0-9]*") &&
                    line[6].matches("[-().&, a-zA-z]*")){
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(String[] line) {
        Database database = Database.getInstance();
        String[] newline = new String[8];
        newline[0] = line[2];
        newline[1] = line[3];
        newline[2] = line[4];
        newline[3] = "0";
        newline[4] = line[0];
        newline[5] = line[5];
        newline[6] = String.valueOf(System.currentTimeMillis() / 1000L);
        String name = new String();
        for(int i=6;i<line.length;i++) {
             name = name + line[i];
             if(i < line.length-1)
                 name = name + " ";
        }
        newline[7] = name;
        Stream stream = new Stream(newline);
        database.addStream(stream);
    }

}
