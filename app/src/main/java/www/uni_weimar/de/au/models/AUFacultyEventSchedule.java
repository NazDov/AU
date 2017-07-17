package www.uni_weimar.de.au.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazar on 15.07.17.
 */

public class AUFacultyEventSchedule extends RealmObject implements AUItem {

    @PrimaryKey
    private String eventScheduleId = UUID.randomUUID().toString();
    private String eventScheduleDay;
    private String eventScheduleTime;
    private String eventSchedulePeriod;
    private String eventScheduleDuration;


    public AUFacultyEventSchedule() {

    }

    public AUFacultyEventSchedule(String eventScheduleDay,
                                  String eventScheduleTime,
                                  String eventSchedulePeriod,
                                  String eventScheduleDuration) {
        this.eventScheduleDay = eventScheduleDay;
        this.eventScheduleTime = eventScheduleTime;
        this.eventSchedulePeriod = eventSchedulePeriod;
        this.eventScheduleDuration = eventScheduleDuration;
    }

    public AUFacultyEventSchedule(EventScheduleBuilder eventScheduleBuilder) {
        this(eventScheduleBuilder.eventScheduleDay,
                eventScheduleBuilder.eventScheduleTime,
                eventScheduleBuilder.eventSchedulePeriod,
                eventScheduleBuilder.eventScheduleDuration);
    }


    public String getEventScheduleId() {
        return eventScheduleId;
    }

    public void setEventScheduleId(String eventScheduleId) {
        this.eventScheduleId = eventScheduleId;
    }

    public String getEventScheduleDay() {
        return eventScheduleDay;
    }

    public void setEventScheduleDay(String eventScheduleDay) {
        this.eventScheduleDay = eventScheduleDay;
    }

    public String getEventScheduleTime() {
        return eventScheduleTime;
    }

    public void setEventScheduleTime(String eventScheduleTime) {
        this.eventScheduleTime = eventScheduleTime;
    }

    public String getEventSchedulePeriod() {
        return eventSchedulePeriod;
    }

    public void setEventSchedulePeriod(String eventSchedulePeriod) {
        this.eventSchedulePeriod = eventSchedulePeriod;
    }

    public String getEventScheduleDuration() {
        return eventScheduleDuration;
    }

    public void setEventScheduleDuration(String eventScheduleDuration) {
        this.eventScheduleDuration = eventScheduleDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUFacultyEventSchedule)) return false;
        AUFacultyEventSchedule that = (AUFacultyEventSchedule) o;
        return Objects.equal(getEventScheduleId(), that.getEventScheduleId()) &&
                Objects.equal(getEventScheduleDay(), that.getEventScheduleDay()) &&
                Objects.equal(getEventScheduleTime(), that.getEventScheduleTime()) &&
                Objects.equal(getEventSchedulePeriod(), that.getEventSchedulePeriod()) &&
                Objects.equal(getEventScheduleDuration(), that.getEventScheduleDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEventScheduleId(), getEventScheduleDay(), getEventScheduleTime(), getEventSchedulePeriod(), getEventScheduleDuration());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("eventScheduleId", eventScheduleId)
                .add("eventScheduleDay", eventScheduleDay)
                .add("eventScheduleTime", eventScheduleTime)
                .add("eventSchedulePeriod", eventSchedulePeriod)
                .add("eventScheduleDuration", eventScheduleDuration)
                .toString();
    }

    public static class EventScheduleBuilder {
        private String eventScheduleDay;
        private String eventScheduleTime;
        private String eventSchedulePeriod;
        private String eventScheduleDuration;

        public EventScheduleBuilder setEventScheduleDay(String eventScheduleDay) {
            this.eventScheduleDay = eventScheduleDay;
            return this;
        }

        public EventScheduleBuilder setEventScheduleTime(String eventScheduleTime) {
            this.eventScheduleTime = eventScheduleTime;
            return this;
        }

        public EventScheduleBuilder setEventSchedulePeriod(String eventSchedulePeriod) {
            this.eventSchedulePeriod = eventSchedulePeriod;
            return this;
        }

        public EventScheduleBuilder setEventScheduleDuration(String eventScheduleDuration) {
            this.eventScheduleDuration = eventScheduleDuration;
            return this;
        }

        public AUFacultyEventSchedule build() {
            return new AUFacultyEventSchedule(this);
        }
    }
}
