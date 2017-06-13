package www.uni_weimar.de.au.application;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by nazar on 12.06.17.
 */

public class AUApplicationConfiguration extends Application {

    private static String TAG = "AUConfiguration";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "application started....");
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Log.i(TAG, "realm database configured");

    }
}
