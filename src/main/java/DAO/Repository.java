package DAO;

public interface Repository<T> {
     T find(long id);
     T save(T t);
     void update(T t);
     void delete(long id);
}
