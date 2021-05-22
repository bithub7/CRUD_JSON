package repository;

import com.google.gson.*;
import model.Writer;

import java.util.ArrayList;
import java.util.List;

public class JsonWriterRepositoryImpl implements WriterRepository{

    String path = System.getProperty("user.dir") + "\\src\\main\\repository\\json\\writers.json";
    Gson gson = new Gson();
    WritingReadingFile writingReadingFile = new WritingReadingFile();


    @Override
    public Writer save(Writer writer) {

        JsonArray jsonArray = writingReadingFile.readJsonArray("writers", path);

        Long id = Long.valueOf(jsonArray.size()+1);

        writer.setId(id);

        StringBuilder jsonObjectStr = new StringBuilder("{\"writers\":");

        jsonObjectStr.append(jsonArray.toString());

        jsonObjectStr.delete(jsonObjectStr.length()-1, jsonObjectStr.length()+1);

        jsonObjectStr.append("," + gson.toJson(writer) + "]}");

        writingReadingFile.writeJsonObject(jsonObjectStr.toString(), path);

        return writer;
    }

    @Override
    public Writer update(Writer writer) {

        JsonArray jsonArray = writingReadingFile.readJsonArray("writers", path);
        if(writer.getId()>jsonArray.size()){
            System.out.println("Поста с таким id не существует");
            return writer;
        }

        JsonElement element = jsonArray.get(writer.getId().intValue()-1);

        Writer writerTemp = gson.fromJson(element, Writer.class);

        writerTemp.setFirstName(writer.getFirstName());
        writerTemp.setLastName(writer.getLastName());

        JsonElement jsonElement = JsonParser.parseString(gson.toJson(writer)).getAsJsonObject();

        jsonArray.set(writer.getId().intValue()-1, jsonElement);

        StringBuilder jsonObjectStr = new StringBuilder("{\"writers\":" + jsonArray.toString() + "}");

        writingReadingFile.writeJsonObject(jsonObjectStr.toString(), path);

        return writer;
    }

    public Writer getById(Long id) {

        JsonArray jsonArray = writingReadingFile.readJsonArray("writers", path);

        JsonElement jsonElement =  jsonArray.get(id.intValue());

        Writer writer = gson.fromJson(jsonElement, Writer.class);

        return writer;
    }

    public List getAll(){

        JsonArray jsonArray = writingReadingFile.readJsonArray("writers", path);

        List<Writer> arrayList = new ArrayList<>();

        for(JsonElement element : jsonArray){
            arrayList.add(gson.fromJson(element, Writer.class));
        }

        return arrayList;
    }

    public void deleteById(Long id) {

        JsonArray jsonArray = writingReadingFile.readJsonArray("writers", path);

        if(jsonArray.size() < id || id < 0){
            System.out.println("Такого id не существует");
        }

        jsonArray.remove(id.intValue()-1);

        StringBuilder strJsonArray = new StringBuilder("{\"writers\":[");

        for(JsonElement element : jsonArray){
            Writer writer = gson.fromJson(element, Writer.class);

            if(writer.getId()>id){
                writer.setId(writer.getId()-1);
            }
            strJsonArray.append(gson.toJson(writer) + ",");
        }
        strJsonArray.delete(strJsonArray.length()-1, strJsonArray.length());
        writingReadingFile.writeJsonObject(strJsonArray.toString()+"]}", path);

    }
}
