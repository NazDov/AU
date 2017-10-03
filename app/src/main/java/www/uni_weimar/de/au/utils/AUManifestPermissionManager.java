package www.uni_weimar.de.au.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by user on 30.09.17.
 */

public class AUManifestPermissionManager {

    private static final int REQUEST_EXTERNAL_CALENDAR_STORAGE = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

    interface AUPermissionSuccessOperation {
        void doPermissionSuccessOperation();
    }


    public static void doOnSuccessStoragePermissionOperation(Activity activity, AUPermissionSuccessOperation permissionSuccessOperation) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // Check if we have read or write permission
            int writeCalendarPermission = activity.checkSelfPermission(Manifest.permission.READ_CALENDAR);
            int readCalendarPermission = activity.checkSelfPermission(Manifest.permission.WRITE_CALENDAR);
            if (writeCalendarPermission != PackageManager.PERMISSION_GRANTED || readCalendarPermission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "you have no permission to access calendar!", Toast.LENGTH_SHORT).show();
            } else {
                permissionSuccessOperation.doPermissionSuccessOperation();
            }
        } else {
            permissionSuccessOperation.doPermissionSuccessOperation();
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // Check if we have read or write permission
            int writeCalendarPermission = activity.checkSelfPermission(Manifest.permission.READ_CALENDAR);
            int readCalendarPermission = activity.checkSelfPermission(Manifest.permission.WRITE_CALENDAR);

            if (writeCalendarPermission != PackageManager.PERMISSION_GRANTED || readCalendarPermission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                activity.requestPermissions(
                        PERMISSIONS,
                        REQUEST_EXTERNAL_CALENDAR_STORAGE
                );
            }
        }
    }

}
