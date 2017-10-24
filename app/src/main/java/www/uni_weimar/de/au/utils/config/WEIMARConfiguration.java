package www.uni_weimar.de.au.utils.config;

import android.content.Context;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaMenuParser;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaWeimarMenuParser;
import www.uni_weimar.de.au.utils.AUFragmentFactory;
import www.uni_weimar.de.au.utils.AUParserFactory;
import www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory;
import www.uni_weimar.de.au.view.fragments.AUAllNewsFeedFragment;

import static www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory.getDefaultLink;

/**
 * Created by user on 23.10.17.
 */

public class WEIMARConfiguration extends IConfiguration {

    @Override
    public String toString() {
        return "WEIMARConfiguration";
    }

    @Override
    public String getConfigFileURL() {
        return "https://www.augmenteduniversity.de/menu_weimar.xml";
    }

    @Override
    protected void configDefaultFragments(Context context) {
        AUFragmentFactory.addFragment(AUAllNewsFeedFragment.class, AUAllNewsFeedFragment.newInstance());
    }

    @Override
    protected void configDefaultParsers(Context context) {
        AUParserFactory.addParserClass(AUCafeteriaMenuParser.class, AUCafeteriaWeimarMenuParser.class);
    }

    @Override
    protected void configDefaultLinks(Context context) {
        getDefaultLink(R.string.MAIN_MENU, getConfigFileURL());
        getDefaultLink(R.string.DEFAULT_COURSES_URL, context.getString(R.string.WEIMAR_COURSES_URL));
        getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, context.getString(R.string.WEIMAR_FACULTY_TOP_HEADER));
        getDefaultLink(R.string.DEFAULT_CAFETERIA_URL, context.getString(R.string.DEFAULT_WEIMAR_CAFETERIA_URL));
        AUUtilityDefaultLinksFactory.getDefaultResource(R.drawable.loader, R.drawable.weimar_loader);
    }
}
