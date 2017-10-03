package www.uni_weimar.de.au.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import www.uni_weimar.de.au.models.AUCalendarEvent;
import www.uni_weimar.de.au.models.AUEventRecurrence;
import www.uni_weimar.de.au.models.AUEventScheduleDurationTags;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.orm.AUCalendarEventORM;

import static android.provider.CalendarContract.Events.*;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by user on 28.09.17.
 */

public class AUCalendarEventScheduleQueryHandler extends AsyncQueryHandler {


    private static final String NO_EVENT_DURATION_OR_TIME_SPECIFIED = "no event duration or time specified";
    private static final String FREQ_WEEKLY = "FREQ=WEEKLY;";
    private static final String UNTIL = "UNTIL=";
    private static final String T000000_Z = "T000000Z;";
    private static final String DD_MM_YYYY = "dd.MM.yyyy";
    private static final String SEMESTER = "COUNT=20;";
    private static final String WKST_SU = "WKST=SU;";
    private static final String BYDAY = "BYDAY=";
    private final Context context;
    private final ContentResolver contentResolver;
    private AUCalendarEventORM calendarEventORM;

    public AUCalendarEventScheduleQueryHandler(ContentResolver cr, Context context) {
        super(cr);
        this.contentResolver = cr;
        this.context = context;
        calendarEventORM = new AUCalendarEventORM();
    }

    enum CalendarParams {
        YEAR,
        MONTH,
        DAY,
        HOUR,
        MINUTE
    }

    enum DateType {
        BEGIN_DATE,
        END_DATE
    }


