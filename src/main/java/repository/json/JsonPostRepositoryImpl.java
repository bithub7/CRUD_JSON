package repository.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Post;
import repository.PostRepository;
import utils.IOUtils;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class JsonPostRepositoryImpl implements PostRepository {

    private final String path = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\posts.json";
    private final Gson gson = new Gson();
    private final String jsonArrayName = "posts";


    @Override
    public Post save(Post post) {

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        Long id = idGenerating(jsonArray);

        post.setId(id);

        StringBuilder jsonObjectStr = new StringBuilder("{\""+jsonArrayName+"\":");

        jsonObjectStr.append(jsonArray.toString());

        jsonObjectStr.delete(jsonObjectStr.length()-1, jsonObjectStr.length()+1);

        jsonObjectStr.append("," + gson.toJson(post) + "]}");

        IOUtils.writeJsonObject(jsonObjectStr.toString(), path);

        return post;
    }

    @Override
    public Post update(Post post) {

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);
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

        StringBuilder jsonObjectStr = new StringBuilder("{\""+jsonArrayName+"\":" + jsonArray.toString() + "}");

        IOUtils.writeJsonObject(jsonObjectStr.toString(), path);

        return post;
    }

    @Override
    public Post getById(Long id){

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        JsonElement jsonElement =  jsonArray.get(id.intValue()-1);

        Post post = gson.fromJson(jsonElement, Post.class);

        return post;
    }

    @Override
    public List<Post> getAll(){

        JsonArray jsonArray = IOUtils.readJsonArray(jsonArrayName, path);

        List<Post> arrayList = new ArrayList<>();

        for(JsonElement element : jsonArray){
            arrayList.add(gson.fromJson(element, Post.class));
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

        ArrayList<Post> postList = gson.fromJson(jsonArray.toString(), new TypeToken<List<Post>>(){}.getType());

        postList.stream().filter(post -> post.getId()>id)
                .forEach(post -> post.setId(post.getId()-1));

        jsonArray = gson.toJsonTree(postList).getAsJsonArray();

        IOUtils.writeJsonObject("{\""+jsonArrayName+"\":"+jsonArray+"}", path);
    }

    private Long idGenerating(JsonArray jsonArray){

        Long id = Long.valueOf(jsonArray.size()+1);

        return id;
    }
}