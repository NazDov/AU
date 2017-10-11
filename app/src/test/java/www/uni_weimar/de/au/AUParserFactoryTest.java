package www.uni_weimar.de.au;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import www.uni_weimar.de.au.parsers.impl.AUCafeteriaMenuParser;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaWeimarMenuParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.utils.AUParserFactory;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by user on 06.10.17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class, constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
public class AUParserFactoryTest {

    AUParserFactoryWrapper auParserFactoryWrapper = new AUParserFactoryWrapper();

    class AUParserFactoryWrapper {

        public void addParser(Class tag, Class instance) {
            AUParserFactory.addParserClass(tag, instance);
        }

        public AUParser newParser(Class tag, String url) {
            return AUParserFactory.newParser(tag, url);
        }

    }

    @Before
    public void init() {
        auParserFactoryWrapper.addParser(AUCafeteriaMenuParser.class, AUCafeteriaWeimarMenuParser.class);
    }

    @Test
    public void testCreateNewInstance() {
        AUCafeteriaWeimarMenuParser cafeteriaWeimarMenuParser = (AUCafeteriaWeimarMenuParser)
                auParserFactoryWrapper.newParser(AUCafeteriaMenuParser.class, "http://");
        assertTrue(cafeteriaWeimarMenuParser != null);
    }

}
