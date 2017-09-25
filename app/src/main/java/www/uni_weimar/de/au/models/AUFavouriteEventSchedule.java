package www.uni_weimar.de.au.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 22.09.17.
 */

public class AUFavouriteEventSchedule extends RealmObject implements AUItem {

    @PrimaryKey
    private String eventFavouriteId = UUID.randomUUID().toString();
    private String eventScheduleId;

    public AUFavouriteEventSchedule() {

    }

    public AUFavouriteEventSchedule(String eventURL) {
        this.eventScheduleId = eventURL;
    }

    public String getEventFavouriteId() {
        return eventFavouriteId;
    }

    public void setEventFavouriteId(String eventFavouriteId) {
        this.eventFavouriteId = eventFavouriteId;
    }

    public String getEventScheduleId() {
        return eventScheduleId;
    }

    public void setEventScheduleId(String eventScheduleId) {
        this.eventScheduleId = eventScheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AUFavouriteEventSchedule that = (AUFavouriteEventSchedule) o;
        return Objects.equal(eventFavouriteId, that.eventFavouriteId) &&
                Objects.equal(eventScheduleId, that.eventScheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventFavouriteId, eventScheduleId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("eventFavouriteId", eventFavouriteId)
                .add("eventScheduleId", eventScheduleId)
                .toString();
    }
}
