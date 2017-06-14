package www.uni_weimar.de.au.parsers.impl;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.parsers.inter.AUParser;

/**
 * Created by nazar on 12.06.17.
 */

public class AUMainMenuTabParser implements AUParser<AUMainMenuTab> {


    @Override
    public List<AUMainMenuTab> parseAllAU(String url) {
        List<AUMainMenuTab> list = null;

        if (url == null) {
            list = parseAllAU();
        }
//
//        Document document;
//        try {
//            document = Jsoup.connect(url).get();
//            Elements tabs = document.getElementsByAttributeValue("autype", AUMainMenuTab.AUTYPE);
//            for (Element tab : tabs) {
//                String tabTitle = tab.attr("title");
//                Log.v("TAB: ", tabTitle);
//            }
//
//
//        } catch (IOException e) {
//            Log.e("URL CONNECT ERROR", e.getMessage());
//            throw new IllegalArgumentException(e);
//        }


        return parseAllAU();

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
