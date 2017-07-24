package www.uni_weimar.de.au.service.impl;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUEventORM;
import www.uni_weimar.de.au.parsers.impl.AUEventParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 21.07.17.
 */

public class AUEventContentRequestService extends AUAbstractContentRequestService<AUFacultyEvent> {

    private AUEventContentRequestService(Realm realm, String url) {
        this(new AUEventORM(realm), new AUEventParser(url));
    }

    private AUEventContentRequestService(AUBaseORM<AUFacultyEvent> auBaseORm, AUParser<AUFacultyEvent> auParser) {
        super(auBaseORm, auParser);
    }

    public static AUEventContentRequestService of(Realm realm, String url) {
        checkNotNull(realm);
        checkNotNull(url);
        return new AUEventContentRequestService(realm, url);
    }

    public AUEventContentRequestService notifyContentOnCacheUpdate(AUContentChangeListener<AUFacultyEvent> auContentChangeListener, String key, String value) {
        List<AUFacultyEvent> cachedContent = readFromCache(key, value);
        auContentChangeListener.notifyContentChange(cachedContent);
        return this;
    }


    private List<AUFacultyEvent> readFromCache(String key, String value) {
        return getAuBaseORM().findAllBy(key, value);
    }
}
