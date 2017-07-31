package www.uni_weimar.de.au.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUDays;
import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.service.impl.AUEventContentRequestService;

import static www.uni_weimar.de.au.view.activity.AUEventItemDetailsActivity.EVENT_URL;

public class AUEventScheduleActivity extends AppCompatActivity {

    private static final List<AUFacultyEventSchedule> EMPTY_SCHEDULE_LIST = new ArrayList<>(0);
    private static final String _08_HOUR = "08";
    private static final String _09_HOUR = "09";
    private static final String _10_HOUR = "10";
    private static final String _11_HOUR = "11";
    private static final String _12_HOUR = "12";
    private static final String _13_HOUR = "13";
    private static final String _14_HOUR = "14";
    private static final String _15_HOUR = "15";
    private static final String _16_HOUR = "16";
    private static final String _17_HOUR = "17";
    private static final String _18_HOUR = "18";
    private static final String _19_HOUR = "19";
    private static final String _20_HOUR = "20";
    private static final String _21_HOUR = "21";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    //    @InjectView(R.id.auEventScheduleRecyclerView)
//    RecyclerView auEventScheduleRecyclerView;
//    AUEventScheduleRecyclerViewAdapter adapter;
    String eventName;
    String eventURL;
    Realm realmUI;
    AUFacultyEvent auFacultyEvent;
    List<AUFacultyEventSchedule> auFacultyEventSchedules;

    @InjectView(R.id.au_tt_mo_08_00_wrapper)
    LinearLayout auMon0800W;
    @InjectView(R.id.au_tt_tue_08_00_wrapper)
    LinearLayout auTue0800W;
    @InjectView(R.id.au_tt_wed_08_00_wrapper)
    LinearLayout auWed0800W;
    @InjectView(R.id.au_tt_thu_08_00_wrapper)
    LinearLayout auThu0800W;
    @InjectView(R.id.au_tt_fri_08_00_wrapper)
    LinearLayout auFr0800W;

    @InjectView(R.id.au_tt_mo_09_00_wrapper)
    LinearLayout auMon0900W;
    @InjectView(R.id.au_tt_tue_09_00_wrapper)
    LinearLayout auTue0900W;
    @InjectView(R.id.au_tt_wed_09_00_wrapper)
    LinearLayout auWed0900W;
    @InjectView(R.id.au_tt_thu_09_00_wrapper)
    LinearLayout auThu0900W;
    @InjectView(R.id.au_tt_fr_09_00_wrapper)
    LinearLayout auFr0900W;


    @InjectView(R.id.au_tt_mo_10_00_wrapper)
    LinearLayout auMon1000W;
    @InjectView(R.id.au_tt_tue_10_00_wrapper)
    LinearLayout auTue1000W;
    @InjectView(R.id.au_tt_wed_10_00_wrapper)
    LinearLayout auWed1000W;
    @InjectView(R.id.au_tt_thu_10_00_wrapper)
    LinearLayout auThu1000W;
    @InjectView(R.id.au_tt_fr_10_00_wrapper)
    LinearLayout auFr1000W;

    @InjectView(R.id.au_tt_mo_11_00_wrapper)
    LinearLayout auMon1100W;
    @InjectView(R.id.au_tt_tue_11_00_wrapper)
    LinearLayout auTue1100W;
    @InjectView(R.id.au_tt_wed_11_00_wrapper)
    LinearLayout auWed1100W;
    @InjectView(R.id.au_tt_thu_11_00_wrapper)
    LinearLayout auThu1100W;
    @InjectView(R.id.au_tt_fr_11_00_wrapper)
    LinearLayout auFr1100W;

    @InjectView(R.id.au_tt_mo_12_00_wrapper)
    LinearLayout auMon1200W;
    @InjectView(R.id.au_tt_tue_12_00_wrapper)
    LinearLayout auTue1200W;
    @InjectView(R.id.au_tt_wed_12_00_wrapper)
    LinearLayout auWed1200W;
    @InjectView(R.id.au_tt_thu_12_00_wrapper)
    LinearLayout auThu1200W;
    @InjectView(R.id.au_tt_fr_12_00_wrapper)
    LinearLayout auFr1200W;

