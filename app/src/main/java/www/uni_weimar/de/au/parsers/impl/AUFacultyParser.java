package www.uni_weimar.de.au.parsers.impl;

import android.support.annotation.NonNull;

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

    private static final int FIRST_LEVEL_TAG = 1;
    private static final String VERANSTALTUNG_SELECTOR = "a[href$='veranstaltung']";
    private String url;

    private AUFacultyParser(String url) {
        this.url = url;
    }


    public static AUFacultyParser of(String url) {
        checkNotNull(url);
        return new AUFacultyParser(url);
    }


    @Override
    public List<AUFacultyHeader> parseAU(String url) throws AUParseException {
        url = checkNotNullUrl(url);
        /**
         * escape the header tag(s)
         * value should be incremented on every recursion
         */
        int escapeHeaderTag = FIRST_LEVEL_TAG;
        return parseAU(url, escapeHeaderTag, null);
    }


    public List<AUFacultyHeader> parseAU(String url,
                                         int escapeHeaderTag,
                                         AUFacultyHeader topLevelHeader) throws AUParseException {
        url = checkNotNullUrl(url);
        RealmList<AUFacultyHeader> auFaculties = new RealmList<>();
        Document htmlDoc;
        int htmlTagPos = 0;
        try {
            htmlDoc = Jsoup.connect(url).get();
            if (escapeHeaderTag != FIRST_LEVEL_TAG) {
                parseAUFacultyEventHeaders(topLevelHeader, auFaculties, htmlDoc);
            }
            parseAUFacultyHeaders(escapeHeaderTag, topLevelHeader, auFaculties, htmlDoc, htmlTagPos);
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }

        return auFaculties;
    }

    private void parseAUFacultyEventHeaders(AUFacultyHeader topLevelHeader, RealmList<AUFacultyHeader> auFaculties,
                                            Document htmlDoc) {
        Elements eventsHtmlTags;
        eventsHtmlTags = htmlDoc.select(VERANSTALTUNG_SELECTOR);
        for (Element eventHtmlTag : eventsHtmlTags) {
            String title = eventHtmlTag.attr(AUItem.TITLE);
            String href = eventHtmlTag.attr(AUItem.HREF);
            AUFacultyHeader auFacultyHeader = new AUFacultyHeader();
            auFacultyHeader.setTitle(title);
            auFacultyHeader.setUrl(href);
            auFacultyHeader.setTopLevelHeader(topLevelHeader);
            auFacultyHeader.setAUFacultyType(AUItem.EVENT);
            auFaculties.add(auFacultyHeader);
        }
    }

    private void parseAUFacultyHeaders(int escapeHeaderTag, AUFacultyHeader topLevelHeader, RealmList<AUFacultyHeader> auFaculties, Document htmlDoc, int htmlTagPos) {
        Elements uebHtmlTags;
        uebHtmlTags = htmlDoc.getElementsByClass(AUItem.UEB);
        for (Element uebHtmlTag : uebHtmlTags) {
            if (++htmlTagPos <= escapeHeaderTag) {
                continue;
            }
            String title = uebHtmlTag.attr(AUItem.TITLE);
            String href = uebHtmlTag.attr(AUItem.HREF);
            AUFacultyHeader auFacultyHeader = new AUFacultyHeader();
            auFacultyHeader.setTitle(title);
            auFacultyHeader.setUrl(href);
            auFacultyHeader.setHeaderLevel(escapeHeaderTag);
            auFacultyHeader.setTopLevelHeader(topLevelHeader);
            auFacultyHeader.setTopLevelHeaderName(topLevelHeader != null ? topLevelHeader.getTitle() : null);
            auFacultyHeader.setAUFacultyType((escapeHeaderTag == FIRST_LEVEL_TAG) ? AUItem.FACULTY : AUItem.AUTYPE);
            auFaculties.add(auFacultyHeader);
        }
        if (topLevelHeader != null) {
            topLevelHeader.setAuFacultyHeaderList(auFaculties);
        }
    }

    @NonNull
    private String checkNotNullUrl(String url) {
        if (url == null) {
            url = this.url;
            checkNotNull(url);
        }
        return url;
    }

    @Override
    public List<AUFacultyHeader> parseAU() throws AUParseException {
        return parseAU(url);
    }


}
