package www.uni_weimar.de.au.service.impl;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.orm.AUMainMenuTabORM;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

/**
 * Created by nazar on 13.06.17.
 */

public class AUMainMenuContentRequestService extends AUAbstractContentRequestService<AUMainMenuTab> {

    public static AUMainMenuContentRequestService of(Realm realm, String url) {
        return new AUMainMenuContentRequestService(realm, url);
    }

    public static AUMainMenuContentRequestService of(Realm realm) {
        return new AUMainMenuContentRequestService(realm);
    }

    AUMainMenuContentRequestService(Realm realm, String url) {
        super(new AUMainMenuTabORM(realm), AUMainMenuTabParser.of(url));
    }

    AUMainMenuContentRequestService(Realm realm) {
        super(new AUMainMenuTabORM(realm), null);
    }

}
