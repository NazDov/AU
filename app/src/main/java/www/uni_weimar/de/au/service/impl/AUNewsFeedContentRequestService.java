package www.uni_weimar.de.au.service.impl;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.orm.AUNewsFeedORM;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUNewsFeedParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

/**
 * Created by ndovhuy on 16.06.2017.
 */

public class AUNewsFeedContentRequestService extends AUAbstractContentRequestService<AUNewsFeed> {


    public AUNewsFeedContentRequestService(Realm realm, String url) {
        super(new AUNewsFeedORM(realm), AUNewsFeedParser.of(url));
    }

}
