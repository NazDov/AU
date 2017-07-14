package www.uni_weimar.de.au.parsers.impl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import io.realm.RealmList;
import www.uni_weimar.de.au.AUTestApplicationConfig;
import www.uni_weimar.de.au.BuildConfig;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.parsers.exception.AUParseException;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by ndovhuy on 07.07.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class, constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(Jsoup.class)
public class AUFacultyHeaderParserTest {

    private AUFacultyParser auFacultyParser;
    private Connection connection;
    private Document document;
    private Elements elements;


    @Before
    public void setup() {
        mockStatic(Jsoup.class);
        connection = mock(Connection.class);
        document = mock(Document.class);
        elements = mock(Elements.class);
        auFacultyParser = new AUFacultyParser(null) {
            @Override
            protected RealmList<AUFacultyHeader> parseAUFacultyHeaders(String auFacultyURL, int escapeHeaderTag, AUFacultyHeader auFacultyHeader) throws AUParseException {
                RealmList<AUFacultyHeader> list = new RealmList<>();
                AUFacultyHeader auFacultyHeader = new AUFacultyHeader();
                auFacultyHeader.setTitle("Institute of Architecture");
                list.add(auFacultyHeader);
                return list;
            }
        };
    }


    @Test(expected = NullPointerException.class)
    public void testAUFacultyParserNullUrlConstructor() {
        String url = null;
        auFacultyParser = AUFacultyParser.of(url);
    }

    @Test
    public void testAUFacultyParser() throws AUParseException {
        String url = "http://url.net";
        //when
        List<AUFacultyHeader> actualAUFacultyHeaderList = auFacultyParser.parseAU(url);
        //then
        assertEquals("Institute of Architecture", actualAUFacultyHeaderList.get(0).getTitle());
    }

    @Test
    public void testAUFacultyHeaderParse() throws AUParseException {
        String url = "https://www.uni-weimar.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=20648&P.vx=kurz";
        AUFacultyParser auFacultyParser = AUFacultyParser.of(url);
        auFacultyParser.parseAU();
    }


}
