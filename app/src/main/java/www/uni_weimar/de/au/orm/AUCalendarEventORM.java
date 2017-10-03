package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUCalendarEvent;

/**
 * Created by user on 02.10.17.
 */

public class AUCalendarEventORM implements AUBaseORM<AUCalendarEvent> {
    @Override
    public AUCalendarEvent add(AUCalendarEvent item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(r -> {
            r.copyToRealmOrUpdate(item);
        });
        return item;
    }

    @Override
    public List<AUCalendarEvent> addAll(List<AUCalendarEvent> items) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(Class<AUCalendarEvent> auCalendarEventClass) {
        return false;
    }


    public void deleteByEventId(Integer eventCalendarId) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync((r) -> {
            AUCalendarEvent calendarEvent = r.where(AUCalendarEvent.class)
                    .equalTo("eventCalendarId", eventCalendarId)
                    .findFirst();
            calendarEvent.deleteFromRealm();
        });
    }

    @Override
    public void delete(String key, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AUCalendarEvent> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AUCalendarEvent findBy(String key, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AUCalendarEvent> findAllBy(String key, String name) {
        throw new UnsupportedOperationException();
    }
}
