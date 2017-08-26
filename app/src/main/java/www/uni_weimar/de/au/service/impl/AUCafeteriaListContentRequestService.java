package www.uni_weimar.de.au.service.impl;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUCafeteria;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUCafeteriaListORM;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaListParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 26.08.2017.
 */

public class AUCafeteriaListContentRequestService extends AUAbstractContentRequestService<AUCafeteria> {

    private AUCafeteriaListContentRequestService(AUBaseORM<AUCafeteria> auBaseORm, AUParser<AUCafeteria> auParser) {
        super(auBaseORm, auParser);
    }

    public static AUCafeteriaListContentRequestService of(Realm realm, String url) {
        checkNotNull(realm);
        checkNotNull(url);
        return new AUCafeteriaListContentRequestService(new AUCafeteriaListORM(realm), AUCafeteriaListParser.of(url));
    }
}