    /**
     * @param facultyEventSchedule
     */
    public void insertCalendarEvent(AUFacultyEventSchedule facultyEventSchedule) {
        facultyEventSchedule = checkNotNull(facultyEventSchedule);
        try {
            _insertCalendarEvent(facultyEventSchedule);
        } catch (AUInsertCalendarException e) {
            Log.e("insertCalendarEvent", e.getLocalizedMessage());
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void _insertCalendarEvent(@NonNull AUFacultyEventSchedule facultyEventSchedule) throws AUInsertCalendarException {
        String eventDuration = facultyEventSchedule.getEventScheduleDuration();
        String eventTime = facultyEventSchedule.getEventScheduleTime();
        if (isNotExisted(eventDuration, eventTime)) {
            throw new AUInsertCalendarException(NO_EVENT_DURATION_OR_TIME_SPECIFIED);
        }
        boolean isRecurringEvent = isRecurringEvent(eventDuration);
        Map<CalendarParams, Integer> eventBeginDateTimeVals = getEventDateTimeValues(eventDuration, eventTime, DateType.BEGIN_DATE);
        Map<CalendarParams, Integer> eventEndDateTimeVals = getEventDateTimeValues(eventDuration, eventTime, DateType.END_DATE);
        Calendar eventBeginTime = Calendar.getInstance();
        Calendar eventEndTime = Calendar.getInstance();
        eventBeginTime.set(eventBeginDateTimeVals.get(CalendarParams.YEAR), eventBeginDateTimeVals.get(CalendarParams.MONTH), eventBeginDateTimeVals.get(CalendarParams.DAY), eventBeginDateTimeVals.get(CalendarParams.HOUR), eventBeginDateTimeVals.get(CalendarParams.MINUTE));
        eventEndTime.set(eventEndDateTimeVals.get(CalendarParams.YEAR), eventEndDateTimeVals.get(CalendarParams.MONTH), eventEndDateTimeVals.get(CalendarParams.DAY), eventEndDateTimeVals.get(CalendarParams.HOUR), eventEndDateTimeVals.get(CalendarParams.MINUTE));
        long startMillis = eventBeginTime.getTimeInMillis();
        long endMillis = eventEndTime.getTimeInMillis();
        ContentValues contentValues = prepareCalendarEventContentValues(facultyEventSchedule, isRecurringEvent, startMillis, endMillis);
        AUManifestPermissionManager.doOnSuccessStoragePermissionOperation((Activity) context, () -> {
            startInsert(facultyEventSchedule.hashCode(), new EventCookie(startMillis, facultyEventSchedule), CalendarContract.Events.CONTENT_URI, contentValues);
        });
    }

    private boolean isRecurringEvent(String eventDuration) {
        boolean isRecurringEvent = false;
        if (!eventDuration.contains(AUEventScheduleDurationTags.AM.toString())) {
            isRecurringEvent = true;
        }
        return isRecurringEvent;
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        Toast.makeText(context, "event was successfully added to calendar " + uri.getPath(), Toast.LENGTH_SHORT).show();
        EventCookie eventCookie = (EventCookie) cookie;
        insertCalendarEventReminder(uri);
        openEventByTime(eventCookie.startMillis);
        //calendarEventORM.add(new AUCalendarEvent());
        super.onInsertComplete(token, cookie, uri);
    }


    public void insertCalendarEventReminder(Uri uri) {
        int eventID = extractEventIDFromURI(uri);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarContract.Reminders.MINUTES, 15);
        contentValues.put(CalendarContract.Reminders.EVENT_ID, eventID);
        contentValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, contentValues);
        }

    }

    private int extractEventIDFromURI(Uri uri) {
        int eventID;
        String uriPath = uri.getPath();
        String eventIDPath = uriPath.substring(uriPath.lastIndexOf("/") + 1);
        eventID = Integer.valueOf(eventIDPath);
        return eventID;
    }

    @NonNull
    private ContentValues prepareCalendarEventContentValues(@NonNull AUFacultyEventSchedule facultyEventSchedule, boolean isRecurringEvent, long startMillis, long endMillis) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CALENDAR_ID, getCalendarID());
        contentValues.put(DTSTART, startMillis);
        contentValues.put(DTEND, endMillis);
        contentValues.put(TITLE, facultyEventSchedule.getEventName());
        contentValues.put(EVENT_LOCATION, facultyEventSchedule.getEventScheduleRoom());
        contentValues.put(EVENT_TIMEZONE, TimeZone.getDefault().getDisplayName());
        contentValues.put(DESCRIPTION, facultyEventSchedule.getEventScheduleRoom());
        contentValues.put(ALL_DAY, 0);
        if (isRecurringEvent) {
            String recurringVal = getRecurringCalendarRule(facultyEventSchedule);
            contentValues.put(RRULE, recurringVal);
        }
        return contentValues;
    }

    int getCalendarID() {
        int calId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            calId = _getCalendarIdWithPermissionCheck();
        } else {
            calId = _getCalID();
        }
        return calId;
    }

    @TargetApi(23)
    private int _getCalendarIdWithPermissionCheck() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Integer calId = _getCalID();
            return calId;
        }
        return 3;
    }

    private int _getCalID() {
        Cursor cursor;
        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT,                 // 3
                CalendarContract.Calendars.IS_PRIMARY                     // 4
        };

        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        int PROJECTION_DISPLAY_NAME_INDEX = 2;
        int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
        int PROJECTION_VISIBLE = 4;
        Uri calendars = CalendarContract.Calendars.CONTENT_URI;

        cursor = contentResolver.query(calendars, EVENT_PROJECTION, null, null, null);
        if (cursor.moveToFirst()) {
            String calName;
            long calId;
            String visible;

            do {
                calName = cursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
                calId = cursor.getLong(PROJECTION_ID_INDEX);
                visible = cursor.getString(PROJECTION_VISIBLE);
                if (visible.equals("1")) {
                    return (int) calId;
                }
                Log.e("Calendar Id : ", "" + calId + " : " + calName + " : " + visible);
            } while (cursor.moveToNext());
            return (int) calId;
        }
        return 3;
    }

    private void openEventByTime(long timeInMillis) {
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, timeInMillis);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        context.startActivity(intent);
    }

    private boolean isNotExisted(String eventDuration, String eventTime) {
        return (eventDuration == null || eventDuration.isEmpty()) || (eventTime == null || eventTime.isEmpty());
    }

    @SuppressLint("SimpleDateFormat")
    @Nullable
    String getRecurringCalendarRule(@NonNull AUFacultyEventSchedule facultyEventSchedule) {
        StringBuilder recurringEventRRULEBuilder = new StringBuilder();
        recurringEventRRULEBuilder.append(FREQ_WEEKLY);
        if (facultyEventSchedule.getEventScheduleDuration()
                .contains(AUEventScheduleDurationTags.BIS.toString())) {
            String eventDate = extractDateFromDuration(facultyEventSchedule.getEventScheduleDuration(), DateType.END_DATE);
            Map<CalendarParams, Integer> calendarParams = null;
            try {
                calendarParams = getCalendarParams(eventDate, new SimpleDateFormat(DD_MM_YYYY));
                buildRRULE(recurringEventRRULEBuilder, calendarParams);
            } catch (ParseException e) {
                calendarParams = handleOnEventDateParseException(eventDate);
                buildRRULE(recurringEventRRULEBuilder, calendarParams);
            }
        }
        if (facultyEventSchedule.getEventScheduleDuration().contains(AUEventScheduleDurationTags.VON.toString())) {
            //set interval for the whole semester
            recurringEventRRULEBuilder.append(SEMESTER);
        }
        String recurrDayVal = getRecurringDayVal(facultyEventSchedule);
        recurringEventRRULEBuilder
                .append(WKST_SU)
                .append(BYDAY)
                .append(recurrDayVal);
        return recurringEventRRULEBuilder.toString();
    }

    private void buildRRULE(StringBuilder recurringEventRRULEBuilder, Map<CalendarParams, Integer> calendarParams) {
        int year = calendarParams.get(CalendarParams.YEAR);
        int month = calendarParams.get(CalendarParams.MONTH);
        int day = calendarParams.get(CalendarParams.DAY);
        month = month + 1;
        String monthF;
        String dayF;
        dayF = format(day);
        monthF = format(month);
        recurringEventRRULEBuilder.append(UNTIL).append(year).append(monthF).append(dayF).append(T000000_Z);
    }

    @NonNull
    private String format(int val) {
        String formVal;
        if (val > 0 && val < 10) {
            formVal = "0" + val;
        } else {
            formVal = "" + val;
        }
        return formVal;
    }

    private String getRecurringDayVal(AUFacultyEventSchedule facultyEventSchedule) {
        String recurByDayProp;
        switch (facultyEventSchedule.getEventScheduleDay()) {
            case "Mo.":
                recurByDayProp = "MO";
                break;
            case "Di.":
                recurByDayProp = "TU";
                break;
            case "Mi.":
                recurByDayProp = "WE";
                break;
            case "Do.":
                recurByDayProp = "TH";
                break;
            case "Fr.":
                recurByDayProp = "FR";
                break;
            default:
                recurByDayProp = "MO";

        }
        return recurByDayProp;
    }

    @NonNull
    Map<CalendarParams, Integer> getEventDateTimeValues(String eventDuration,
                                                        String eventTime,
                                                        DateType type) {
        Map<CalendarParams, Integer> dates;
        String eventDateTime = extractFormattedEventDateTime(eventDuration, eventTime, type);
        try {
            dates = _getEventDateTimeValues(eventDateTime);
        } catch (ParseException e) {
            dates = handleOnEventDateTimeParseException(eventDateTime);

        }
        return dates;
    }

    @NonNull
    Map<CalendarParams, Integer> handleOnEventDateTimeParseException(String eventDateTime) {
        Map<CalendarParams, Integer> dates;
        Calendar cal = Calendar.getInstance();
        String[] vals = eventDateTime.split("\\s+");
        String[] eventDateParts = vals[0].split("\\.");
        int day = extractNumberPart(eventDateParts[0].trim());
        int month = extractNumberPart(eventDateParts[1].trim());
        int year = extractNumberPart(eventDateParts[2].trim());
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        String[] eventTimeParts = vals[1].split(":");
        int hour = extractNumberPart(eventTimeParts[0].trim());
        int minutes = extractNumberPart(eventTimeParts[1].trim());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        Date date = cal.getTime();
        dates = getCalendarParamsFromDate(date);
        return dates;
    }

    @NonNull
    Map<CalendarParams, Integer> handleOnEventDateParseException(String eventDate) {
        Map<CalendarParams, Integer> dates;
        Calendar cal = Calendar.getInstance();
        String[] eventDateParts = eventDate.split("\\.");
        int day = extractNumberPart(eventDateParts[0]);
        int month = extractNumberPart(eventDateParts[1]);
        int year = extractNumberPart(eventDateParts[2]);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        dates = getCalendarParamsFromDate(cal.getTime());
        return dates;
    }

    int extractNumberPart(String eventDatePart) {
        eventDatePart = replaceAllTrailingSpaces(eventDatePart);
        int num = 0;
        if (eventDatePart != null && eventDatePart.length() > 1)
            if (eventDatePart.charAt(0) == '0') {
                num = Integer.valueOf(eventDatePart.substring(1));
            } else {
                num = Integer.valueOf(eventDatePart);
            }
        return num;
    }

    private String replaceAllTrailingSpaces(String eventDatePart) {
        return eventDatePart.replaceAll("^\\s+|\\s+$", "");
    }

    private Map<CalendarParams, Integer> _getEventDateTimeValues(String eventDateTime) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return getCalendarParams(eventDateTime, simpleDateFormat);
    }

    @NonNull
    private Map<CalendarParams, Integer> getCalendarParams(String eventDateTime, SimpleDateFormat simpleDateFormat) throws ParseException {
        Date parsedDate = simpleDateFormat.parse(eventDateTime);
        return getCalendarParamsFromDate(parsedDate);
    }

    @NonNull
    private Map<CalendarParams, Integer> getCalendarParamsFromDate(Date parsedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        return buildDates(calendar);
    }

    @NonNull
    private Map<CalendarParams, Integer> buildDates(Calendar calendar) {
        Map<CalendarParams, Integer> dates = new HashMap<>();
        dates.put(CalendarParams.YEAR, calendar.get(Calendar.YEAR));
        dates.put(CalendarParams.MONTH, calendar.get(Calendar.MONTH));
        dates.put(CalendarParams.DAY, calendar.get(Calendar.DAY_OF_MONTH));
        dates.put(CalendarParams.HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        dates.put(CalendarParams.MINUTE, calendar.get(Calendar.MINUTE));
        return dates;
    }

    @NonNull
    String extractFormattedEventDateTime(@NonNull String eventDuration,
                                         @NonNull String eventTime,
                                         DateType dateType) {
        StringBuilder dateTimeBuilder = new StringBuilder();
        String extEventDate = null;
        if (eventDuration.contains(AUEventScheduleDurationTags.AM.toString())) {
            extEventDate = eventDuration.replaceAll(AUEventScheduleDurationTags.AM.toString(), "").trim();
        }
        if (eventDuration.contains(AUEventScheduleDurationTags.VON.toString())) {
            extEventDate = eventDuration.replaceAll(AUEventScheduleDurationTags.VON.toString(), "").trim();
        }
        if (eventDuration.contains(AUEventScheduleDurationTags.BIS.toString())) {
            extEventDate = extractDateFromDuration(eventDuration, DateType.BEGIN_DATE);
        }
        String extEventTime = null;
        if (eventTime.contains(AUEventScheduleDurationTags.BIS.toString())) {
            String[] splitEventTime = eventTime.split(AUEventScheduleDurationTags.BIS.toString());
            extEventTime = splitEventTime[dateType.ordinal()].trim();
            if (extEventTime.contains("s.t.") || extEventTime.contains("c.t.")) {
                extEventTime = extEventTime.substring(0, 6).trim();
            }
        }
        dateTimeBuilder.append(extEventDate);
        dateTimeBuilder.append(" ");
        dateTimeBuilder.append(extEventTime);
        return dateTimeBuilder.toString();
    }

    @NonNull
    private String extractDateFromDuration(@NonNull String eventDuration, DateType dateType) {
        String extEventDate;
        String[] eventDurationParts = eventDuration.split(AUEventScheduleDurationTags.BIS.toString());
        extEventDate = eventDurationParts[dateType.ordinal()].trim();
        return extEventDate;
    }

    private class EventCookie {
        private final AUFacultyEventSchedule facultyEventSchedule;
        private final long startMillis;

        EventCookie(long startMillis, AUFacultyEventSchedule facultyEventSchedule) {
            this.startMillis = startMillis;
            this.facultyEventSchedule = facultyEventSchedule;
        }
    }

    private class AUInsertCalendarException extends Throwable {

        AUInsertCalendarException(String message) {
            super(message);
        }

    }
}
