package www.uni_weimar.de.au.service.impl;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUCafeteriaMenu;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUCafeteriaMenuORM;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaMenuParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;

/**
 * Created by nazar on 20.08.17.
 */

public class AUCafeteriaMenuContentRequestService extends AUAbstractContentRequestService<AUCafeteriaMenu>{

    private AUCafeteriaMenuContentRequestService(Realm realm, String url) {
        super(new AUCafeteriaMenuORM(realm), AUCafeteriaMenuParser.of(url));
    }

    public static AUCafeteriaMenuContentRequestService of(Realm realm, String url){
        return new AUCafeteriaMenuContentRequestService(realm, url);
    }


}
