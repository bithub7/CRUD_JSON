package repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class IOUtils {

    public static void writeJsonObject(String jsonObjectStr, String path) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path)))
        {
            writer.write(String.valueOf(jsonObjectStr));
            writer.flush();

        } catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static JsonArray readJsonArray(String arrayName, String path){

        JsonArray jsonArray = new JsonArray();

        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {

            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);
            jsonArray = jsonObject.getAsJsonArray(arrayName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}