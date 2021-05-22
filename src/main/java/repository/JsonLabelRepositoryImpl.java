package repository;

import com.google.gson.*;
import model.Label;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonLabelRepositoryImpl implements LabelRepository{

    String path = System.getProperty("user.dir") + "\\src\\main\\repository\\json\\labels.json";
    Gson gson = new Gson();
    WritingReadingFile writingReadingFile = new WritingReadingFile();

    @Override
    public Label save(Label label){

        if(duplicateCheck(label)){
            System.out.println("Тег с таким именет уже существует");
            return label;
        }

        JsonArray jsonArray = writingReadingFile.readJsonArray("labels", path);

        int id =  jsonArray.size();

        label.setId( Long.valueOf(id+1));

        StringBuilder jsonObjectStr = new StringBuilder("{\"labels\":");

        jsonObjectStr.append(jsonArray.toString());

        jsonObjectStr.delete(jsonObjectStr.length()-1, jsonObjectStr.length()+1);

        jsonObjectStr.append("," + gson.toJson(label) + "]}");

        writingReadingFile.writeJsonObject(jsonObjectStr.toString(), path);

        return label;
    }

    @Override
    public Label update(Label label) {

        JsonArray jsonArray = writingReadingFile.readJsonArray("labels", path);
        if(label.getId()>jsonArray.size()){
            System.out.println("Тега с таким id не существует");
            return label;
        }

        JsonElement element = jsonArray.get(label.getId().intValue());

        Label labelTemp = gson.fromJson(element, Label.class);

        labelTemp.setName(label.getName());

        JsonElement jsonElement = JsonParser.parseString(gson.toJson(label)).getAsJsonObject();

        jsonArray.set(label.getId().intValue(), jsonElement);

        StringBuilder jsonObjectStr = new StringBuilder("{\"labels\":" + jsonArray.toString() + "}");

        writingReadingFile.writeJsonObject(jsonObjectStr.toString(), path);

        return null;
    }

    public Label getById(Long id){

        JsonArray jsonArray = writingReadingFile.readJsonArray("labels", path);

        JsonElement jsonElement =  jsonArray.get(id.intValue()-1);

        Label label = gson.fromJson(jsonElement, Label.class);

        return label;
    }

    public List<Label> getAll(){

        JsonArray jsonArray = writingReadingFile.readJsonArray("labels", path);

        List<Label> arrayList = new ArrayList();

        for(JsonElement element : jsonArray){
            arrayList.add(gson.fromJson(element, (Type) Label.class));
        }
        return arrayList;
    }

    public void deleteById(Long id){

        JsonArray jsonArray = writingReadingFile.readJsonArray("labels", path);

        if(jsonArray.size() < id || id < 0){
            System.out.println("Тега с таким id не существует");
        }

        jsonArray.remove(id.intValue()-1);

        StringBuilder strJsonArray = new StringBuilder("{\"labels\":[");

        for(JsonElement element : jsonArray){
            Label label = gson.fromJson(element, Label.class);

            if(label.getId()>id){
                label.setId(label.getId()-1);
            }
            strJsonArray.append(gson.toJson(label) + ",");
        }
        strJsonArray.delete(strJsonArray.length()-1, strJsonArray.length());
        writingReadingFile.writeJsonObject(strJsonArray.toString()+"]}",path);
    }

    public boolean duplicateCheck(Label label){

        JsonArray jsonArray = writingReadingFile.readJsonArray("labels", path);

        for(JsonElement element : jsonArray){

            Label labelTemp = gson.fromJson(element, Label.class);

            if(label.getName().equalsIgnoreCase(labelTemp.getName())){
                return true;
            }
        }
        return false;
    }
}
