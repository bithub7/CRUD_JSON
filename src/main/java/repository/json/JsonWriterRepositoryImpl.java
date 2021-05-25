package repository.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Writer;
import repository.WriterRepository;
import utils.IOUtils;

import java.util.ArrayList;
import java.util.List;

public class JsonWriterRepositoryImpl implements WriterRepository {

    private final String path = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\writers.json";
    private final Gson gson = new Gson();
    private final String jsonArrayName = "writers";

    @Override
    public Writer save(Writer writer) {

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        Long id = idGenerating(jsonArray);

        writer.setId(id);

        StringBuilder jsonObjectStr = new StringBuilder("{\""+jsonArrayName+"\":");

        jsonObjectStr.append(jsonArray.toString());

        jsonObjectStr.delete(jsonObjectStr.length()-1, jsonObjectStr.length()+1);

        jsonObjectStr.append("," + gson.toJson(writer) + "]}");

        IOUtils.writeJsonObject(jsonObjectStr.toString(), path);

        return writer;
    }

    @Override
    public Writer update(Writer writer) {

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

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

        StringBuilder jsonObjectStr = new StringBuilder("{\""+jsonArrayName+"\":" + jsonArray.toString() + "}");

        IOUtils.writeJsonObject(jsonObjectStr.toString(), path);

        return writer;
    }

    @Override
    public Writer getById(Long id) {

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        JsonElement jsonElement =  jsonArray.get(id.intValue());

        Writer writer = gson.fromJson(jsonElement, Writer.class);

        return writer;
    }

    @Override
    public List getAll(){

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        List<Writer> arrayList = new ArrayList<>();

        for(JsonElement element : jsonArray){
            arrayList.add(gson.fromJson(element, Writer.class));
        }

        return arrayList;
    }

    @Override
    public void deleteById(Long id) {

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        if(jsonArray.size() < id || id < 0){
            System.out.println("Такого id не существует");
        }

        jsonArray.remove(id.intValue()-1);

        ArrayList<Writer> writerList = gson.fromJson(jsonArray.toString(), new TypeToken<List<Writer>>(){}.getType());

        writerList.stream().filter(writer -> writer.getId()>id)
                .forEach(writer -> writer.setId(writer.getId()-1));

        jsonArray = gson.toJsonTree(writerList).getAsJsonArray();

        IOUtils.writeJsonObject("{\""+jsonArrayName+"\":"+jsonArray+"}", path);
    }

    private Long idGenerating(JsonArray jsonArray){

        Long id = Long.valueOf(jsonArray.size()+1);

        return id;
    }
}
