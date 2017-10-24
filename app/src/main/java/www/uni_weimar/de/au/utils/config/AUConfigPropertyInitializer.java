package www.uni_weimar.de.au.utils.config;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaMenuParser;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaWeimarMenuParser;
import www.uni_weimar.de.au.utils.AUFragmentFactory;
import www.uni_weimar.de.au.utils.AUParserFactory;
import www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory;
import www.uni_weimar.de.au.view.fragments.AUAllLMUNewsFeedFragment;
import www.uni_weimar.de.au.view.fragments.AUAllNewsFeedFragment;

import static com.google.common.base.Preconditions.checkNotNull;
import static www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory.getDefaultLink;

/**
 * Created by nazar on 22.08.17.
 */

public class AUConfigPropertyInitializer {

    private static final String FILE_NAME = "university.properties";
    private static final java.lang.String NAME = "name";
    private static String universityName;
    private static AUUniversityConfigurator configurator;

    private AUConfigPropertyInitializer() {
    }


    public static void configureAUProperties(Context context) {
        context = checkNotNull(context);
        if(configurator == null) {
            configurator = new AUUniversityConfigurator();
        }
        InputStream is = null;
        try {
            is = context.getAssets().open(FILE_NAME);
            Properties properties = new Properties();
            properties.load(is);
            universityName = properties.getProperty(NAME);
            configurator.initConfiguration(context, universityName);
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
}
