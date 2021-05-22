package repository;

import java.io.IOException;

public interface GenericRepository <T,ID>{
    T save(T obj);
    T update(T obj);
}
