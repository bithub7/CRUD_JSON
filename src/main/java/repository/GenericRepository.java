package repository;

import java.io.IOException;
import java.util.List;

public interface GenericRepository <T,ID>{
    T save(T obj);
    T update(T obj);
    T getById(ID id);
    List<T> getAll();
    void deleteById(ID id);
}
