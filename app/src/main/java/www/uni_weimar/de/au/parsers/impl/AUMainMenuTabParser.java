package www.uni_weimar.de.au.parsers.impl;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.application.AUApplicationConfiguration;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

/**
 * Created by nazar on 12.06.17.
 */

public class AUMainMenuTabParser implements AUParser<AUMainMenuTab> {

    private Context context;

    public AUMainMenuTabParser() {
        context = AUApplicationConfiguration.getContext();
    }

    @Override
    public List<AUMainMenuTab> parseAllAU(String url) throws AUParseException {
        List<AUMainMenuTab> auMainMenuTabList = new ArrayList<>();
        String allNewsUrl = "";

        if (url == null) {
            auMainMenuTabList = parseAllAU();
        }

        Document document;
        try {
            document = Jsoup.connect(url).get();
            Elements tabs = document.getElementsByAttributeValue("autype", AUMainMenuTab.AUTYPE);
            for (Element tab : tabs) {
                String tabTitle = tab.attr(AUItem.TITLE);
                Log.v("TAB_PARENT: ", tabTitle);
                AUMainMenuTab auMainMenuTab = new AUMainMenuTab();
                auMainMenuTab.setTitle(tabTitle);
                Elements tabItems = tab.children();
                RealmList<AUMainMenuItem> mainMenuTabItems = new RealmList<>();
                for (Element tabItem : tabItems) {
                    String tabItemTitle = tabItem.attr(AUItem.TITLE);
                    String tabItemUrl = tabItem.attr(AUItem.URL);
                    Log.v("TAB_CHILD: ", tabItemUrl);
                    AUMainMenuItem auMainMenuItem = new AUMainMenuItem();
                    auMainMenuItem.setTitle(tabItemTitle);
                    auMainMenuItem.setUrl(tabItemTitle.equals("All News") ? allNewsUrl : tabItemUrl);
                    mainMenuTabItems.add(auMainMenuItem);
                }
                auMainMenuTab.setAUMainMenuItemList(mainMenuTabItems);
                auMainMenuTabList.add(auMainMenuTab);
            }
        } catch (IOException e) {
            Log.e("SYSTEM:ROOT CNX ERROR", e.getMessage());
            throw new AUParseException(e.getMessage());
        }


        return auMainMenuTabList;

    }

    @Override
    public List<AUMainMenuTab> parseAllAU() {
        List<AUMainMenuTab> list = new ArrayList<>();
        AUMainMenuTab newsTab = new AUMainMenuTab();
        newsTab.setTitle("News");
        AUMainMenuTab newsTab2 = new AUMainMenuTab();
        newsTab2.setTitle("Library");
        AUMainMenuTab newsTab3 = new AUMainMenuTab();
        newsTab3.setTitle("Courses");
        AUMainMenuTab newsTab4 = new AUMainMenuTab();
        newsTab4.setTitle("Cafeteria");
        list.add(newsTab);
        list.add(newsTab2);
        list.add(newsTab3);
        list.add(newsTab4);
        return list;
    }
}
