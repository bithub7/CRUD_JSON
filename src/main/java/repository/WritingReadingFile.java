package repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WritingReadingFile {

    public void writeJsonObject(String jsonObjectStr, String path) {

        try(FileWriter writer = new FileWriter(path))
        {
            writer.write(String.valueOf(jsonObjectStr));
            writer.flush();

        } catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public JsonArray readJsonArray(String arrayName, String path){

        FileReader reader = null;

        try {

            reader = new FileReader(path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);
        JsonArray jsonArray = jsonObject.getAsJsonArray(arrayName);

        return jsonArray;
    }

}
