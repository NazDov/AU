package www.uni_weimar.de.au.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.service.impl.AUMainMenuContentRequestService;

import static www.uni_weimar.de.au.application.AUApplicationConfiguration.hasInternetConnection;

/**
 * Created by ndovhuy on 26.06.2017.
 */

public class AUInitActivity extends AppCompatActivity {

    private AUMainMenuContentRequestService auMainMenuContentRequestService;
    private Observable<List<AUMainMenuTab>> observable;
    private Disposable disposable;
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.au_screen_loader);
        initSystemMainMenuComponents(this);
    }

    public void initSystemMainMenuComponents(Activity activity) {
        if (!hasInternetConnection(activity)) {
            callAUMainMenuActivity();
            return;
        }
        realm = Realm.getDefaultInstance();
        auMainMenuContentRequestService = AUMainMenuContentRequestService.of(realm, getResources().getString(R.string.MAIN_MENU));
        observable = auMainMenuContentRequestService.requestContent();
        disposable = observable.subscribe(this::onSuccess, this::onError);
    }

    private void onError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<AUMainMenuTab> auMainMenuTabs) {
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
        if (realm != null) {
            realm.close();
            realm = null;
        }
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