    @InjectView(R.id.au_tt_mo_13_00_wrapper)
    LinearLayout auMon1300W;
    @InjectView(R.id.au_tt_tue_13_00_wrapper)
    LinearLayout auTue1300W;
    @InjectView(R.id.au_tt_wed_13_00_wrapper)
    LinearLayout auWed1300W;
    @InjectView(R.id.au_tt_thu_13_00_wrapper)
    LinearLayout auThu1300W;
    @InjectView(R.id.au_tt_fr_13_00_wrapper)
    LinearLayout auFr1300W;

    @InjectView(R.id.au_tt_mo_14_00_wrapper)
    LinearLayout auMon1400W;
    @InjectView(R.id.au_tt_tue_14_00_wrapper)
    LinearLayout auTue1400W;
    @InjectView(R.id.au_tt_wed_14_00_wrapper)
    LinearLayout auWed1400W;
    @InjectView(R.id.au_tt_thu_14_00_wrapper)
    LinearLayout auThu1400W;
    @InjectView(R.id.au_tt_fr_14_00_wrapper)
    LinearLayout auFr1400W;

    @InjectView(R.id.au_tt_mo_15_00_wrapper)
    LinearLayout auMon1500W;
    @InjectView(R.id.au_tt_tue_15_00_wrapper)
    LinearLayout auTue1500W;
    @InjectView(R.id.au_tt_wed_15_00_wrapper)
    LinearLayout auWed1500W;
    @InjectView(R.id.au_tt_thu_15_00_wrapper)
    LinearLayout auThu1500W;
    @InjectView(R.id.au_tt_fr_15_00_wrapper)
    LinearLayout auFr1500W;

    @InjectView(R.id.au_tt_mo_16_00_wrapper)
    LinearLayout auMon1600W;
    @InjectView(R.id.au_tt_tue_16_00_wrapper)
    LinearLayout auTue1600W;
    @InjectView(R.id.au_tt_wed_16_00_wrapper)
    LinearLayout auWed1600W;
    @InjectView(R.id.au_tt_thu_16_00_wrapper)
    LinearLayout auThu1600W;
    @InjectView(R.id.au_tt_fr_16_00_wrapper)
    LinearLayout auFr1600W;

    @InjectView(R.id.au_tt_mo_17_00_wrapper)
    LinearLayout auMon1700W;
    @InjectView(R.id.au_tt_tue_17_00_wrapper)
    LinearLayout auTue1700W;
    @InjectView(R.id.au_tt_wed_17_00_wrapper)
    LinearLayout auWed1700W;
    @InjectView(R.id.au_tt_thu_17_00_wrapper)
    LinearLayout auThu1700W;
    @InjectView(R.id.au_tt_fr_17_00_wrapper)
    LinearLayout auFr1700W;

    @InjectView(R.id.au_tt_mo_18_00_wrapper)
    LinearLayout auMon1800W;
    @InjectView(R.id.au_tt_tue_18_00_wrapper)
    LinearLayout auTue1800W;
    @InjectView(R.id.au_tt_wed_18_00_wrapper)
    LinearLayout auWed1800W;
    @InjectView(R.id.au_tt_thu_18_00_wrapper)
    LinearLayout auThu1800W;
    @InjectView(R.id.au_tt_fr_18_00_wrapper)
    LinearLayout auFr1800W;

    @InjectView(R.id.au_tt_mo_19_00_wrapper)
    LinearLayout auMon1900W;
    @InjectView(R.id.au_tt_tue_19_00_wrapper)
    LinearLayout auTue1900W;
    @InjectView(R.id.au_tt_wed_19_00_wrapper)
    LinearLayout auWed1900W;
    @InjectView(R.id.au_tt_thu_19_00_wrapper)
    LinearLayout auThu1900W;
    @InjectView(R.id.au_tt_fr_19_00_wrapper)
    LinearLayout auFr1900W;

    @InjectView(R.id.au_tt_mo_20_00_wrapper)
    LinearLayout auMon2000W;
    @InjectView(R.id.au_tt_tue_20_00_wrapper)
    LinearLayout auTue2000W;
    @InjectView(R.id.au_tt_wed_20_00_wrapper)
    LinearLayout auWed2000W;
    @InjectView(R.id.au_tt_thu_20_00_wrapper)
    LinearLayout auThu2000W;
    @InjectView(R.id.au_tt_fr_20_00_wrapper)
    LinearLayout auFr2000W;

