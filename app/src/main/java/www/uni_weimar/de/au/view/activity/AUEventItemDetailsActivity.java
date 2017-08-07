package www.uni_weimar.de.au.view.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.service.impl.AUEventContentRequestService;
import www.uni_weimar.de.au.view.adapters.AUEventScheduleItemRecyclerViewAdapter;

public class AUEventItemDetailsActivity extends AppCompatActivity {

    protected static final String EVENT_URL = "eventURL";
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.auEventItemName)
    TextView auEventName;
    @InjectView(R.id.auVeranstaltungsartName)
    TextView auEventType;
    @InjectView(R.id.auVeranstaltungsnummerName)
    TextView auEventNumber;
    @InjectView(R.id.auSemesterName)
    TextView auEventSemester;
    @InjectView(R.id.auSWSName)
    TextView auSWSName;
    @InjectView(R.id.auZugeordnetePersonenName)
    TextView auLecturerName;
    @InjectView(R.id.auEventDescriptionName)
    TextView auEventDescriptionName;
    @InjectView(R.id.progressBar)
    ProgressBar spinner;
    Realm realmUI;
    AUFacultyEvent auFacultyEvent;
    @InjectView(R.id.auEventScheduleRecyclerView)
    RecyclerView auEventScheduleRecyclerView;
    AUEventScheduleItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auevent_item_details);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String eventURL = (String) intent.getExtras().getCharSequence(AUItem.AU_ITEM_URL_TAG);
        String eventName = (String) intent.getExtras().getCharSequence(AUItem.AU_ITEM_NAME_TAG);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(eventName);
        spinner.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.schedule_tab_color), PorterDuff.Mode.MULTIPLY);
        spinner.setVisibility(View.VISIBLE);
        realmUI = Realm.getDefaultInstance();
        AUEventContentRequestService.of(realmUI, eventURL)
                .notifyContentOnCacheUpdate(this::onSuccess, EVENT_URL, eventURL)
                .requestNewContent()
                .subscribe(this::onSuccess, this::onError);
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<AUFacultyEvent> auFacultyEvents) {
        if (auFacultyEvents == null || auFacultyEvents.isEmpty()) return;
        auFacultyEvent = auFacultyEvents.get(0);
        auEventName.setText(auFacultyEvent.getEventName());
        auEventNumber.setText(auFacultyEvent.getEventNumber());
        auEventType.setText(auFacultyEvent.getEventType());
        auEventSemester.setText(auFacultyEvent.getEventSemester());
        auSWSName.setText(auFacultyEvent.getEventSWS());
        auLecturerName.setText(auFacultyEvent.getEventLecturer());
        auEventDescriptionName.setText(auFacultyEvent.getEventDescription());
        spinner.setVisibility(View.GONE);
        adapter = new AUEventScheduleItemRecyclerViewAdapter(auFacultyEvent.getAuEventScheduleList());
        auEventScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        auEventScheduleRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realmUI != null) {
            realmUI.close();
            realmUI = null;
        }
    }
}
