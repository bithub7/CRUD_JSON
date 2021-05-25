package repository.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Label;
import model.Post;
import repository.LabelRepository;
import repository.IOUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonLabelRepositoryImpl implements LabelRepository {

    private final String path = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\labels.json";
    private final Gson gson = new Gson();
    private final String jsonArrayName = "labels";

    @Override
    public Label save(Label label){

        if(duplicateCheck(label)){
            System.out.println("Тег с таким именет уже существует");
            return label;
        }

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        Long id = idGenerating(jsonArray);

        label.setId(id);

        StringBuilder jsonObjectStr = new StringBuilder("{\""+jsonArrayName+"\":");

        jsonObjectStr.append(jsonArray.toString());

        jsonObjectStr.delete(jsonObjectStr.length()-1, jsonObjectStr.length()+1);

        jsonObjectStr.append("," + gson.toJson(label) + "]}");

        IOUtils.writeJsonObject(jsonObjectStr.toString(), path);

        return label;
    }

    @Override
    public Label update(Label label) {

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);
        if(label.getId()>jsonArray.size()){
            System.out.println("Тега с таким id не существует");
            return label;
        }

        JsonElement element = jsonArray.get(label.getId().intValue());

        Label labelTemp = gson.fromJson(element, Label.class);

        labelTemp.setName(label.getName());

        JsonElement jsonElement = JsonParser.parseString(gson.toJson(label)).getAsJsonObject();

        jsonArray.set(label.getId().intValue(), jsonElement);

        StringBuilder jsonObjectStr = new StringBuilder("{\""+jsonArrayName+"\":" + jsonArray.toString() + "}");

        IOUtils.writeJsonObject(jsonObjectStr.toString(), path);

        return null;
    }

    @Override
    public Label getById(Long id){

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        JsonElement jsonElement =  jsonArray.get(id.intValue()-1);

        Label label = gson.fromJson(jsonElement, Label.class);

        return label;
    }

    @Override
    public List<Label> getAll(){

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        List<Label> arrayList = new ArrayList();

        for(JsonElement element : jsonArray){
            arrayList.add(gson.fromJson(element, (Type) Label.class));
        }
        return arrayList;
    }

    @Override
    public void deleteById(Long id){

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        if(jsonArray.size() < id || id < 0){
            System.out.println("Такого id не существует");
        }

        jsonArray.remove(id.intValue()-1);

        ArrayList<Label> labelList = gson.fromJson(jsonArray.toString(), new TypeToken<List<Label>>(){}.getType());

        labelList.stream().filter(label -> label.getId()>id)
                .forEach(label -> label.setId(label.getId()-1));

        jsonArray = gson.toJsonTree(labelList).getAsJsonArray();

        IOUtils.writeJsonObject("{\""+jsonArrayName+"\":"+jsonArray+"}", path);
    }

    private boolean duplicateCheck(Label label){

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        for(JsonElement element : jsonArray){

            Label labelTemp = gson.fromJson(element, Label.class);

            if(label.getName().equalsIgnoreCase(labelTemp.getName())){
                return true;
            }
        }
        return false;
    }

    private Long idGenerating(JsonArray jsonArray){

        Long id = Long.valueOf(jsonArray.size()+1);

        return id;
    }
}
