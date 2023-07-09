package Command;

import DataBase.*;

public class AddFileStreamCommand implements Command{

        @Override
        public boolean checkCommand(String[] line) {

            if(line.length == 8){
                if(line[0].matches("[1-3]") && line[1].matches("[0-9]*") &&
                        line[2].matches("[1-5]") && line[3].matches("[0-9]*") &&
                        line[4].matches("[0-9]*") && line[5].matches("[0-9]*") &&
                        line[6].matches("[0-9]*") && line[7].matches("[-().&,:#' a-zA-z0-9]*")){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void execute(String[] line) {
            Database database = Database.getInstance();
            Stream stream = new Stream(line);
            database.addStream(stream);
        }

}
