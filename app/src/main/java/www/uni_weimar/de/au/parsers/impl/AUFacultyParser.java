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

public class AUFacultyParser implements AUParser<AUFacultyHeader> {

    private final static int AUFacultyNameLevel = 1;
    private String auFacultyHeaderUrl;

    AUFacultyParser(String url) {
        this.auFacultyHeaderUrl = url;
    }

    public static AUFacultyParser of(String url) {
        checkNotNull(url);
        return new AUFacultyParser(url);
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
        return parseAUFacultyHeaders(url, escapeHeaderTag, null);
    }

    protected RealmList<AUFacultyHeader> parseAUFacultyHeaders(String auFacultyURL, int escapeHeaderTag, AUFacultyHeader topLevelHeader) throws AUParseException {
        checkNotNull(auFacultyURL);
        RealmList<AUFacultyHeader> auFaculties = new RealmList<>();
        Document htmlDoc;
        Elements htmlTags;
        int countTags = 0;
        int autype = escapeHeaderTag;
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
                RealmList innerAUFacultyHead = parseAUFacultyHeaders(href, ++escapeHeaderTag, auFacultyHeader);
                auFacultyHeader.setAuFacultyHeaderLis(innerAUFacultyHead);
                auFacultyHeader.setTopLevelHeader(topLevelHeader);

                if (autype == AUFacultyNameLevel) {
                    auFacultyHeader.setAUFacultyType(AUItem.FACULTY);
                } else {
                    auFacultyHeader.setAUFacultyType(AUItem.AUTYPE);
                }

                auFaculties.add(auFacultyHeader);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }
        return auFaculties;
    }


    @Override
    public List<AUFacultyHeader> parseAU() throws AUParseException {
        return parseAU(auFacultyHeaderUrl);
    }
}
