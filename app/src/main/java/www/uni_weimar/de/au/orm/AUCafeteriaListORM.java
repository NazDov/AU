package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUCafeteria;

/**
 * Created by ndovhuy on 26.08.2017.
 */

public class AUCafeteriaListORM implements AUBaseORM<AUCafeteria> {

    private final Realm realm;

    public AUCafeteriaListORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AUCafeteria add(AUCafeteria item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUCafeteria> addAll(List<AUCafeteria> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        items = realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
        return items;
    }

    @Override
    public boolean deleteAll(Class<AUCafeteria> auCafeteriaClass) {
        return false;
    }

    @Override
    public void delete(String key, String name) {

    }

    @Override
    public List<AUCafeteria> findAll() {
        return realm.where(AUCafeteria.class).findAll();
    }

    @Override
    public AUCafeteria findBy(String key, String name) {
        return null;
    }

    @Override
    public List<AUCafeteria> findAllBy(String key, String name) {
        return realm.where(AUCafeteria.class).equalTo(key, name).findAll();
    }
}
