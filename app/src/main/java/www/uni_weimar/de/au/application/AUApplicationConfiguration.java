package www.uni_weimar.de.au.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.util.List;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.orm.AUMainMenuTabORM;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;

/**
 * Created by nazar on 12.06.17.
 */

public class AUApplicationConfiguration extends Application {

    private static String TAG = "AUConfiguration";
    private static Context context;
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "application started....");
        configRealmDatabase();
        Log.i(TAG, "realm database configured");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+2:00"));

    }

    private void configRealmDatabase() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();
        new AsyncTask<Object, Void, Object>() {

            @Override
            protected Object doInBackground(Object... params) {
                List<AUMainMenuTab> auMainMenuTabList;
                AUMainMenuTabORM auMainMenuTabORM = new AUMainMenuTabORM(realm);
                AUMainMenuTabParser auMainMenuTabParser = new AUMainMenuTabParser();
                try {
                    auMainMenuTabList = auMainMenuTabParser.parseAllAU("http://ec2-35-157-27-16.eu-central-1.compute.amazonaws.com/menu.xml");
                    auMainMenuTabORM.addAll(auMainMenuTabList);
                } catch (AUParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

    }

    public static void setContext(Activity context) {
        AUApplicationConfiguration.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
