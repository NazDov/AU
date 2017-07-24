package www.uni_weimar.de.au.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

public class AUEventItemDetailsActivity extends AppCompatActivity {

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
    @InjectView(R.id.au_event_description_recycler_view)
    RecyclerView auEventRecyclerView;
    AUEventContentRequestService auEventContentRequestService;
    Realm realmUI;
    AUFacultyEvent auFacultyEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auevent_item_details);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String auItemURL = (String) intent.getExtras().getCharSequence(AUItem.AU_ITEM_URL_TAG);
        String auItemName = (String) intent.getExtras().getCharSequence(AUItem.AU_ITEM_NAME_TAG);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(auItemName);
        realmUI = Realm.getDefaultInstance();
        auEventContentRequestService = AUEventContentRequestService.of(realmUI, auItemURL);
        auEventContentRequestService
                .requestContent()
                .subscribe(this::onSuccess, this::onError);
        auEventContentRequestService.notifyContentOnCacheUpdate(this::onSuccess);
    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<AUFacultyEvent> auFacultyEvents) {
        if (auFacultyEvents == null || auFacultyEvents.isEmpty())
            return;
        auFacultyEvent = auFacultyEvents.get(0);
        auEventName.setText(auFacultyEvent.getEventName());
        auEventNumber.setText(auFacultyEvent.getEventNumber());
        auEventType.setText(auFacultyEvent.getEventType());
        auEventSemester.setText(auFacultyEvent.getEventSemester());
        auSWSName.setText(auFacultyEvent.getEventSWS());
        auLecturerName.setText(auFacultyEvent.getEventLecturer());
        auEventDescriptionName.setText(auFacultyEvent.getEventDescription());
    }
}
