package project;

import java.io.FileWriter;
import java.io.IOException;

public class WriteData {

    public static void writeData(String text, String fileName){
        FileWriter writer;
        try {
            writer = new FileWriter("src/main/project/outputFiles/" + fileName, true);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
