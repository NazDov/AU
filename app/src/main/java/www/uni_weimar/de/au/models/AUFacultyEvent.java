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
    private String eventName;
    private String eventLecturer;
    private String eventSemester;
    private String eventMaxParticipants;
    private String eventType;
    private String eventURL;
    private String eventNumber;
    private String eventRhytmus;
    private String eventSWS;
    private String eventDescription;
    private RealmList<AUFacultyEventSchedule> auEventScheduleList;

    public AUFacultyEvent() {

    }

    private AUFacultyEvent(String eventName,
                           String eventLecturer,
                           String eventSemester,
                           String eventMaxParticipants,
                           String eventType,
                           String eventNumber,
                           String eventRhytmus,
                           String eventSWS,
                           String eventDescription,
                           String eventURL,
                           RealmList<AUFacultyEventSchedule> auEventScheduleList) {
        this.eventName = eventName;
        this.eventLecturer = eventLecturer;
        this.eventSemester = eventSemester;
        this.eventMaxParticipants = eventMaxParticipants;
        this.eventType = eventType;
        this.eventNumber = eventNumber;
        this.eventRhytmus = eventRhytmus;
        this.eventSWS = eventSWS;
        this.eventDescription = eventDescription;
        this.eventURL = eventURL;
        this.auEventScheduleList = auEventScheduleList;
    }

    public AUFacultyEvent(EventBuilder eventBuilder) {
        this(eventBuilder.eventName,
                eventBuilder.eventLecturer,
                eventBuilder.eventSemester,
                eventBuilder.eventMaxParticipants,
                eventBuilder.eventType,
                eventBuilder.eventNumber,
                eventBuilder.eventRhytmus,
                eventBuilder.eventSWS,
                eventBuilder.eventDescription,
                eventBuilder.eventURL,
                eventBuilder.auEventScheduleList);
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

    public String getEventType() {
        return eventType;
    }

    public String getEventSWS() {
        return eventSWS;
    }

    public String getEventNumber() {
        return eventNumber;
    }

    public String getEventRhytmus() {
        return eventRhytmus;
    }

    public String getEventURL() {
        return eventURL;
    }

    public RealmList<AUFacultyEventSchedule> getAuEventScheduleList() {
        return auEventScheduleList;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUFacultyEvent)) return false;
        AUFacultyEvent that = (AUFacultyEvent) o;
        return Objects.equal(getEventName(), that.getEventName()) &&
                Objects.equal(getEventLecturer(), that.getEventLecturer()) &&
                Objects.equal(getEventSemester(), that.getEventSemester()) &&
                Objects.equal(getEventMaxParticipants(), that.getEventMaxParticipants()) &&
                Objects.equal(getEventType(), that.getEventType()) &&
                Objects.equal(getEventNumber(), that.getEventNumber()) &&
                Objects.equal(getEventRhytmus(), that.getEventRhytmus()) &&
                Objects.equal(getEventSWS(), that.getEventSWS()) &&
                Objects.equal(getAuEventScheduleList(), that.getAuEventScheduleList());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEventName(), getEventLecturer(), getEventSemester(), getEventMaxParticipants(), getEventType(), getEventNumber(), getEventRhytmus(), getEventSWS(), getAuEventScheduleList());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("eventName", eventName)
                .add("eventLecturer", eventLecturer)
                .add("eventSemester", eventSemester)
                .add("eventMaxParticipants", eventMaxParticipants)
                .add("eventType", eventType)
                .add("eventNumber", eventNumber)
                .add("eventRhytmus", eventRhytmus)
                .add("eventSWS", eventSWS)
                .add("auEventScheduleList", auEventScheduleList)
                .toString();
    }

    public static class EventBuilder {
        private String eventName;
        private String eventLecturer;
        private String eventSemester;
        private String eventMaxParticipants;
        private String eventType;
        private RealmList<AUFacultyEventSchedule> auEventScheduleList;
        private String eventNumber;
        private String eventRhytmus;
        private String eventSWS;
        private String eventDescription;
        private String eventURL;

        public EventBuilder setEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public EventBuilder setEventLecturer(String eventLecturer) {
            this.eventLecturer = eventLecturer;
            return this;
        }

        public EventBuilder setEventSemester(String eventSemester) {
            this.eventSemester = eventSemester;
            return this;
        }

        public EventBuilder setEventMaxParticipants(String eventMaxParticipants) {
            this.eventMaxParticipants = eventMaxParticipants;
            return this;
        }

        public EventBuilder setAuEventScheduleList(RealmList<AUFacultyEventSchedule> auEventScheduleList) {
            this.auEventScheduleList = auEventScheduleList;
            return this;
        }

        public EventBuilder setEventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public EventBuilder setEventNumber(String eventNumber) {
            this.eventNumber = eventNumber;
            return this;
        }

        public EventBuilder setEventRhytmus(String eventRhytmus) {
            this.eventRhytmus = eventRhytmus;
            return this;
        }

        public EventBuilder setEventSWS(String eventSWS) {
            this.eventSWS = eventSWS;
            return this;
        }


        public EventBuilder setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
            return this;
        }

        public EventBuilder setEventURL(String eventURL) {
            this.eventURL = eventURL;
            return this;
        }

        public AUFacultyEvent build() {
            return new AUFacultyEvent(this);
        }
    }
}
