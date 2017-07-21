package www.uni_weimar.de.au.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUItem;

public class AUEventItemDetailsActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

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
    }
}
