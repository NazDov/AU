package www.uni_weimar.de.au.service.inter;

import android.util.Log;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmObject;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.orm.AUBaseORM;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

/**
 * Created by nazar on 13.06.17.
 */

public abstract class AUAbstractContentRequestService<T extends RealmObject> implements AUCacheService<T>, AUCacheNotifier<T> {

    private AUBaseORM<T> auBaseORM;
    private AUParser<T> auParser;

    public AUAbstractContentRequestService(AUBaseORM<T> auBaseORm, AUParser<T> auParser) {
        this.auBaseORM = auBaseORm;
        this.auParser = auParser;
    }

    public Observable<List<T>> requestContent(AUContentChangeListener<T> auContentChangeListener) {
        notifyContentOnCacheUpdate(auContentChangeListener);
        return Observable.create((ObservableOnSubscribe<List<T>>) e -> {
            try {
                e.onNext(auParser.parseAllAU(null));
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


    @Override
    public void notifyContentOnCacheUpdate(AUContentChangeListener<T> auContentChangeListener) {
        List<T> cachedContent = readFromCache(null);
        auContentChangeListener.notifyContentChange(cachedContent);
    }

    @Override
    public List<T> writeToCache(List<T> realmObjects) {
        return auBaseORM.addAll(realmObjects);
    }

    @Override
    public List<T> readFromCache(List<T> objects) {
        return  auBaseORM.findAll();
    }

    public AUBaseORM<T> getAuBaseORM() {
        return auBaseORM;
    }
}
