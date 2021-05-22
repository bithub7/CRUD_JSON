package controller;


import com.google.gson.Gson;
import model.Writer;

public class WriterController {

    Gson gson = new Gson();

    public String createWriterJson(Writer writer){
        //создаем строку для записи в файл
        String writerJson = gson.toJson(writer);
        return writerJson;
    }

    public String updateWriterJson(Writer writer){
        //создаем строку для обновления записи в файл
        return null;
    }
}
