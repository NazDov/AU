package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import www.uni_weimar.de.au.models.AUFacultyHeader;

/**
 * Created by ndovhuy on 10.07.2017.
 */
public class AUFacultyORM implements AUBaseORM<AUFacultyHeader> {

    private Realm realm;

    public AUFacultyORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AUFacultyHeader add(AUFacultyHeader item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealm(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUFacultyHeader> addAll(List<AUFacultyHeader> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(AUFacultyHeader.class);
        items = realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
        return items;
    }

    @Override
    public boolean deleteAll(Class<AUFacultyHeader> auFacultyHeaderClass) {
        return false;
    }

    @Override
    public void delete(String key, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AUFacultyHeader> findAll() {
        RealmResults<AUFacultyHeader> auFaculties = realm.where(AUFacultyHeader.class).findAll();
        return auFaculties;
    }

    @Override
    public AUFacultyHeader findBy(String key, String name) {
        return null;
    }

    @Override
    public List<AUFacultyHeader> findAllBy(String key, String name) {
        return realm.where(AUFacultyHeader.class).equalTo(key, name).findAll();
    }
}
