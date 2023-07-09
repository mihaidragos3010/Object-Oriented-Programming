package Read;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCommand implements Read{

    private static BufferedReader reader;

    @Override
    public String[] getLine(String filePath){

        String[] line = null;
        try {
            if(reader == null  ) {
                reader = new BufferedReader(new FileReader(filePath));
            }
            line = reader.readLine().split(" ");
        }
        catch (NullPointerException e){
            reader = null;
            return null;
        }
        catch (IOException e){
            e.getStackTrace();
        }

        return line;

    }

    public void reset(){
        this.reader = null;
    }
}
