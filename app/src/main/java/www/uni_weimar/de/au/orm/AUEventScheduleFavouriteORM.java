package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFavouriteEventSchedule;

/**
 * Created by user on 22.09.17.
 */

public class AUEventScheduleFavouriteORM implements AUBaseORM<AUFavouriteEventSchedule> {

    private static final String EVENT_SCHEDULE_ID = "eventScheduleId";
    private final Realm realm;

    public AUEventScheduleFavouriteORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AUFavouriteEventSchedule add(AUFavouriteEventSchedule item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUFavouriteEventSchedule> addAll(List<AUFavouriteEventSchedule> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        items = realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
        return items;
    }

    @Override
    public boolean deleteAll(Class<AUFavouriteEventSchedule> auFavouriteEventScheduleClass) {
        return false;
    }

    public void deleteByID(String id) {
        delete(EVENT_SCHEDULE_ID, id);
    }

    @Override
    public void delete(String key, String name) {
        realm.executeTransaction(realm1 -> {
            AUFavouriteEventSchedule favouriteEventSchedule = realm1.where(AUFavouriteEventSchedule.class).equalTo(key, name).findFirst();
            favouriteEventSchedule.deleteFromRealm();
        });
    }

    public boolean hasItem(String key, String name) {
        AUFavouriteEventSchedule favouriteEventSchedule = realm.where(AUFavouriteEventSchedule.class).equalTo(key, name).findFirst();
        return favouriteEventSchedule != null;
    }

    @Override
    public List<AUFavouriteEventSchedule> findAll() {
        return realm.where(AUFavouriteEventSchedule.class).findAll();
    }

    @Override
    public AUFavouriteEventSchedule findBy(String key, String name) {
        return null;
    }

    @Override
    public List<AUFavouriteEventSchedule> findAllBy(String key, String name) {
        return null;
    }
}
