package www.uni_weimar.de.au.utils.config;

import android.content.Context;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaMenuParser;
import www.uni_weimar.de.au.utils.AUFragmentFactory;
import www.uni_weimar.de.au.utils.AUParserFactory;
import www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory;
import www.uni_weimar.de.au.view.fragments.AUAllLMUNewsFeedFragment;
import www.uni_weimar.de.au.view.fragments.AUAllNewsFeedFragment;

import static www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory.getDefaultLink;

/**
 * Created by user on 23.10.17.
 */

public class LMUConfiguration extends IConfiguration {
    @Override
    public String getConfigFileURL() {
        return "https://www.augmenteduniversity.de/menu_lmu.xml";
    }

    @Override
    protected void configDefaultFragments(Context context) {
        AUFragmentFactory.addFragment(AUAllNewsFeedFragment.class, AUAllLMUNewsFeedFragment.newInstance());
    }

    @Override
    protected void configDefaultParsers(Context context) {
        AUParserFactory.addParserClass(AUCafeteriaMenuParser.class, AUCafeteriaMenuParser.class);
    }


    protected void configDefaultLinks(Context context) {
        getDefaultLink(R.string.MAIN_MENU, getConfigFileURL());
        getDefaultLink(R.string.DEFAULT_COURSES_URL, context.getString(R.string.LMU_COURSES_URL));
        getDefaultLink(R.string.AU_FACULTY_TOP_HEADER, context.getString(R.string.LMU_FACULTY_TOP_HEADER));
        getDefaultLink(R.string.DEFAULT_CAFETERIA_URL, context.getString(R.string.DEFAULT_CAFETERIA_URL));
        AUUtilityDefaultLinksFactory.getDefaultResource(R.drawable.loader, R.drawable.lmu_loader);
    }

    @Override
    public String toString() {
        return "LMUConfiguration";
    }
}
