package www.uni_weimar.de.au.service.impl;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUFacultyHeaderORM;
import www.uni_weimar.de.au.parsers.impl.AUFacultyHeaderParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 10.07.2017.
 */

public class AUFacultyContentRequestService extends AUAbstractContentRequestService<AUFacultyHeader> {

    public static AUFacultyContentRequestService of(Realm realm, String url) {
        checkNotNull(realm);
        checkNotNull(url);
        return new AUFacultyContentRequestService(realm, url);
    }

    public static AUFacultyContentRequestService of(AUBaseORM<AUFacultyHeader> auBaseORm, AUParser<AUFacultyHeader> auParser) {
        checkNotNull(auBaseORm);
        checkNotNull(auParser);
        return new AUFacultyContentRequestService(auBaseORm, auParser);
    }

    AUFacultyContentRequestService(Realm realm, String url) {
        super(new AUFacultyHeaderORM(realm), AUFacultyHeaderParser.of(url));
    }

    AUFacultyContentRequestService(AUBaseORM<AUFacultyHeader> auBaseORm, AUParser<AUFacultyHeader> auParser) {
        super(auBaseORm, auParser);
    }
}
