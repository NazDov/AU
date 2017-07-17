package www.uni_weimar.de.au.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.service.impl.AUFacultyContentRequestService;
import www.uni_weimar.de.au.service.impl.AUMainMenuContentRequestService;

import static www.uni_weimar.de.au.application.AUApplicationConfiguration.hasInternetConnection;

/**
 * Created by ndovhuy on 26.06.2017.
 */

public class AUInitActivity extends AppCompatActivity {

    @InjectView(R.id.au_init_text)
    TextView auInitTextView;
    private Disposable auFacultyDisposable;
    private Disposable auMainMenuDisposable;
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.au_screen_loader);
        ButterKnife.inject(this);
        initSystemMainMenuComponents(this);
    }

    public void initSystemMainMenuComponents(Activity activity) {
        auInitTextView.setText("Please wait...");
        realm = Realm.getDefaultInstance();
        if (!hasInternetConnection(activity) || hasCacheableData()) {
            new Handler().postDelayed(() -> {
                callAUMainMenuActivity();
            }, 3000);
            return;
        }
        auMainMenuDisposable = AUMainMenuContentRequestService
                .of(realm, getResources().getString(R.string.MAIN_MENU))
                .requestContent()
                .subscribe(this::onMainMenuLoaded, this::onError);
    }

    private boolean hasCacheableData() {
        long auMainMenuTabCount = realm.where(AUMainMenuTab.class).count();
        long auFacultyHeaderCount = realm.where(AUFacultyHeader.class).count();
        long auNewsFeedCount = realm.where(AUNewsFeed.class).count();
        return auMainMenuTabCount > 0 && auFacultyHeaderCount > 0 && auNewsFeedCount > 0;
    }

    private void onError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onMainMenuLoaded(List<AUMainMenuTab> auMainMenuTabs) {
        auInitTextView.setText("Loading components...");
        callAUMainMenuActivity();
    }

    private void onScheduleLoaded(List<AUFacultyHeader> auFacultyHeaders) {
        callAUMainMenuActivity();
    }

    private void callAUMainMenuActivity() {
        Intent intent = new Intent(this, AUMainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        if (realm != null) {
            realm.close();
            realm = null;
        }
        if (auMainMenuDisposable != null) {
            auMainMenuDisposable.dispose();
            auMainMenuDisposable = null;
        }

        if (auFacultyDisposable != null) {
            auFacultyDisposable.dispose();
            auFacultyDisposable = null;
        }
    }
}
