package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;

/**
 * Created by user on 22.09.17.
 */

public class AUEventScheduleORM implements AUBaseORM<AUFacultyEventSchedule> {

    private static final String EVENT_SCHEDULE_ID = "eventScheduleId";
    private final Realm realm;

    public AUEventScheduleORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AUFacultyEventSchedule add(AUFacultyEventSchedule item) {
        throw new UnsupportedOperationException("operation is not supported");
    }

    @Override
    public List<AUFacultyEventSchedule> addAll(List<AUFacultyEventSchedule> items) {
        throw new UnsupportedOperationException("operation is not supported");
    }

    @Override
    public boolean deleteAll(Class<AUFacultyEventSchedule> auFacultyEventScheduleClass) {
        throw new UnsupportedOperationException("operation is not supported");
    }

    @Override
    public void delete(String key, String name) {
        throw new UnsupportedOperationException("operation is not supported");
    }

    @Override
    public List<AUFacultyEventSchedule> findAll() {
        return realm.where(AUFacultyEventSchedule.class).findAll();
    }

    @Override
    public AUFacultyEventSchedule findBy(String key, String name) {
        throw new UnsupportedOperationException("operation is not supported");
    }

    @Override
    public List<AUFacultyEventSchedule> findAllBy(String key, String name) {
        throw new UnsupportedOperationException("operation is not supported");
    }


    public List<AUFacultyEventSchedule> findAllByEventID(String [] eventIDs) {
        return findAllBy(EVENT_SCHEDULE_ID, eventIDs);
    }

    public List<AUFacultyEventSchedule> findAllBy(String key, String [] names) {
        return realm.where(AUFacultyEventSchedule.class).in(key, names).findAll();
    }
}
