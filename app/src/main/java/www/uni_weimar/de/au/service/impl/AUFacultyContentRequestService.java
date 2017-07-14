package www.uni_weimar.de.au.service.impl;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUFacultyORM;
import www.uni_weimar.de.au.parsers.impl.AUFacultyParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 10.07.2017.
 */

public class AUFacultyContentRequestService extends AUAbstractContentRequestService<AUFacultyHeader> {

    AUFacultyContentRequestService(Realm realm, String url) {
        super(new AUFacultyORM(realm), AUFacultyParser.of(url));
    }

    AUFacultyContentRequestService(AUBaseORM<AUFacultyHeader> auBaseORm, AUParser<AUFacultyHeader> auParser) {
        super(auBaseORm, auParser);
    }

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


    @Override
    public List<AUFacultyHeader> readFromCache(List<AUFacultyHeader> objects) {
        return getAuBaseORM().findAllBy("autype", "faculty");
    }
}
