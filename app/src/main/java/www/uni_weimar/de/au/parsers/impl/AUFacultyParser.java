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

    private final static int AU_FACULTY_NAME_TAG = 1;
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
        url = checkNotNullUrl(url);
        /**
         * escape the header tag(s)
         * value should be incremented on every recursion
         */
        int escapeHeaderTag = 1;
        return parseAUFacultyHeaders(url, escapeHeaderTag, null);
    }


    public List<AUFacultyHeader> parseAU(String url, boolean isRecursive) throws AUParseException {
        return isRecursive ? parseAU(url) : null;
    }

    public List<AUFacultyHeader> parseAU(String url,
                                         int escapeHeaderTag,
                                         AUFacultyHeader topLevelHeader) throws AUParseException {
        url = checkNotNullUrl(url);
        RealmList<AUFacultyHeader> auFaculties = new RealmList<>();
        Document htmlDoc;
        Elements uebHtmlTags;
        int htmlTagPos = 0;
        try {
            htmlDoc = Jsoup.connect(url).get();
            if (escapeHeaderTag != 1) getAUFacultyEvents(topLevelHeader, auFaculties, htmlDoc);
            getAUFacultyHeaders(escapeHeaderTag, topLevelHeader, auFaculties, htmlDoc, htmlTagPos);
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }

        return auFaculties;
    }

    private void getAUFacultyHeaders(int escapeHeaderTag, AUFacultyHeader topLevelHeader, RealmList<AUFacultyHeader> auFaculties, Document htmlDoc, int htmlTagPos) {
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
            auFacultyHeader.setAUFacultyType((escapeHeaderTag == AU_FACULTY_NAME_TAG) ? AUItem.FACULTY : AUItem.AUTYPE);
            auFaculties.add(auFacultyHeader);
        }
        if (topLevelHeader != null) {
            topLevelHeader.setAuFacultyHeaderLis(auFaculties);
        }
    }

    @NonNull
    private String checkNotNullUrl(String url) {
        if (url == null) {
            url = auFacultyHeaderUrl;
            checkNotNull(url);
        }
        return url;
    }

    private RealmList<AUFacultyHeader> parseAUFacultyHeaders(String auFacultyURL, int escapeHeaderTag, AUFacultyHeader topLevelHeader) throws AUParseException {
        RealmList<AUFacultyHeader> auFaculties = new RealmList<>();
        Document htmlDoc;
        try {
            htmlDoc = Jsoup.connect(auFacultyURL).get();
            if (escapeHeaderTag != 1) getAUFacultyEvents(topLevelHeader, auFaculties, htmlDoc);
            getAUFacultyHeaders(escapeHeaderTag,
                    topLevelHeader,
                    auFaculties,
                    htmlDoc);
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }
        return auFaculties;
    }

    private void getAUFacultyEvents(AUFacultyHeader topLevelHeader, RealmList<AUFacultyHeader> auFaculties,
                                    Document htmlDoc) {
        Elements eventsHtmlTags;
        int escapeTags = 0;
        eventsHtmlTags = htmlDoc.getElementsByClass(AUItem.EVENT);
        for (Element eventHtmlTag : eventsHtmlTags) {
            if (++escapeTags <= 3) continue;
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

    private void getAUFacultyHeaders(int escapeHeaderTag,
                                     AUFacultyHeader topLevelHeader,
                                     RealmList<AUFacultyHeader> auFaculties,
                                     Document htmlDoc) throws AUParseException {
        Elements uebHtmlTags;
        int htmlTagPos = 0;
        uebHtmlTags = htmlDoc.getElementsByClass(AUItem.UEB);
        for (Element uebTag : uebHtmlTags) {
            if (++htmlTagPos <= escapeHeaderTag) {
                continue;
            }
            String title = uebTag.attr(AUItem.TITLE);
            String href = uebTag.attr(AUItem.HREF);
            AUFacultyHeader auFacultyHeader = new AUFacultyHeader();
            auFacultyHeader.setTitle(title);
            auFacultyHeader.setUrl(href);
            int nextEscapeHeaderTag = escapeHeaderTag + 1;
            RealmList<AUFacultyHeader> innerAUFacultyHead = parseAUFacultyHeaders(href, nextEscapeHeaderTag, auFacultyHeader);
            auFacultyHeader.setAuFacultyHeaderLis(innerAUFacultyHead);
            auFacultyHeader.setTopLevelHeader(topLevelHeader);
            auFacultyHeader.setAUFacultyType((escapeHeaderTag == AU_FACULTY_NAME_TAG) ? AUItem.FACULTY : AUItem.AUTYPE);
            auFaculties.add(auFacultyHeader);
        }
    }


    @Override
    public List<AUFacultyHeader> parseAU() throws AUParseException {
        return parseAU(auFacultyHeaderUrl);
    }


}
