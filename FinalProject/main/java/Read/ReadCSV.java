package Read;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV implements Read {

    private static BufferedReader reader;

    @Override
    public String[] getLine(String filePath){

        String[] line = null;
        try {
            if(reader == null) {
                reader = new BufferedReader(new FileReader(filePath));
                line = reader.readLine().split(",");
            }
            line = reader.readLine().split("\",\"");

            for(int i=0;i<line.length;i++) {
                if (line[i].matches("\".*"))
                    line[i] = line[i].substring(1);
                if (line[i].matches(".*\""))
                    line[i] = line[i].substring(0, line[i].length()-1);
            }
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

}
