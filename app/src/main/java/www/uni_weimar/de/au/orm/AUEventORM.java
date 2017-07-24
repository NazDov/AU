package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import www.uni_weimar.de.au.models.AUFacultyEvent;

/**
 * Created by nazar on 21.07.17.
 */

public class AUEventORM implements AUBaseORM<AUFacultyEvent> {

    private final Realm realm;

    public AUEventORM(Realm realm) {
        this.realm = realm;
    }


    @Override
    public AUFacultyEvent add(AUFacultyEvent item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealm(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUFacultyEvent> addAll(List<AUFacultyEvent> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(AUFacultyEvent.class);
        items = realm.copyToRealm(items);
        realm.commitTransaction();
        return items;
    }

    @Override
    public boolean deleteAll(Class<AUFacultyEvent> auFacultyEventClass) {
        return false;
    }

    @Override
    public void delete(String key, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AUFacultyEvent> findAll() {
        RealmResults<AUFacultyEvent> facultyEvents = realm.where(AUFacultyEvent.class).findAll();
        return facultyEvents;
    }

    @Override
    public AUFacultyEvent findBy(String key, String name) {
        return realm.where(AUFacultyEvent.class).equalTo(key, name).findFirst();
    }

    @Override
    public List<AUFacultyEvent> findAllBy(String key, String name) {
        return realm.where(AUFacultyEvent.class).equalTo(key, name).findAll();
    }
}
