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

    private AUNewsFeedParser auNewsFeedParser;

    public AUNewsFeedContentRequestService(Realm realm, String url) {
        super(new AUNewsFeedORM(realm));
        auNewsFeedParser = new AUNewsFeedParser();
    }


    @Override
    public Observable<List<AUNewsFeed>> requestContent(AUContentChangeListener<AUNewsFeed> auContentChangeListener) {
        notifyContentOnCacheUpdate(auContentChangeListener);
        return Observable.create((ObservableOnSubscribe<List<AUNewsFeed>>) e -> {
            try {
                List<AUNewsFeed> auNewsFeeds = auNewsFeedParser.parseAllAU(null);
                Log.e("PARSED SIZE: ", auNewsFeeds.size() + "");
                e.onNext(auNewsFeeds);
                e.onComplete();
            } catch (AUParseException a) {
                e.onError(a);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::writeToCache)
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::readFromCache);
    }
}
