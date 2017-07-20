package www.uni_weimar.de.au.parsers.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 20.07.17.
 */

public class AUEventParser implements AUParser<AUFacultyEvent> {

    private static final String SEMESTER = "Semester";
    private final String url;

    public AUEventParser(String url) {
        this.url = url;
    }

    public static AUEventParser of(String url) {
        checkNotNull(url);
        return new AUEventParser(url);
    }


    @Override
    public List<AUFacultyEvent> parseAU(String url) throws AUParseException {

        Document htmlDoc;
        try {
            htmlDoc = Jsoup.connect(url).get();
            new AUFacultyEvent
                    .EventBuilder()
                    .setEventSemester(getTDVal(htmlDoc, SEMESTER))
                    .setEventLecturer();
        } catch (IOException e) {
            throw new AUParseException(e.getLocalizedMessage());
        }

        return null;
    }

    private String getTDVal(Document htmlDoc, String key) {
        Elements th = htmlDoc.select("th:containsOwn(" + key + ") + td");
        return th != null && !th.isEmpty() ? th.get(0).html() : null;
    }

    @Override
    public List<AUFacultyEvent> parseAU() throws AUParseException {
        return parseAU(url);
    }
}
