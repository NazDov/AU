package www.uni_weimar.de.au.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import www.uni_weimar.de.au.MainActivity;

/**
 * Created by nazar on 12.06.17.
 */

public class AUApplicationConfiguration extends Application {

    private static String TAG = "AUConfiguration";
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "application started....");
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Log.i(TAG, "realm database configured");

    }
    public static void setContext(Activity context) {
        AUApplicationConfiguration.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
