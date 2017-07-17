package www.uni_weimar.de.au.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ndovhuy on 07.07.2017.
 */
public class AUFacultyEvent extends RealmObject implements AUItem {

    @PrimaryKey
    private String eventId = UUID.randomUUID().toString();
    private String eventName;
    private String eventLecturer;
    private String eventSemester;
    private String eventMaxParticipants;
    private RealmList<AUFacultyEventSchedule> auEventScheduleList;

    public AUFacultyEvent() {

    }

    private AUFacultyEvent(String eventName,
                           String eventLecturer,
                           String eventSemester,
                           String eventMaxParticipants,
                           RealmList<AUFacultyEventSchedule> auEventScheduleList) {
        this.eventName = eventName;
        this.eventLecturer = eventLecturer;
        this.eventSemester = eventSemester;
        this.eventMaxParticipants = eventMaxParticipants;
        this.auEventScheduleList = auEventScheduleList;
    }

    public AUFacultyEvent(EventBuilder eventBuilder) {
        this(eventBuilder.eventName,
                eventBuilder.eventLecturer,
                eventBuilder.eventSemester,
                eventBuilder.eventMaxParticipants,
                eventBuilder.auEventScheduleList);
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLecturer() {
        return eventLecturer;
    }

    public String getEventSemester() {
        return eventSemester;
    }

    public String getEventMaxParticipants() {
        return eventMaxParticipants;
    }


    public RealmList<AUFacultyEventSchedule> getAuEventScheduleList() {
        return auEventScheduleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUFacultyEvent)) return false;
        AUFacultyEvent that = (AUFacultyEvent) o;
        return Objects.equal(getEventId(), that.getEventId()) &&
                Objects.equal(getEventName(), that.getEventName()) &&
                Objects.equal(getEventLecturer(), that.getEventLecturer()) &&
                Objects.equal(getEventSemester(), that.getEventSemester()) &&
                Objects.equal(getEventMaxParticipants(), that.getEventMaxParticipants()) &&
                Objects.equal(getAuEventScheduleList(), that.getAuEventScheduleList());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEventId(), getEventName(), getEventLecturer(), getEventSemester(), getEventMaxParticipants(), getAuEventScheduleList());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("eventId", eventId)
                .add("eventName", eventName)
                .add("eventLecturer", eventLecturer)
                .add("eventSemester", eventSemester)
                .add("eventMaxParticipants", eventMaxParticipants)
                .add("auEventScheduleList", auEventScheduleList)
                .toString();
    }

    public static class EventBuilder {
        private String eventName;
        private String eventLecturer;
        private String eventSemester;
        private String eventMaxParticipants;
        private RealmList<AUFacultyEventSchedule> auEventScheduleList;

        public EventBuilder setEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public EventBuilder setEventLecturer(String eventLecturer) {
            this.eventLecturer = eventLecturer;
            return this;
        }

        public void setEventSemester(String eventSemester) {
            this.eventSemester = eventSemester;
        }

        public EventBuilder setEventMaxParticipants(String eventMaxParticipants) {
            this.eventMaxParticipants = eventMaxParticipants;
            return this;
        }

        public EventBuilder setAuEventScheduleList(RealmList<AUFacultyEventSchedule> auEventScheduleList) {
            this.auEventScheduleList = auEventScheduleList;
            return this;
        }

        public AUFacultyEvent build() {
            return new AUFacultyEvent(this);
        }
    }
}
