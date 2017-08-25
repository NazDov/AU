package www.uni_weimar.de.au.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import www.uni_weimar.de.au.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 22.08.17.
 */

public class AUUtilityUniversityPropertyReader {

    private static final String FILE_NAME = "university.properties";
    private static final java.lang.String NAME = "name";
    private static String universityName;

    private AUUtilityUniversityPropertyReader() {

    }

    public static void configureAUProperties(Context context) {
        checkNotNull(context);
        InputStream is = null;
        try {
            is = context.getAssets().open(FILE_NAME);
            Properties properties = new Properties();
            properties.load(is);
            universityName = properties.getProperty(NAME);
            auInitDefaultLinks(context);
            auInitDefaultParsers(context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) try {
                is.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }

    private static void auInitDefaultParsers(Context aucontext) {
        switch (universityName) {
            case "WEIMAR":
                AUUtilityDefaultLinksFactory.getDefaultLink(R.string.DEFAULT_COURSES_URL, aucontext.getString(R.string.WEIMAR_COURSES_URL));
                AUUtilityDefaultLinksFactory.getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, aucontext.getString(R.string.WEIMAR_FACULTY_TOP_HEADER));
                break;
            case "LMU":
                AUUtilityDefaultLinksFactory.getDefaultLink(R.string.DEFAULT_COURSES_URL, aucontext.getString(R.string.LMU_COURSES_URL));
                AUUtilityDefaultLinksFactory.getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, aucontext.getString(R.string.LMU_FACULTY_TOP_HEADER));
                break;
            default:
                AUUtilityDefaultLinksFactory.getDefaultLink(R.string.DEFAULT_COURSES_URL, aucontext.getString(R.string.WEIMAR_COURSES_URL));
                AUUtilityDefaultLinksFactory.getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, aucontext.getString(R.string.WEIMAR_FACULTY_TOP_HEADER));
                break;
        }
    }

    private static void auInitDefaultLinks(Context context) {

    }
}
