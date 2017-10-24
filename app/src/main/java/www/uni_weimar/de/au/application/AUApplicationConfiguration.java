package www.uni_weimar.de.au.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import www.uni_weimar.de.au.utils.config.AUConfigPropertyInitializer;
import www.uni_weimar.de.au.utils.SSLUtilities;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 12.06.17.
 */

public class AUApplicationConfiguration extends Application {

    private static String TAG = "AUConfiguration";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "application started....");
        configRealmDatabase();
        Log.i(TAG, "realm database configured");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+2:00"));
        if (Build.VERSION.SDK_INT < LOLLIPOP) SSLUtilities.trustAllCerts();
        AUConfigPropertyInitializer.configureAUProperties(getApplicationContext());
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

}
