package www.uni_weimar.de.au.service.impl;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.models.AUFavouriteEventSchedule;
import www.uni_weimar.de.au.orm.AUEventScheduleFavouriteORM;
import www.uni_weimar.de.au.orm.AUEventScheduleORM;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

/**
 * Created by user on 22.09.17.
 */

public class AUEventScheduleFavouriteContentRequestService extends AUAbstractContentRequestService<AUFacultyEventSchedule> {

    private AUEventScheduleFavouriteORM eventScheduleFavouriteORM;

    public AUEventScheduleFavouriteContentRequestService(Realm realm) {
        super(new AUEventScheduleORM(realm), null);
        eventScheduleFavouriteORM = new AUEventScheduleFavouriteORM(realm);
    }


    @Override
    public AUAbstractContentRequestService<AUFacultyEventSchedule> notifyContentOnCacheUpdate(AUContentChangeListener<AUFacultyEventSchedule> auContentChangeListener) {
        auContentChangeListener.notifyContentChange(populateEventSchedulesFromFavouritesIfExist());
        return this;
    }

    private @NonNull List<AUFacultyEventSchedule> populateEventSchedulesFromFavouritesIfExist() {
        List<AUFavouriteEventSchedule> favouriteEventSchedules = eventScheduleFavouriteORM.findAll();
        List<AUFacultyEventSchedule> facultyEventSchedules = new ArrayList<>();
        if (!favouriteEventSchedules.isEmpty()) {
            facultyEventSchedules = populateEventSchedulesFromFavourites(favouriteEventSchedules);
        }
        return facultyEventSchedules;
    }

    private List<AUFacultyEventSchedule> populateEventSchedulesFromFavourites(List<AUFavouriteEventSchedule> favouriteEventSchedules) {
        List<AUFacultyEventSchedule> facultyEventSchedules;
        String[] allEventSchedulesIDs = new String[favouriteEventSchedules.size()];
        for (int i = 0; i < favouriteEventSchedules.size(); i++) {
            allEventSchedulesIDs[i] = favouriteEventSchedules.get(i).getEventScheduleId();
        }
        AUEventScheduleORM eventScheduleORM = (AUEventScheduleORM) getAuBaseORM();
        facultyEventSchedules = eventScheduleORM.findAllByEventID(allEventSchedulesIDs);
        return facultyEventSchedules;
    }
}
