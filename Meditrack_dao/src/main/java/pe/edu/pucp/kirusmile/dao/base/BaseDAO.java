package pe.edu.pucp.kirusmile.dao.base;

public interface BaseDAO <T, ID> {
    T load(ID id);
    T save(T t);
    T update(T t);
    void remove(T t);
}