    @InjectView(R.id.au_tt_mo_21_00_wrapper)
    LinearLayout auMon2100W;
    @InjectView(R.id.au_tt_tue_21_00_wrapper)
    LinearLayout auTue2100W;
    @InjectView(R.id.au_tt_wed_21_00_wrapper)
    LinearLayout auWed2100W;
    @InjectView(R.id.au_tt_thu_21_00_wrapper)
    LinearLayout auThu2100W;
    @InjectView(R.id.au_tt_fr_21_00_wrapper)
    LinearLayout auFr2100W;
    AUDays auDay;
    Map<AUDays, Map<String, LinearLayout>> auTimetableItemsMap;

    private Map<AUDays, Map<String, LinearLayout>> initAuTimeTableItemsMap() {
        Map<AUDays, Map<String, LinearLayout>> auTimetableItemsMap = new HashMap<>();
        auTimetableItemsMap.put(AUDays.MO, getMOTimeTableLayouts());
        auTimetableItemsMap.put(AUDays.TUE, getTUETimeTableLayouts());
        auTimetableItemsMap.put(AUDays.WED, getWedTimeTableLayouts());
        auTimetableItemsMap.put(AUDays.THU, getThuTimeTableLayouts());
        auTimetableItemsMap.put(AUDays.FR, getFrTimeTableLayouts());
        return auTimetableItemsMap;
    }

    private Map<String, LinearLayout> getMOTimeTableLayouts() {
        Map<String, LinearLayout> moTimeTableLayouts = new HashMap<>();
        moTimeTableLayouts.put(_08_HOUR, auMon0800W);
        moTimeTableLayouts.put(_09_HOUR, auMon0900W);
        moTimeTableLayouts.put(_10_HOUR, auMon1000W);
        moTimeTableLayouts.put(_11_HOUR, auMon1100W);
        moTimeTableLayouts.put(_12_HOUR, auMon1200W);
        moTimeTableLayouts.put(_13_HOUR, auMon1300W);
        moTimeTableLayouts.put(_14_HOUR, auMon1400W);
        moTimeTableLayouts.put(_15_HOUR, auMon1500W);
        moTimeTableLayouts.put(_16_HOUR, auMon1600W);
        moTimeTableLayouts.put(_17_HOUR, auMon1700W);
        moTimeTableLayouts.put(_18_HOUR, auMon1800W);
        moTimeTableLayouts.put(_19_HOUR, auMon1900W);
        moTimeTableLayouts.put(_20_HOUR, auMon2000W);
        moTimeTableLayouts.put(_21_HOUR, auMon2100W);
        return moTimeTableLayouts;
    }

    private Map<String, LinearLayout> getTUETimeTableLayouts() {
        Map<String, LinearLayout> tueTimeTableLayouts = new HashMap<>();
        tueTimeTableLayouts.put(_08_HOUR, auTue0800W);
        tueTimeTableLayouts.put(_09_HOUR, auTue0900W);
        tueTimeTableLayouts.put(_10_HOUR, auTue1000W);
        tueTimeTableLayouts.put(_11_HOUR, auTue1100W);
        tueTimeTableLayouts.put(_12_HOUR, auTue1200W);
        tueTimeTableLayouts.put(_13_HOUR, auTue1300W);
        tueTimeTableLayouts.put(_14_HOUR, auTue1400W);
        tueTimeTableLayouts.put(_15_HOUR, auTue1500W);
        tueTimeTableLayouts.put(_16_HOUR, auTue1600W);
        tueTimeTableLayouts.put(_17_HOUR, auTue1700W);
        tueTimeTableLayouts.put(_18_HOUR, auTue1800W);
        tueTimeTableLayouts.put(_19_HOUR, auTue1900W);
        tueTimeTableLayouts.put(_20_HOUR, auTue2000W);
        tueTimeTableLayouts.put(_21_HOUR, auTue2100W);
        return tueTimeTableLayouts;
    }

    private Map<String, LinearLayout> getWedTimeTableLayouts() {
        Map<String, LinearLayout> wedTimeTableLayouts = new HashMap<>();
        wedTimeTableLayouts.put(_08_HOUR, auWed0800W);
        wedTimeTableLayouts.put(_09_HOUR, auWed0900W);
        wedTimeTableLayouts.put(_10_HOUR, auWed1000W);
        wedTimeTableLayouts.put(_11_HOUR, auWed1100W);
        wedTimeTableLayouts.put(_12_HOUR, auWed1200W);
        wedTimeTableLayouts.put(_13_HOUR, auWed1300W);
        wedTimeTableLayouts.put(_14_HOUR, auWed1400W);
        wedTimeTableLayouts.put(_15_HOUR, auWed1500W);
        wedTimeTableLayouts.put(_16_HOUR, auWed1600W);
        wedTimeTableLayouts.put(_17_HOUR, auWed1700W);
        wedTimeTableLayouts.put(_18_HOUR, auWed1800W);
        wedTimeTableLayouts.put(_19_HOUR, auWed1900W);
        wedTimeTableLayouts.put(_20_HOUR, auWed2000W);
        wedTimeTableLayouts.put(_21_HOUR, auWed2100W);
        return wedTimeTableLayouts;
    }

