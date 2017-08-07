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
    private String eventScheduleRoom;
    private String eventScheduleLecturer;
    private String eventMaxParticipants;


    public AUFacultyEventSchedule() {

    }

    public AUFacultyEventSchedule(String eventScheduleDay,
                                  String eventScheduleTime,
                                  String eventSchedulePeriod,
                                  String eventScheduleRoom,
                                  String eventScheduleDuration,
                                  String eventMaxParticipants,
                                  String eventScheduleLecturer) {
        this.eventScheduleDay = eventScheduleDay;
        this.eventScheduleTime = eventScheduleTime;
        this.eventSchedulePeriod = eventSchedulePeriod;
        this.eventScheduleDuration = eventScheduleDuration;
        this.eventScheduleLecturer = eventScheduleLecturer;
        this.eventMaxParticipants = eventMaxParticipants;
        this.eventScheduleRoom = eventScheduleRoom;
    }

    public AUFacultyEventSchedule(EventScheduleBuilder eventScheduleBuilder) {
        this(eventScheduleBuilder.eventScheduleDay,
                eventScheduleBuilder.eventScheduleTime,
                eventScheduleBuilder.eventSchedulePeriod,
                eventScheduleBuilder.eventScheduleRoom,
                eventScheduleBuilder.eventScheduleDuration,
                eventScheduleBuilder.eventMaxParticipants,
                eventScheduleBuilder.eventScheduleLecturer);
    }


    public String getEventScheduleId() {
        return eventScheduleId;
    }

    public String getEventScheduleDay() {
        return eventScheduleDay;
    }

    public String getEventScheduleTime() {
        return eventScheduleTime;
    }

    public String getEventSchedulePeriod() {
        return eventSchedulePeriod;
    }

    public String getEventScheduleDuration() {
        return eventScheduleDuration;
    }

    public String getEventScheduleRoom() {
        return eventScheduleRoom;
    }

    public String getEventMaxParticipants() {
        return eventMaxParticipants;
    }

    public String getEventScheduleLecturer() {
        return eventScheduleLecturer;
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
                Objects.equal(getEventScheduleDuration(), that.getEventScheduleDuration()) &&
                Objects.equal(getEventScheduleRoom(), that.getEventScheduleRoom()) &&
                Objects.equal(getEventScheduleLecturer(), that.getEventScheduleLecturer()) &&
                Objects.equal(getEventMaxParticipants(), that.getEventMaxParticipants());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEventScheduleId(), getEventScheduleDay(), getEventScheduleTime(), getEventSchedulePeriod(), getEventScheduleDuration(), getEventScheduleRoom(), getEventScheduleLecturer(), getEventMaxParticipants());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("eventScheduleId", eventScheduleId)
                .add("eventScheduleDay", eventScheduleDay)
                .add("eventScheduleTime", eventScheduleTime)
                .add("eventSchedulePeriod", eventSchedulePeriod)
                .add("eventScheduleDuration", eventScheduleDuration)
                .add("eventScheduleRoom", eventScheduleRoom)
                .add("eventScheduleLecturer", eventScheduleLecturer)
                .add("eventMaxParticipants", eventMaxParticipants)
                .toString();
    }

    public static class EventScheduleBuilder {
        private String eventScheduleDay;
        private String eventScheduleTime;
        private String eventSchedulePeriod;
        private String eventScheduleDuration;
        private String eventScheduleRoom;
        private String eventScheduleLecturer;
        private String eventMaxParticipants;

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

        public EventScheduleBuilder setEventScheduleRoom(String eventScheduleRoom) {
            this.eventScheduleRoom = eventScheduleRoom;
            return this;
        }

        public EventScheduleBuilder setEventScheduleLecturer(String eventScheduleLecturer) {
            this.eventScheduleLecturer = eventScheduleLecturer;
            return this;
        }

        public EventScheduleBuilder setEventMaxParticipants(String eventMaxParticipants) {
            this.eventMaxParticipants = eventMaxParticipants;
            return this;
        }

        public AUFacultyEventSchedule build() {
            return new AUFacultyEventSchedule(this);
        }
    }
}
