package www.uni_weimar.de.au.service;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.orm.AUMainMenuTabORM;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;

/**
 * Created by nazar on 13.06.17.
 */

public class MainMenuContentProviderService
        extends AUAbstractContentProviderService<AUMainMenuTab>
        implements AUCacheService<AUMainMenuTab> {

    private AUMainMenuTabParser auMainMenuTabParser;
    private AUMainMenuTabORM auMainMenuTabORM;

    public MainMenuContentProviderService(Context context, Realm realm) {
        super(context);
        auMainMenuTabParser = new AUMainMenuTabParser();
        auMainMenuTabORM = new AUMainMenuTabORM(realm);
    }


    /**
     * provide content to the UI. first we will always obtain copy of the cached data
     * if it's available. The non-blocking processing will provide parsing
     * and the new data will be updated when it's available
     *
     * @return
     */
    @Override
    public Observable<List<AUMainMenuTab>> provideContent() {
        Observable<List<AUMainMenuTab>> observable = Observable.create(new ObservableOnSubscribe<List<AUMainMenuTab>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AUMainMenuTab>> e) throws Exception {
                try {
                    List<AUMainMenuTab> auMainMenuTabList = auMainMenuTabParser.parseAllAU();
                    e.onNext(auMainMenuTabList);
                    e.onComplete();
                } catch (Exception throwable) {
                    e.onError(throwable);
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());

        List<AUMainMenuTab> cachedResultList = readFromCache();
        if (cachedResultList != null) {
            observable.mergeWith(Observable.just(cachedResultList));
        }
        return observable;
    }

    @Override
    public void writeToCache(List<AUMainMenuTab> objects) {
        auMainMenuTabORM.addAll(objects);
    }

    @Override
    public List<AUMainMenuTab> readFromCache() {
        return auMainMenuTabORM.findAll();
    }
}