    private Map<String, LinearLayout> getThuTimeTableLayouts() {
        Map<String, LinearLayout> thuTimeTableLayouts = new HashMap<>();
        thuTimeTableLayouts.put(_08_HOUR, auThu0800W);
        thuTimeTableLayouts.put(_09_HOUR, auThu0900W);
        thuTimeTableLayouts.put(_10_HOUR, auThu1000W);
        thuTimeTableLayouts.put(_11_HOUR, auThu1100W);
        thuTimeTableLayouts.put(_12_HOUR, auThu1200W);
        thuTimeTableLayouts.put(_13_HOUR, auThu1300W);
        thuTimeTableLayouts.put(_14_HOUR, auThu1400W);
        thuTimeTableLayouts.put(_15_HOUR, auThu1500W);
        thuTimeTableLayouts.put(_16_HOUR, auThu1600W);
        thuTimeTableLayouts.put(_17_HOUR, auThu1700W);
        thuTimeTableLayouts.put(_18_HOUR, auThu1800W);
        thuTimeTableLayouts.put(_19_HOUR, auThu1900W);
        thuTimeTableLayouts.put(_20_HOUR, auThu2000W);
        thuTimeTableLayouts.put(_21_HOUR, auThu2100W);
        return thuTimeTableLayouts;
    }

