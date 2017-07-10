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
import www.uni_weimar.de.au.models.AUFaculty;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 07.07.2017.
 */

public class AUFacultyParser implements AUParser<AUFaculty> {

    private static final String TAG = AUFacultyParser.class.getSimpleName();
    private String facultyHeaderUrl;

    public AUFacultyParser() {

    }

    public AUFacultyParser(String url) {
        this.facultyHeaderUrl = url;
    }

    @Override
    public List<AUFaculty> parseAU(String url) throws AUParseException {
        checkNotNull(url);
        Document htmlDoc;
        Elements htmlTags;
        List<AUFaculty> auFaculties = new ArrayList<>();
        try {
            htmlDoc = Jsoup.connect(url).get();
            htmlTags = htmlDoc.getElementsByClass("ueb");
            for (Element htmlTag : htmlTags) {
                String auFacultyTitle = htmlTag.attr(AUItem.TITLE);
                if (auFacultyTitle.contains("Veranstaltungs")) {
                    continue;
                }
                Log.v(TAG, auFacultyTitle);
                AUFaculty auFaculty = new AUFaculty();
                auFaculty.setTitle(auFacultyTitle);
                String auFacultyURL = htmlTag.attr(AUItem.HREF);
                Log.v(TAG, auFacultyURL);
                auFaculty.setUrl(auFacultyURL);
                int escapeTags = 2;
                RealmList<AUFacultyHeader> auFacultyHeaders = parseAUFacultyHeaders(auFacultyURL, escapeTags);
                auFaculty.setAuFacultyHeaders(auFacultyHeaders);
                auFaculties.add(auFaculty);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }

        return auFaculties;
    }

    public RealmList<AUFacultyHeader> parseAUFacultyHeaders(String auFacultyURL, int escapeTags) throws AUParseException {
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
                if (countTags <= escapeTags) {
                    continue;
                }
                String title = htmlTag.attr(AUItem.TITLE);
                String href = htmlTag.attr(AUItem.HREF);
                AUFacultyHeader auFacultyHeader = new AUFacultyHeader();
                auFacultyHeader.setTitle(title);
                auFacultyHeader.setUrl(href);
                RealmList innerAUFacultyHead = parseAUFacultyHeaders(href, ++escapeTags);
                auFacultyHeader.setAuFacultyHeaderLis(innerAUFacultyHead);
                auFacultyHeaders.add(auFacultyHeader);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }


        return auFacultyHeaders;
    }


    @Override
    public List<AUFaculty> parseAU() {
        return null;
    }
}
