package www.uni_weimar.de.au.service.impl;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUCafeteriaMenu;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.orm.AUCafeteriaMenuORM;
import www.uni_weimar.de.au.parsers.impl.AUCafeteriaMenuParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;
import www.uni_weimar.de.au.utils.AUParserFactory;

/**
 * Created by nazar on 20.08.17.
 */

public class AUCafeteriaMenuContentRequestService extends AUAbstractContentRequestService<AUCafeteriaMenu> {

    private AUCafeteriaMenuContentRequestService(Realm realm, String url) {
        super(new AUCafeteriaMenuORM(realm), AUParserFactory.newParser(AUCafeteriaMenuParser.class, url));
    }

    public static AUCafeteriaMenuContentRequestService of(Realm realm, String url) {
        return new AUCafeteriaMenuContentRequestService(realm, url);
    }


    public AUCafeteriaMenuContentRequestService notifyContentOnCacheUpdate(AUContentChangeListener<AUCafeteriaMenu> auContentChangeListener, String key, String name) {
        List<AUCafeteriaMenu> auCafeteriaMenus;
        auCafeteriaMenus = getAuBaseORM().findAllBy(key, name);
        auContentChangeListener.notifyContentChange(auCafeteriaMenus);
        return this;
    }


}
