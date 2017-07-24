package www.uni_weimar.de.au.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.service.impl.AUEventContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;
import www.uni_weimar.de.au.view.adapters.AUEventScheduleRecyclerViewAdapter;

import static www.uni_weimar.de.au.view.activity.AUEventItemDetailsActivity.EVENT_URL;

public class AUEventScheduleActivity extends AppCompatActivity {

    private static final List<AUFacultyEventSchedule> EMPTY_SCHEDULE_LIST = new ArrayList<>(0);
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.auEventScheduleRecyclerView)
    RecyclerView auEventScheduleRecyclerView;
    AUEventScheduleRecyclerViewAdapter adapter;
    String eventName;
    String eventURL;
    Realm realmUI;
    AUFacultyEvent auFacultyEvent;
    List<AUFacultyEventSchedule> auFacultyEventSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auevent_schedule);
        ButterKnife.inject(this);
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
        adapter = new AUEventScheduleRecyclerViewAdapter(getBaseContext(), auFacultyEventSchedules);
        auEventScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        auEventScheduleRecyclerView.setAdapter(adapter);
    }

}
