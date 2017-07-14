package www.uni_weimar.de.au.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import www.uni_weimar.de.au.utils.SSLUtilities;
import www.uni_weimar.de.au.view.activity.AUMainMenuActivity;

import static com.google.common.base.Preconditions.checkNotNull;

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
        configRealmDatabase();
        Log.i(TAG, "realm database configured");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+2:00"));
        SSLUtilities.trustAllCerts();
    }


    public static boolean hasInternetConnection(Context context) {
        checkNotNull(context);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public void configRealmDatabase() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static void setContext(Activity context) {
        AUApplicationConfiguration.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
