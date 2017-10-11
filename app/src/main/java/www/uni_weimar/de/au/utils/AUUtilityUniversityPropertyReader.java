package www.uni_weimar.de.au.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaMenuParser;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaWeimarMenuParser;
import www.uni_weimar.de.au.parsers.impl.AUNewsFeedParser;
import www.uni_weimar.de.au.view.fragments.AUAllLMUNewsFeedFragment;
import www.uni_weimar.de.au.view.fragments.AUAllNewsFeedFragment;

import static com.google.common.base.Preconditions.checkNotNull;
import static www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory.getDefaultLink;

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
            auInitDefaultFragments();
            auInitDefaultParsers();
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

    private static void auInitDefaultParsers() {
        switch (universityName){
            case "WEIMAR":
                AUParserFactory.addParserClass(AUCafeteriaMenuParser.class, AUCafeteriaWeimarMenuParser.class);
                break;
            case "LMU":
                AUParserFactory.addParserClass(AUCafeteriaMenuParser.class, AUCafeteriaMenuParser.class);
                break;
        }
    }

    private static void auInitDefaultFragments() {
        switch (universityName){
            case "WEIMAR":
                AUFragmentFactory.addFragment(AUAllNewsFeedFragment.class, AUAllNewsFeedFragment.newInstance());
                break;
            case "LMU":
                AUFragmentFactory.addFragment(AUAllNewsFeedFragment.class, AUAllLMUNewsFeedFragment.newInstance());
                break;
        }
    }

    private static void auInitDefaultLinks(Context aucontext) {
        switch (universityName) {
            case "WEIMAR":
                getDefaultLink(R.string.MAIN_MENU, "https://www.augmenteduniversity.de/menu_weimar.xml");
                getDefaultLink(R.string.DEFAULT_COURSES_URL, aucontext.getString(R.string.WEIMAR_COURSES_URL));
                getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, aucontext.getString(R.string.WEIMAR_FACULTY_TOP_HEADER));
                getDefaultLink(R.string.DEFAULT_CAFETERIA_URL, aucontext.getString(R.string.DEFAULT_WEIMAR_CAFETERIA_URL));
                AUUtilityDefaultLinksFactory.getDefaultResource(R.drawable.loader, R.drawable.weimar_loader);
                break;
            case "LMU":
                getDefaultLink(R.string.MAIN_MENU, "https://www.augmenteduniversity.de/menu_lmu.xml");
                getDefaultLink(R.string.DEFAULT_COURSES_URL, aucontext.getString(R.string.LMU_COURSES_URL));
                getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, aucontext.getString(R.string.LMU_FACULTY_TOP_HEADER));
                getDefaultLink(R.string.DEFAULT_CAFETERIA_URL, aucontext.getString(R.string.DEFAULT_CAFETERIA_URL));
                AUUtilityDefaultLinksFactory.getDefaultResource(R.drawable.loader, R.drawable.lmu_loader);
                break;
            default:
                getDefaultLink(R.string.DEFAULT_COURSES_URL, aucontext.getString(R.string.WEIMAR_COURSES_URL));
                getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, aucontext.getString(R.string.WEIMAR_FACULTY_TOP_HEADER));
                getDefaultLink(R.string.DEFAULT_CAFETERIA_URL, aucontext.getString(R.string.DEFAULT_CAFETERIA_URL));
                break;
        }
    }

}
