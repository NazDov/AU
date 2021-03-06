package www.uni_weimar.de.au.models;

import android.support.annotation.NonNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Comparator;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazar on 15.07.17.
 */

public class AUFacultyEventSchedule extends RealmObject implements AUItem, Comparable<AUFacultyEventSchedule> {

    @PrimaryKey
    private String eventScheduleId;
    private String eventScheduleDay;
    private String eventScheduleTime;
    private String eventSchedulePeriod;
    private String eventScheduleDuration;
    private String eventScheduleRoom;
    private String eventScheduleLecturer;
    private String eventMaxParticipants;
    private String eventURL;
    private String eventName;


    public AUFacultyEventSchedule() {

    }

    public AUFacultyEventSchedule(EventScheduleBuilder eventScheduleBuilder) {
        this(eventScheduleBuilder.eventScheduleId,
                eventScheduleBuilder.eventScheduleDay,
                eventScheduleBuilder.eventScheduleTime,
                eventScheduleBuilder.eventSchedulePeriod,
                eventScheduleBuilder.eventScheduleRoom,
                eventScheduleBuilder.eventScheduleDuration,
                eventScheduleBuilder.eventMaxParticipants,
                eventScheduleBuilder.eventScheduleLecturer,
                eventScheduleBuilder.eventURL,
                eventScheduleBuilder.eventName);
    }


    private AUFacultyEventSchedule(
            String eventScheduleId,
            String eventScheduleDay,
            String eventScheduleTime,
            String eventSchedulePeriod,
            String eventScheduleRoom,
            String eventScheduleDuration,
            String eventMaxParticipants,
            String eventScheduleLecturer,
            String eventURL,
            String eventName) {
        this.eventScheduleId = eventScheduleId;
        this.eventScheduleDay = eventScheduleDay;
        this.eventScheduleTime = eventScheduleTime;
        this.eventSchedulePeriod = eventSchedulePeriod;
        this.eventScheduleDuration = eventScheduleDuration;
        this.eventScheduleLecturer = eventScheduleLecturer;
        this.eventMaxParticipants = eventMaxParticipants;
        this.eventScheduleRoom = eventScheduleRoom;
        this.eventURL = eventURL;
        this.eventName = eventName;
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

    public String getEventURL() {
        return eventURL;
    }

    public String getEventName() {
        return eventName;
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

    @Override
    public int compareTo(@NonNull AUFacultyEventSchedule other) {
        if (hasEventScheduleDurationProperty(other)) {
            if (eventScheduleDuration.contains(AUEventScheduleDurationTags.AM.name())
                    && other.eventScheduleDuration.contains(AUEventScheduleDurationTags.AM.name())) {
                return eventScheduleDuration.compareTo(other.eventScheduleDuration);
            }
            if (eventScheduleDuration.contains(AUEventScheduleDurationTags.BIS.name())
                    && other.eventScheduleDuration.contains(AUEventScheduleDurationTags.BIS.name())) {
                return eventScheduleDuration.compareTo(other.eventScheduleDuration);
            }
            if (eventScheduleDuration.contains(AUEventScheduleDurationTags.VON.name())
                    && other.eventScheduleDuration.contains(AUEventScheduleDurationTags.VON.name())) {
                return eventScheduleDuration.compareTo(other.eventScheduleDuration);
            }
        }
        return 0;
    }

    private boolean hasEventScheduleDurationProperty(@NonNull AUFacultyEventSchedule other) {
        return eventScheduleDuration != null && !eventScheduleDuration.isEmpty()
                && other.eventScheduleDuration != null && !other.eventScheduleDuration.isEmpty();
    }

    public static class EventScheduleBuilder {
        private String eventScheduleId;
        private String eventScheduleDay;
        private String eventScheduleTime;
        private String eventSchedulePeriod;
        private String eventScheduleDuration;
        private String eventScheduleRoom;
        private String eventScheduleLecturer;
        private String eventMaxParticipants;
        private String eventURL;
        private String eventName;


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

        public EventScheduleBuilder setEventURL(String eventURL) {
            this.eventURL = eventURL;
            return this;
        }

        public EventScheduleBuilder setEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public EventScheduleBuilder setEventScheduleID(String eventScheduleID) {
            this.eventScheduleId = eventScheduleID;
            return this;
        }

        public AUFacultyEventSchedule build() {
            return new AUFacultyEventSchedule(this);
        }
    }
}
