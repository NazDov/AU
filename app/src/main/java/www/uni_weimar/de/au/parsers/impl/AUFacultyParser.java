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
        checkNotNullUrl(url);
        Document document;
        Elements elements;
        List<AUFaculty> auFaculties = new ArrayList<>();
        try {
            document = Jsoup.connect(url).get();
            elements = document.getElementsByClass("ueb");
            for (Element element : elements) {
                AUFaculty auFaculty = new AUFaculty();
                String auFacultyTitle = element.attr(AUItem.TITLE);
                Log.v(TAG, auFacultyTitle);
                auFaculty.setTitle(auFacultyTitle);
                String auFacultyURL = element.attr(AUItem.HREF);
                Log.v(TAG, auFacultyURL);
                auFaculty.setUrl(auFacultyURL);
                RealmList<AUFacultyHeader> auFacultyHeaders = parseAUFacultyHeaders(auFacultyURL);
                auFaculties.add(auFaculty);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }

        return auFaculties;
    }

    protected RealmList<AUFacultyHeader> parseAUFacultyHeaders(String auFacultyURL) {



        return null;
    }

    private void checkNotNullUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("URL is not defined");
        }
    }

    @Override
    public List<AUFaculty> parseAU() {
        return null;
    }
}
