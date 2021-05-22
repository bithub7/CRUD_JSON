import model.Label;
import model.Post;
import model.Writer;
import repository.JsonLabelRepositoryImpl;
import repository.JsonPostRepositoryImpl;
import repository.JsonWriterRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("***************************************************************************************************");
        System.out.println("Методы кдасса JsonLabelRepositoryImpl\n");
        JsonLabelRepositoryImpl labelRepo = new JsonLabelRepositoryImpl();

        System.out.println("Получние тега с id '5':");
        System.out.println(labelRepo.getById(5l)+"\n\n");


        System.out.println("Получение всех тегов:");
        System.out.println(labelRepo.getAll()+"\n\n");


        System.out.println("Создание тега:");
        System.out.println(labelRepo.save(new Label(1l, "value"))+"\n\n");

        System.out.println("Обновление тега с id '9':");
        System.out.println(new Label(9l, "value update")+ "\n\n");


        System.out.println("Удаление тега c id '9'\n\n");
        labelRepo.deleteById(9l);


        System.out.println("***************************************************************************************************");
        System.out.println("Методы класса JsonPostRepositoryImpl\n");
        JsonPostRepositoryImpl postRepo = new JsonPostRepositoryImpl();

        System.out.println("Получение поста с id '3'");
        System.out.println(postRepo.getById(3l)+"\n\n");


        System.out.println("Получение всех постов:");
        System.out.println(postRepo.getAll()+"\n\n");


        System.out.println("Создание поста:");

        List<Label> labelList = new ArrayList<Label>();
        labelList.add(new Label(1l, "news"));
        labelList.add(new Label(2l, "sport"));
        System.out.println(postRepo.save(new Post(1l, "text9 text9 text9", null, null, labelList))+"\n\n");

        System.out.println("Обновление поста c id '7':");
        System.out.println(postRepo.update(new Post(7l, "text9update text9update text9update", null, null, labelList))+"\n\n");


        System.out.println("Удаление поста c id '7':"+"\n\n");
        postRepo.deleteById(7l);


        System.out.println("***************************************************************************************************");
        System.out.println("Методы класса JsonWriterRepositoryImpl\n");
        JsonWriterRepositoryImpl writerRepo = new JsonWriterRepositoryImpl();


        System.out.println("Получение автора с id '2'");
        System.out.println(writerRepo.getById(2l)+"\n\n");


        System.out.println("Получение всех авторов:");
        System.out.println(writerRepo.getAll()+"\n\n");

        System.out.println("Создание нового автора:");
        System.out.println(writerRepo.save(new Writer(1l, "Alexey", "Svotin", null))+"\n\n");


        System.out.println("Обновление автора с id '8':");
        System.out.println(writerRepo.update(new Writer(8l, "AlexeyNew", "SvotinNew", null))+"\n\n");


        System.out.println("Удаление автора с id '8':");
        writerRepo.deleteById(8l);
    }
}
