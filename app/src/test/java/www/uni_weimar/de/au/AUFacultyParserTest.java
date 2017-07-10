package www.uni_weimar.de.au;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import www.uni_weimar.de.au.models.AUFaculty;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUFacultyParser;

import static junit.framework.Assert.assertSame;

/**
 * Created by ndovhuy on 07.07.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class, constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(Jsoup.class)
public class AUFacultyParserTest {

    private AUFacultyParser auFacultyParser;


    @Test
    public void testAUFacultyParser() {
        auFacultyParser = new AUFacultyParser();
//        String url = "https://www.uni-weimar.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=20648&P.vx=kurz";
//        List<AUFaculty> auFacultyList = null;
//        try {
//            auFacultyList = auFacultyParser.parseAU(url);
//        } catch (AUParseException e) {
//            e.printStackTrace();
//        }


    }


}
