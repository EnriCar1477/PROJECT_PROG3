package pe.edu.pucp.kirusmile.dao.base;

import java.util.List;

public interface BaseDAO<T> {
    int save(T objeto);
    int update(T objeto);
    int delete(int id);
    T load(int id);
    List<T> listALL();
}
