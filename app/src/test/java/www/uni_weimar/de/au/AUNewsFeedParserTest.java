package www.uni_weimar.de.au;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUNewsFeedParser;
import static junit.framework.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by ndovhuy on 06.07.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class, constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(Jsoup.class)
public class AUNewsFeedParserTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();
    private AUNewsFeedParser auMainMenuTabParser;
    private Connection connectionMock;
    private Document mockDocument;
    private Elements mockElements;


    @Before
    public void setup() {
        mockStatic(Jsoup.class);
        auMainMenuTabParser = new AUNewsFeedParser();
        connectionMock = mock(Connection.class);
        mockDocument = mock(Document.class);
        mockElements = PowerMockito.mock(Elements.class);

    }

    @Test(expected = AUParseException.class)
    public void testParseAllAU() throws IOException, AUParseException {
        String url = "http://www.valid.com";
        when(Jsoup.connect(url)).thenThrow(AUParseException.class);
        auMainMenuTabParser.parseAU(url);
        verifyStatic();
    }

    @Test
    public void testParseAllAuWithNullUrl() throws IOException {
        String newsFeedUrl = "http://therighturl.net";
        auMainMenuTabParser.setNewsFeedUrl(newsFeedUrl);
        when(Jsoup.connect(newsFeedUrl)).thenReturn(connectionMock);
        when(connectionMock.get()).thenReturn(mockDocument);
        mockElements = new Elements(1);
        when(mockDocument.getElementsByTag(AUItem.ITEM)).thenReturn(mockElements);
        try {
            auMainMenuTabParser.parseAU(null);
        } catch (AUParseException e) {
            e.printStackTrace();
        }
        assertEquals(newsFeedUrl, auMainMenuTabParser.getNewsFeedUrl());
    }

    @Test
    public void testCorrectAUNewsFeedParsing() throws IOException {
        String newsFeedUrl = "http://therighturl.net";
        auMainMenuTabParser.setNewsFeedUrl(newsFeedUrl);
        when(Jsoup.connect(newsFeedUrl)).thenReturn(connectionMock);
        when(connectionMock.get()).thenReturn(mockDocument);
        when(mockDocument.getElementsByTag(AUItem.ITEM)).thenReturn(mockElements);
        Iterator<Element> iteratorMock = mock(Iterator.class);
        when(mockElements.iterator()).thenReturn(iteratorMock);
        when(iteratorMock.hasNext()).thenReturn(true, false);
        Element mockElement = mock(Element.class);
        when(iteratorMock.next()).thenReturn(mockElement);
        String title = "title";
        Elements titleElementsMock = mock(Elements.class);
        when(mockElement.getElementsByTag(AUItem.TITLE)).thenReturn(titleElementsMock);
        when(titleElementsMock.text()).thenReturn(title);
        String author = "author";
        Elements authorElementsMock = mock(Elements.class);
        when(mockElement.getElementsByTag(AUItem.AUTHOR)).thenReturn(authorElementsMock);
        when(authorElementsMock.text()).thenReturn(author);
        String link = "link";
        Elements linkElementsMock = mock(Elements.class);
        when(mockElement.getElementsByTag(AUItem.LINK)).thenReturn(linkElementsMock);
        when(linkElementsMock.text()).thenReturn(link);
        String descr = "descr";
        Elements descrElementsMock = mock(Elements.class);
        when(mockElement.getElementsByTag(AUItem.DESCR)).thenReturn(descrElementsMock);
        when(descrElementsMock.text()).thenReturn(descr);
        String category = "category";
        Elements categoryElementsMock = mock(Elements.class);
        when(mockElement.getElementsByTag(AUItem.CATEGORY)).thenReturn(categoryElementsMock);
        when(categoryElementsMock.text()).thenReturn(category);
        String pubDate = "pubDate";
        Elements pubDateElementsMock = mock(Elements.class);
        when(mockElement.getElementsByTag(AUItem.PUB_DATE)).thenReturn(pubDateElementsMock);
        when(pubDateElementsMock.text()).thenReturn(pubDate);
        Elements imgElements = mock(Elements.class);
        when(mockElement.getElementsByTag(AUItem.ENCLOSURE)).thenReturn(imgElements);
        String imgURL = "url";
        when(imgElements.attr(AUItem.IMG_URL)).thenReturn(imgURL);
        List<AUNewsFeed> expAuNewsFeedList = new ArrayList<>();
        AUNewsFeed auNewsFeed = new AUNewsFeed();
        auNewsFeed.setAuthor(author);
        auNewsFeed.setImgUrl(imgURL);
        auNewsFeed.setLink(link);
        auNewsFeed.setDesciption(descr);
        auNewsFeed.setTitle(title);
        auNewsFeed.setCategory(category);
        auNewsFeed.setPubDate(pubDate);
        expAuNewsFeedList.add(auNewsFeed);
        List<AUNewsFeed> actualAuNewsFeedList = null;
        try {
            actualAuNewsFeedList = auMainMenuTabParser.parseAU(newsFeedUrl);
        } catch (AUParseException e) {
            e.printStackTrace();
        }
        assertEquals(expAuNewsFeedList, actualAuNewsFeedList);
    }

}
