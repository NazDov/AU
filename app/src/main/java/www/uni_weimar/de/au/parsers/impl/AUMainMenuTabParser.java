package www.uni_weimar.de.au.parsers.impl;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 12.06.17.
 */

public class AUMainMenuTabParser implements AUParser<AUMainMenuTab> {

    private static final String AUTYPE = "autype";
    private static final String ALL_NEWS = "All News";
    private String mainMenuURL;

    private AUMainMenuTabParser(String mainMenuURL) {
        this.mainMenuURL = mainMenuURL;
    }

    public static AUMainMenuTabParser of(String url) {
        checkNotNull(url);
        return new AUMainMenuTabParser(url);
    }

    @Override
    public List<AUMainMenuTab> parseAU(String url) throws AUParseException {
        List<AUMainMenuTab> auMainMenuTabList = new ArrayList<>();
        String allNewsUrl = "";
        if (url == null) {
            checkNotNull(this.mainMenuURL);
            url= this.mainMenuURL;
        }
        Document document;
        try {
            document = Jsoup.connect(url).get();
            Elements tabs = document.getElementsByAttributeValue(AUTYPE, AUMainMenuTab.AUTYPE);
            for (Element tab : tabs) {
                String tabTitle = tab.attr(AUItem.TITLE);
                Elements tabItems = tab.children();
                RealmList<AUMainMenuItem> mainMenuTabItems = new RealmList<>();
                for (Element tabItem : tabItems) {
                    String tabItemTitle = tabItem.attr(AUItem.TITLE);
                    String tabItemUrl = tabItem.attr(AUItem.URL);
                    AUMainMenuItem auMainMenuItem = new AUMainMenuItem();
                    auMainMenuItem.setTitle(tabItemTitle);
                    auMainMenuItem.setUrl(tabItemTitle.equals(ALL_NEWS) ? allNewsUrl : tabItemUrl);
                    mainMenuTabItems.add(auMainMenuItem);
                }
                AUMainMenuTab auMainMenuTab = AUMainMenuTab.of(tabTitle, mainMenuTabItems);
                auMainMenuTabList.add(auMainMenuTab);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }


        return auMainMenuTabList;

    }

    @Override
    public List<AUMainMenuTab> parseAU() throws AUParseException {
        return parseAU(mainMenuURL);
    }
}
