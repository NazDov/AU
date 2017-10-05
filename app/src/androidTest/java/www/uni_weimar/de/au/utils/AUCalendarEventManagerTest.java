package www.uni_weimar.de.au.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import www.uni_weimar.de.au.models.AUFacultyEventSchedule;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static www.uni_weimar.de.au.utils.AUCalendarEventScheduleQueryHandler.*;

/**
 * Created by user on 29.09.17.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class AUCalendarEventManagerTest {

    AUCalendarEventScheduleQueryHandler eventScheduleQueryHandler;
    private String eventDuration;
    private String eventTime;
    private Context context;

    @Before
    public void init() {
        getInstrumentation().runOnMainSync(() -> {
            context = InstrumentationRegistry.getContext();
            eventScheduleQueryHandler = new AUCalendarEventScheduleQueryHandler(context.getContentResolver(), context);
        });
        eventDuration = "von 16.12.2017";
        eventTime = "15:00 bis 18:00 s.t.";
    }

    @Test
    public void testExtractFormattedDateTimeString() {
        String actualBeginFormDateTime = eventScheduleQueryHandler.extractFormattedEventDateTime(eventDuration, eventTime, DateType.BEGIN_DATE);
        String expBeginFormDateTime = "16.12.2017 15:00";
        String actualEndFormDateTime = eventScheduleQueryHandler.extractFormattedEventDateTime(eventDuration, eventTime, DateType.END_DATE);
        String expEndFormDateTime = "16.12.2017 18:00";
        assertEquals(expBeginFormDateTime, actualBeginFormDateTime);
        assertEquals(expEndFormDateTime, actualEndFormDateTime);
    }

    @Test
    public void testHandleEventDateTimeParse() {
        String date = "16.12.2017 15:00";
        Map<CalendarParams, Integer> eventDateTimeValues = eventScheduleQueryHandler.handleOnEventDateTimeParseException(date);
        int day = eventDateTimeValues.get(CalendarParams.DAY);
        int year = eventDateTimeValues.get(CalendarParams.YEAR);
        int month = eventDateTimeValues.get(CalendarParams.MONTH);
        int hour = eventDateTimeValues.get(CalendarParams.HOUR);
        int minutes = eventDateTimeValues.get(CalendarParams.MINUTE);
        assertEquals(16, day);
        assertEquals(11, month);
        assertEquals(2017, year);
        assertEquals(15, hour);
        assertEquals(0, minutes);
    }

    @Test
    public void testHandleParseEventDate() {
        String date = "16.12.2017";
        Map<CalendarParams, Integer> eventDateVals = eventScheduleQueryHandler.handleOnEventDateParseException(date);
        int day = eventDateVals.get(CalendarParams.DAY);
        int month = eventDateVals.get(CalendarParams.MONTH);
        int year = eventDateVals.get(CalendarParams.YEAR);
        assertEquals(16, day);
        assertEquals(11, month);
        assertEquals(2017, year);
    }

    @Test
    public void testGetEventDateTimeValues() {
        Map<CalendarParams, Integer> eventDateTimeValues = eventScheduleQueryHandler.getEventDateTimeValues(eventDuration, eventTime, DateType.BEGIN_DATE);
        int day = eventDateTimeValues.get(CalendarParams.DAY);
        int year = eventDateTimeValues.get(CalendarParams.YEAR);
        int month = eventDateTimeValues.get(CalendarParams.MONTH);
        int hour = eventDateTimeValues.get(CalendarParams.HOUR);
        int minutes = eventDateTimeValues.get(CalendarParams.MINUTE);
        assertEquals(16, day);
        assertEquals(11, month);
        assertEquals(2017, year);
        assertEquals(15, hour);
        assertEquals(0, minutes);
    }

    @Test
    public void testExtractNumberPart() {
        String num1 = "11";
        String num2 = "05";
        String num3 = "00";
        int actualNum1 = eventScheduleQueryHandler.extractNumberPart(num1);
        int actualNum2 = eventScheduleQueryHandler.extractNumberPart(num2);
        int actualNum3 = eventScheduleQueryHandler.extractNumberPart(num3);
        assertSame(11, actualNum1);
        assertSame(5, actualNum2);
        assertSame(0, actualNum3);
    }

    @Test
    public void testGetCalendarId(){
        int calendarId = eventScheduleQueryHandler.getCalendarID();
        System.out.println(calendarId);
    }

    @Test
    public void testGetRecurringCalendarRule() {
        AUFacultyEventSchedule facultyEventScheduleFirst =
                new AUFacultyEventSchedule.EventScheduleBuilder()
                        .setEventScheduleDay("Di.")
                        .setEventScheduleDuration("von 17.10.2017")
                        .setEventSchedulePeriod("wöch.")
                        .setEventScheduleTime("14:00 bis 18:00")
                        .build();
        AUFacultyEventSchedule facultyEventScheduleSecond =
                new AUFacultyEventSchedule.EventScheduleBuilder()
                        .setEventScheduleDay("Di.")
                        .setEventScheduleDuration("17.10.2017 bis 26.01.2018")
                        .setEventSchedulePeriod("wöch.")
                        .setEventScheduleTime("19:00 bis 20:30")
                        .build();
        String actualRecurCalRuleFirst = eventScheduleQueryHandler.getRecurringCalendarRule(facultyEventScheduleFirst);
        String expRecurCalRuleFirst = "FREQ=WEEKLY;COUNT=20;WKST=SU;BYDAY=TU";
        String actualRecurCalRuleSecond = eventScheduleQueryHandler.getRecurringCalendarRule(facultyEventScheduleSecond);
        String expRecurCalRuleSecond = "FREQ=WEEKLY;UNTIL=20180127T000000Z;WKST=SU;BYDAY=TU";
        assertEquals(expRecurCalRuleFirst, actualRecurCalRuleFirst);
        assertEquals(expRecurCalRuleSecond, actualRecurCalRuleSecond);
    }

    @Test
    public void testDateParser() {
        String parseString = "16.12.2017 15:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(parseString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
    }
}
