package www.uni_weimar.de.au.parsers.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;
import io.realm.RealmList;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 07.07.2017.
 */

public class AUFacultyHeaderParser implements AUParser<AUFacultyHeader> {

    private String auFacultyHeaderUrl;

    AUFacultyHeaderParser(String url) {
        this.auFacultyHeaderUrl = url;
    }

    public static AUFacultyHeaderParser of(String url) {
        checkNotNull(url);
        return new AUFacultyHeaderParser(url);
    }

    @Override
    public List<AUFacultyHeader> parseAU(String url) throws AUParseException {
        if (url == null) {
            url = auFacultyHeaderUrl;
            checkNotNull(url);
        }
        /**
         * escape the header tag(s)
         * value should be incremented on every recursion
         */
        int escapeHeaderTag = 1;
        return parseAUFacultyHeaders(url, escapeHeaderTag);
    }

    protected RealmList<AUFacultyHeader> parseAUFacultyHeaders(String auFacultyURL, int escapeHeaderTag) throws AUParseException {
        checkNotNull(auFacultyURL);
        RealmList<AUFacultyHeader> auFacultyHeaders = new RealmList<>();
        Document htmlDoc;
        Elements htmlTags;
        int countTags = 0;
        try {
            htmlDoc = Jsoup.connect(auFacultyURL).get();
            htmlTags = htmlDoc.getElementsByClass("ueb");
            for (Element htmlTag : htmlTags) {
                countTags++;
                if (countTags <= escapeHeaderTag) {
                    continue;
                }
                String title = htmlTag.attr(AUItem.TITLE);
                String href = htmlTag.attr(AUItem.HREF);
                AUFacultyHeader auFacultyHeader = new AUFacultyHeader();
                auFacultyHeader.setTitle(title);
                auFacultyHeader.setUrl(href);
                RealmList innerAUFacultyHead = parseAUFacultyHeaders(href, ++escapeHeaderTag);
                auFacultyHeader.setAuFacultyHeaderLis(innerAUFacultyHead);
                auFacultyHeaders.add(auFacultyHeader);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }
        return auFacultyHeaders;
    }


    @Override
    public List<AUFacultyHeader> parseAU() throws AUParseException {
        return parseAU(auFacultyHeaderUrl);
    }
}
