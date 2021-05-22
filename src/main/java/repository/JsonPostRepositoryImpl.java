package repository;

import com.google.gson.*;
import model.Post;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class JsonPostRepositoryImpl implements PostRepository {

    String path = System.getProperty("user.dir") + "\\src\\main\\repository\\json\\posts.json";
    Gson gson = new Gson();
    WritingReadingFile writingReadingFile = new WritingReadingFile();

    @Override
    public Post save(Post post) {

        JsonArray jsonArray = writingReadingFile.readJsonArray("posts", path);

        Long id = Long.valueOf(jsonArray.size()+1);
        post.setId(id);

        StringBuilder jsonObjectStr = new StringBuilder("{\"posts\":");

        jsonObjectStr.append(jsonArray.toString());

        jsonObjectStr.delete(jsonObjectStr.length()-1, jsonObjectStr.length()+1);

        jsonObjectStr.append("," + gson.toJson(post) + "]}");

        writingReadingFile.writeJsonObject(jsonObjectStr.toString(), path);

        return post;
    }

    @Override
    public Post update(Post post) {

        JsonArray jsonArray = writingReadingFile.readJsonArray("posts", path);
        if(post.getId()>jsonArray.size()){
            System.out.println("Поста с таким id не существует");
            return post;
        }

        JsonElement element = jsonArray.get(post.getId().intValue()-1);

        Post postTemp = gson.fromJson(element, Post.class);

        postTemp.setContent(post.getContent());
        postTemp.setLabels(post.getLabels());
        post.setUpdated(new Date().toString());

        JsonElement jsonElement = JsonParser.parseString(gson.toJson(post)).getAsJsonObject();

        jsonArray.set(post.getId().intValue(), jsonElement);

        StringBuilder jsonObjectStr = new StringBuilder("{\"posts\":" + jsonArray.toString() + "}");

        writingReadingFile.writeJsonObject(jsonObjectStr.toString(), path);

        return post;
    }

    public Post getById(Long id){

        JsonArray jsonArray = writingReadingFile.readJsonArray("posts", path);

        JsonElement jsonElement =  jsonArray.get(id.intValue()-1);

        Post post = gson.fromJson(jsonElement, Post.class);

        return post;
    }

    public List<Post> getAll(){

        JsonArray jsonArray = writingReadingFile.readJsonArray("posts", path);

        List<Post> arrayList = new ArrayList<>();

        for(JsonElement element : jsonArray){
            arrayList.add(gson.fromJson(element, Post.class));
        }

        return arrayList;
    }

    public void deleteById(Long id){

        JsonArray jsonArray = writingReadingFile.readJsonArray("posts", path);

        if(jsonArray.size() < id || id < 0){
            System.out.println("Такого id не существует");
        }

        jsonArray.remove(id.intValue()-1);

        StringBuilder strJsonArray = new StringBuilder("{\"posts\":[");

        for(JsonElement element : jsonArray){
            Post post = gson.fromJson(element, Post.class);

            if(post.getId()>id){
                post.setId(post.getId()-1);
            }
            strJsonArray.append(gson.toJson(post) + ",");
        }
        strJsonArray.delete(strJsonArray.length()-1, strJsonArray.length());
        writingReadingFile.writeJsonObject(strJsonArray.toString()+"]}", path);
    }
}