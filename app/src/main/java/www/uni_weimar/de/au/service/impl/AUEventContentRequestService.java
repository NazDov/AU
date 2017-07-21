package www.uni_weimar.de.au.service.impl;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUEventORM;
import www.uni_weimar.de.au.parsers.impl.AUEventParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 21.07.17.
 */

public class AUEventContentRequestService extends AUAbstractContentRequestService<AUFacultyEvent> {

    public AUEventContentRequestService(Realm realm, String url) {
        this(new AUEventORM(realm), new AUEventParser(url));
    }

    private AUEventContentRequestService(AUBaseORM<AUFacultyEvent> auBaseORm, AUParser<AUFacultyEvent> auParser) {
        super(auBaseORm, auParser);
    }

    public static AUAbstractContentRequestService of(Realm realm, String url) {
        checkNotNull(realm);
        checkNotNull(url);
        return new AUEventContentRequestService(realm, url);
    }
}