    private Map<String, LinearLayout> getFrTimeTableLayouts() {
        Map<String, LinearLayout> friTimeTableLayouts = new HashMap<>();
        friTimeTableLayouts.put(_08_HOUR, auFr0800W);
        friTimeTableLayouts.put(_09_HOUR, auFr0900W);
        friTimeTableLayouts.put(_10_HOUR, auFr1000W);
        friTimeTableLayouts.put(_11_HOUR, auFr1100W);
        friTimeTableLayouts.put(_12_HOUR, auFr1200W);
        friTimeTableLayouts.put(_13_HOUR, auFr1300W);
        friTimeTableLayouts.put(_14_HOUR, auFr1400W);
        friTimeTableLayouts.put(_15_HOUR, auFr1500W);
        friTimeTableLayouts.put(_16_HOUR, auFr1600W);
        friTimeTableLayouts.put(_17_HOUR, auFr1700W);
        friTimeTableLayouts.put(_18_HOUR, auFr1800W);
        friTimeTableLayouts.put(_19_HOUR, auFr1900W);
        friTimeTableLayouts.put(_20_HOUR, auFr2000W);
        friTimeTableLayouts.put(_21_HOUR, auFr2100W);
        return friTimeTableLayouts;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auevent_schedule);
        ButterKnife.inject(this);
        auTimetableItemsMap = initAuTimeTableItemsMap();
        setSupportActionBar(toolbar);
        eventName = getIntent().getStringExtra(AUItem.AU_ITEM_NAME_TAG);
        eventURL = getIntent().getStringExtra(AUItem.AU_ITEM_URL_TAG);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(eventName + " Schedule");
        realmUI = Realm.getDefaultInstance();
        AUEventContentRequestService.of(realmUI, eventURL)
                .notifyContentOnCacheUpdate(this::onSuccess, EVENT_URL, eventURL);

    }

    private void onSuccess(List<AUFacultyEvent> content) {
        if (content == null || content.isEmpty()) return;
        auFacultyEvent = content.get(0);
        auFacultyEventSchedules = auFacultyEvent != null ? auFacultyEvent.getAuEventScheduleList() : EMPTY_SCHEDULE_LIST;
//        adapter = new AUEventScheduleRecyclerViewAdapter(getBaseContext(), auFacultyEventSchedules);
//        auEventScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        auEventScheduleRecyclerView.setAdapter(adapter);


        initAUTimeTableItems();

    }

    private void initAUTimeTableItems() {
        for (AUFacultyEventSchedule facultyEventSchedule : auFacultyEventSchedules) {
            String eventDay = facultyEventSchedule.getEventScheduleDay();
            String eventTime = facultyEventSchedule.getEventScheduleTime();
            String eventRoom = facultyEventSchedule.getEventScheduleRoom();
            String eventTimeFrom = eventTime.split("bis")[0].replaceAll("\\s+$", "");
            String eventTimeEnd = eventTime.split("bis")[1].replaceAll("^\\s+", "");
            Map<String, LinearLayout> allTimeTableItems = null;

            if (AUDays.MO.toString().equals(eventDay)) {
                auDay = AUDays.MO;
                allTimeTableItems = auTimetableItemsMap.get(AUDays.MO);
            }

            if (AUDays.TUE.toString().equals(eventDay)) {
                auDay = AUDays.TUE;
                allTimeTableItems = auTimetableItemsMap.get(AUDays.TUE);
            }

            if (AUDays.WED.toString().equals(eventDay)) {
                auDay = AUDays.WED;
                allTimeTableItems = auTimetableItemsMap.get(AUDays.WED);
            }

            if (AUDays.THU.toString().equals(eventDay)) {
                auDay = AUDays.THU;
                allTimeTableItems = auTimetableItemsMap.get(AUDays.THU);
            }
            if (AUDays.FR.toString().equals(eventDay)) {
                auDay = AUDays.FR;
                allTimeTableItems = auTimetableItemsMap.get(AUDays.FR);
            }
            if (allTimeTableItems != null)
                showActiveTimeTableItems(allTimeTableItems, eventTimeFrom, eventTimeEnd, eventRoom);
        }
    }

    private void showActiveTimeTableItems(Map<String, LinearLayout> allTimeTableItems,
                                          String eventTimeFrom,
                                          String eventTimeEnd,
                                          String eventText) {
        List<LinearLayout> activeTimeTableItems = getActiveTimeTableItems(allTimeTableItems,
                eventTimeFrom, eventTimeEnd);
        for (int index = 0; index < activeTimeTableItems.size(); index++) {
            LinearLayout activeTimeTable = activeTimeTableItems.get(index);
            if (index == 0) {
                activeTimeTable.addView(getAUTimeTableTextViewWithText(eventText));
            }
            activeTimeTable.setBackgroundColor(getColorByDay());
            activeTimeTable.setOnClickListener(v -> {

            });
        }
    }

    private int getColorByDay() {
        int colorId = 0;
        switch (auDay) {
            case MO:
                colorId = getResources().getColor(R.color.TIME_TABLE_MO_COLOR);
                break;
            case TUE:
                colorId = getResources().getColor(R.color.TIME_TABLE_TUE_COLOR);
                break;
            case WED:
                colorId = getResources().getColor(R.color.TIME_TABLE_WED_COLOR);
                break;
            case THU:
                colorId = getResources().getColor(R.color.TIME_TABLE_THU_COLOR);
                break;
            case FR:
                colorId = getResources().getColor(R.color.TIME_TABLE_FR_COLOR);
                break;
            default:
                colorId = getResources().getColor(R.color.TIME_TABLE_FR_COLOR);
                break;
        }

        return colorId;
    }

    private List<LinearLayout> getActiveTimeTableItems(Map<String, LinearLayout> allTimeTables,
                                                       String eventTimeFrom,
                                                       String eventTimeEnd) {
        boolean addNextLayout = false;
        List<LinearLayout> timeTableItems = new ArrayList<>();
        List<String> allTimeTableTimes = new ArrayList<>(allTimeTables.keySet());
        Collections.sort(allTimeTableTimes);

        for (int index = 0; index < allTimeTableTimes.size(); index++) {
            String time = allTimeTableTimes.get(index);
            LinearLayout layout;
            if (eventTimeFrom.startsWith(time)) {
                addNextLayout = true;
            }

            if (eventTimeEnd.startsWith(time)) {
                break;
            }

            if (addNextLayout) {
                layout = allTimeTables.get(time);
                timeTableItems.add(layout);
            }
        }

        return timeTableItems;
    }


    private TextView getAUTimeTableTextViewWithText(String textName) {
        TextView textView = new TextView(this);
        textView.setText(textName);
        textView.setTextSize(12.0f);
        textView.setGravity(Gravity.TOP);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        return textView;
    }

}
