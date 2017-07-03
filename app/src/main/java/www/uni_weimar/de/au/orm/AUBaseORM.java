package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by nazar on 13.06.17.
 */

public interface AUBaseORM<T extends RealmObject> {


    T add(T item);

    List<T> addAll(List<T> items);

    boolean deleteAll(Class<T> tClass);

    void delete(String key, String name);

    List<T> findAll();

    T findBy(String key, String name);

    List<T> findAllBy(String key, String name);


}
