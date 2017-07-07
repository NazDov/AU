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
import org.mockito.Mock;
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
import java.util.ListIterator;

import io.realm.RealmList;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;
import www.uni_weimar.de.au.parsers.impl.AUNewsFeedParser;

import static junit.framework.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Created by ndovhuy on 07.07.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = AUTestApplicationConfig.class, constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(Jsoup.class)
public class AUMainMenuTabParserTest {

    private Connection connection;
    private Document document;
    private Elements elements;
    private Element element;
    private ListIterator<Element> iterator;
    private AUMainMenuTabParser auMainMenuTabParser;
    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();


    @Before
    public void setup() {
        mockStatic(Jsoup.class);
        connection= mock(Connection.class);
        document = mock(Document.class);
        elements = mock(Elements.class);
        element = mock(Element.class);
        iterator = mock(ListIterator.class);
        auMainMenuTabParser = new AUMainMenuTabParser();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = AUParseException.class)
    public void testVerifyThrowParseException() throws AUParseException {
        //given
        String url = "http://someurl.com";
        when(Jsoup.connect(url)).thenThrow(AUParseException.class);
        auMainMenuTabParser.parseAU(url);
        verifyStatic();
    }

    @Test
    public void testAUMainMenuTabParser() throws IOException, AUParseException {
        String url = "http://smth.com";
        //Given
        when(Jsoup.connect(url)).thenReturn(connection);
        when(connection.get()).thenReturn(document);
        when(document.getElementsByAttributeValue("autype", AUMainMenuTab.AUTYPE)).thenReturn(elements);
        when(elements.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(element);
        String title = "News";
        when(element.attr(AUItem.TITLE)).thenReturn(title);
        AUMainMenuTab auMainMenuTab = new AUMainMenuTab();
        auMainMenuTab.setTitle(title);
        Elements tabChildren = mock(Elements.class);
        when(element.children()).thenReturn(tabChildren);
        Iterator<Element> tabChildrenIterator = mock(Iterator.class);
        when(tabChildren.iterator()).thenReturn(tabChildrenIterator);
        when(tabChildrenIterator.hasNext()).thenReturn(true, false);
        Element childTabElement = mock(Element.class);
        when(tabChildrenIterator.next()).thenReturn(childTabElement);
        String childTabTitle = "My News";
        when(childTabElement.attr(AUItem.TITLE)).thenReturn(childTabTitle);
        String childTabUrl = "http://smth.com";
        when(childTabElement.attr(AUItem.URL)).thenReturn(childTabUrl);
        AUMainMenuItem auMainMenuItem = new AUMainMenuItem();
        auMainMenuItem.setTitle(childTabTitle);
        auMainMenuItem.setUrl(childTabUrl);
        RealmList<AUMainMenuItem> auMainMenuItems = new RealmList<>();
        auMainMenuItems.add(auMainMenuItem);
        auMainMenuTab.setAUMainMenuItemList(auMainMenuItems);
        List<AUMainMenuTab> expAUMAinMenuTab = new ArrayList<>();
        expAUMAinMenuTab.add(auMainMenuTab);
        //when
        List<AUMainMenuTab> actualAuMainMenuTabList = auMainMenuTabParser.parseAU(url);
        //then
        assertEquals(expAUMAinMenuTab.size(), actualAuMainMenuTabList.size());
        assertEquals(expAUMAinMenuTab.get(0).getTitle(), actualAuMainMenuTabList.get(0).getTitle());
        assertNotNull(actualAuMainMenuTabList);
    }

}
