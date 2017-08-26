package www.uni_weimar.de.au.parsers.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import www.uni_weimar.de.au.models.AUCafeteria;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 26.08.2017.
 */

public class AUCafeteriaListParser implements AUParser<AUCafeteria> {

    private static final String CAFETERIA_LIST_TAG = "js-speiseplaene-liste";
    private static final String LI_STANDORT = "li.standort";
    private final String url;

    private AUCafeteriaListParser(String url) {
        this.url = url;
    }

    public static AUCafeteriaListParser of(String url) {
        checkNotNull(url);
        return new AUCafeteriaListParser(url);
    }


    @Override
    public List<AUCafeteria> parseAU(String url) throws AUParseException {
        List<AUCafeteria> auCafeterias = new ArrayList<>();
        Document htmlDoc;
        Elements cafeteriaNodes;
        try {
            htmlDoc = Jsoup.connect(url).get();
            cafeteriaNodes = htmlDoc.select(LI_STANDORT);
            for (Element cafeteriaNode : cafeteriaNodes) {
                String cafeteriaLink = cafeteriaNode.getElementsByTag("a").get(0).attr("href");
                String cafeteriaName = cafeteriaNode.text();
                AUCafeteria auCafeteria = new AUCafeteria();
                auCafeteria.setName(cafeteriaName);
                auCafeteria.setUrl(cafeteriaLink);
                auCafeterias.add(auCafeteria);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }
        return auCafeterias;
    }

    @Override
    public List<AUCafeteria> parseAU() throws AUParseException {
        return parseAU(this.url);
    }
}
