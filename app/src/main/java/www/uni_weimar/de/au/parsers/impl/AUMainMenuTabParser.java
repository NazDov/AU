package www.uni_weimar.de.au.parsers.impl;

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
        return list;

    }

    @Override
    public List<AUMainMenuTab> parseAllAU() {
        List<AUMainMenuTab> list = new ArrayList<>();
        AUMainMenuTab newsTab = new AUMainMenuTab();
        newsTab.setTitle("News");
        list.add(newsTab);
        return list;
    }
}
