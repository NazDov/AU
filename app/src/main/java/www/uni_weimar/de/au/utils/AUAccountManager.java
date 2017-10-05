package www.uni_weimar.de.au.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by user on 04.10.17.
 */

public class AUAccountManager {

    private static AUAccountManager auAccountManager;
    private final Context context;

    private AUAccountManager(Context context) {
        this.context = context;

    }

    public static AUAccountManager getInstance(Context context) {
        if (auAccountManager == null) {
            auAccountManager = new AUAccountManager(context);
        }
        return auAccountManager;
    }

    String getGoogleAccountType() {
        return "com.gmail";
    }

    String getGoogleAccountName() {
        String googleAccountName = "naz1491@gmail.com";
        Pattern gmailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account acc : accounts) {
            if (gmailPattern.matcher(acc.name).matches()) {
                googleAccountName = acc.name;
            }
        }
        return googleAccountName;
    }
}
