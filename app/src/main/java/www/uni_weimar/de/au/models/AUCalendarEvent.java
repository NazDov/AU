package www.uni_weimar.de.au.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 02.10.17.
 */

public class AUCalendarEvent extends RealmObject implements AUItem {

    @PrimaryKey
    private Integer eventCalendarId;
    private String eventScheduleId;

    public AUCalendarEvent(){
    }

    public AUCalendarEvent(Integer eventCalendarId, String eventScheduleId) {
        this.eventCalendarId = eventCalendarId;
        this.eventScheduleId = eventScheduleId;
    }

    public Integer getEventCalendarId() {
        return eventCalendarId;
    }

    public String getEventScheduleId() {
        return eventScheduleId;
    }

    public void setEventCalendarId(Integer eventCalendarId) {
        this.eventCalendarId = eventCalendarId;
    }

    public void setEventScheduleId(String eventScheduleId) {
        this.eventScheduleId = eventScheduleId;
    }
}
