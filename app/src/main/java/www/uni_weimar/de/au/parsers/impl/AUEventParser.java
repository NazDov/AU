package www.uni_weimar.de.au.parsers.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import io.realm.RealmList;
import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.utils.AULinkUtil;

import static com.google.common.base.Preconditions.checkNotNull;
import static www.uni_weimar.de.au.utils.AULinkUtil.returnUniqueLinkPart;

/**
 * Created by nazar on 20.07.17
 */

public class AUEventParser implements AUParser<AUFacultyEvent> {

    private static final String SEMESTER = "Semester";
    private static final String LECTURER_SELECTOR = "a[href*='personal.pid']";
    private static final String EVENT_SCHEDULE_ID_SELECTOR = "a[href*='veranstaltung.veranstid']";
    private static final String EVENT_TYPE = "Veranstaltungsart";
    private static final String EVENT_NUMBER = "Veranstaltungsnummer";
    private static final String EVENT_RHYTMUS = "Rhythmus";
    private static final String EVENT_SWS = "SWS";
    private static final String EVENT_DESCRIPTION = "Beschreibung";
    private static final String EVENT_SCHEDULE_TABLE = "Übersicht über alle Veranstaltungstermine";
    private static final String TD_TAG_SELECTOR = "td:nth-child(2)";
    private static final String TD_ZEIT_SELECTOR = "td:nth-child(3)";
    private static final String TD_RHYTMUS_SELECTOR = "td:nth-child(4)";
    private static final String TD_DAUER_SELECTOR = "td:nth-child(5)";
    private static final String TD_RAUM_SELECTOR = "td:nth-child(6)";
    private static final String TD_LEHR_PERSON_SELECTOR = "td:nth-child(8)";
    private static final String TD_MAX_PART_SELECTOR = "td:nth-child(11)";
    private static final String EVENT_NAME_SELECTOR = "h1";
    private final String eventURL;
    private String eventName;

    private AUEventParser(String eventURL) {
        this.eventURL = eventURL;
    }

    public static AUEventParser of(String url) {
        url = checkNotNull(url);
        return new AUEventParser(url);
    }


    @Override
    public List<AUFacultyEvent> parseAU(String url) throws AUParseException {
        RealmList<AUFacultyEvent> auFacultyEvents = new RealmList<>();
        Document htmlDoc;
        try {
            htmlDoc = Jsoup.connect(url).get();
            eventName = parseEventName(htmlDoc);
            auFacultyEvents.add(new AUFacultyEvent.EventBuilder()
                    .setEventURL(url)
                    .setEventName(eventName)
                    .setEventType(parseEventTableVal(htmlDoc, EVENT_TYPE))
                    .setEventNumber(parseEventTableVal(htmlDoc, EVENT_NUMBER))
                    .setEventRhytmus(parseEventTableVal(htmlDoc, EVENT_RHYTMUS))
                    .setEventSWS(parseEventTableVal(htmlDoc, EVENT_SWS))
                    .setEventSemester(parseEventTableVal(htmlDoc, SEMESTER))
                    .setEventLecturer(parseEventLectureName(htmlDoc))
                    .setEventDescription(parseEventTableVal(htmlDoc, EVENT_DESCRIPTION))
                    .setAuEventScheduleList(parseEventSchedule(htmlDoc, EVENT_SCHEDULE_TABLE))
                    .build());
        } catch (IOException e) {
            throw new AUParseException(e.getLocalizedMessage());
        }

        return auFacultyEvents;
    }

    private RealmList<AUFacultyEventSchedule> parseEventSchedule(Document htmlDoc, String eventScheduleTable) {
        RealmList<AUFacultyEventSchedule> eventScheduleList = new RealmList<>();
        Elements eventTRItems = htmlDoc.select("table[summary=" + eventScheduleTable + "] tr:not(:first-child)");
        for (Element eventTRItem : eventTRItems) {
            eventScheduleList.add(new AUFacultyEventSchedule.EventScheduleBuilder()
                    .setEventURL(eventURL)
                    .setEventName(eventName)
                    .setEventScheduleID(parseEventScheduleID(eventTRItem))
                    .setEventScheduleDay(parseEventValBySelector(eventTRItem, TD_TAG_SELECTOR))
                    .setEventScheduleTime(parseEventValBySelector(eventTRItem, TD_ZEIT_SELECTOR))
                    .setEventScheduleDuration(parseEventValBySelector(eventTRItem, TD_DAUER_SELECTOR))
                    .setEventSchedulePeriod(parseEventValBySelector(eventTRItem, TD_RHYTMUS_SELECTOR))
                    .setEventScheduleRoom(parseEventValBySelector(eventTRItem, TD_RAUM_SELECTOR))
                    .setEventScheduleLecturer(parseEventValBySelector(eventTRItem, TD_LEHR_PERSON_SELECTOR))
                    .setEventMaxParticipants(parseEventValBySelector(eventTRItem, TD_MAX_PART_SELECTOR))
                    .build());
        }
        return eventScheduleList;
    }

    private String parseEventValBySelector(Element trItem, String selector) {
        Elements select = trItem.select(selector);
        return select == null || select.isEmpty() ? null : select.get(0).text();
    }

    private String parseEventScheduleID(Element trItem){
        Elements eventScheduleIdElements = trItem.select(EVENT_SCHEDULE_ID_SELECTOR);
        String s = returnUniqueLinkPart(eventScheduleIdElements.attr(AUItem.HREF));
        return eventScheduleIdElements != null && !eventScheduleIdElements.isEmpty()? s : null;
    }

    private String parseEventName(Document html) {
        Element eventNameHtmlElement = html.select(EVENT_NAME_SELECTOR).get(0);
        return eventNameHtmlElement != null ? eventNameHtmlElement.text().split("-")[0] : null;
    }

    private String parseEventLectureName(Document htmlDoc) {
        Elements lectureHref = htmlDoc.select(LECTURER_SELECTOR);
        return lectureHref != null && !lectureHref.isEmpty() ? lectureHref.text() : null;
    }

    private String parseEventTableVal(Document htmlDoc, String key) {
        Elements th = htmlDoc.select("th:containsOwn(" + key + ") + td");
        return th != null && !th.isEmpty() ? th.get(0).text() : null;
    }

    @Override
    public List<AUFacultyEvent> parseAU() throws AUParseException {
        return parseAU(eventURL);
    }
}
