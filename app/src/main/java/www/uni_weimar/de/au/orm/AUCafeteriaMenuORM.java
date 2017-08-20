package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUCafeteriaMenu;

/**
 * Created by nazar on 20.08.17.
 */

public class AUCafeteriaMenuORM implements AUBaseORM<AUCafeteriaMenu> {

    private final Realm realm;

    public AUCafeteriaMenuORM(Realm realm){
        this.realm = realm;
    }

    @Override
    public AUCafeteriaMenu add(AUCafeteriaMenu item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealm(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUCafeteriaMenu> addAll(List<AUCafeteriaMenu> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(AUCafeteriaMenu.class);
        items = realm.copyToRealm(items);
        realm.commitTransaction();
        return items;
    }

    @Override
    public boolean deleteAll(Class<AUCafeteriaMenu> auCafeteriaMenuClass) {
        realm.delete(auCafeteriaMenuClass);
        return true;
    }

    @Override
    public void delete(String key, String name) {

    }

    @Override
    public List<AUCafeteriaMenu> findAll() {
        return realm.where(AUCafeteriaMenu.class).findAll();
    }

    @Override
    public AUCafeteriaMenu findBy(String key, String name) {
        return realm.where(AUCafeteriaMenu.class).equalTo(key, name).findFirst();
    }

    @Override
    public List<AUCafeteriaMenu> findAllBy(String key, String name) {
        return realm.where(AUCafeteriaMenu.class).equalTo(key, name).findAll();
    }
}
