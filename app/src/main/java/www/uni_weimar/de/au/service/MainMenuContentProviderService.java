package www.uni_weimar.de.au.service;

import android.util.Log;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.orm.AUMainMenuTabORM;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;

/**
 * Created by nazar on 13.06.17.
 */

public class MainMenuContentProviderService implements AUAbstractContentProviderService<AUMainMenuTab> {

    private AUMainMenuTabParser auMainMenuTabParser;
    private AUMainMenuTabORM auMainMenuTabORM;
    private String url;


    public MainMenuContentProviderService() {

    }

    public MainMenuContentProviderService(Realm realm, String url) {
        this.url = url;
        auMainMenuTabParser = new AUMainMenuTabParser();
        auMainMenuTabORM = new AUMainMenuTabORM(realm);
    }

    /**
     * provide content to the UI. first we will always obtain copy of the cached data
     * if it's available. The non-blocking processing will provide parsing
     * and the new data will be updated when it's available
     * <p>
     * the logic behind this:
     * <p>
     * first the readFromCache function will be ran, populating the cached data to the UI.
     * then on the parallel computation thread the writeToCache function will be triggered
     *
     * @return
     */
    @Override
    public Observable<List<AUMainMenuTab>> provideContent() {
        Observable<List<AUMainMenuTab>> observable = Observable.create((ObservableOnSubscribe<List<AUMainMenuTab>>) e -> {
            try {
                List<AUMainMenuTab> auMainMenuTabList = auMainMenuTabParser.parseAllAU(url);
                e.onNext(auMainMenuTabList);
                e.onComplete();
            } catch (Exception throwable) {
                e.onError(throwable);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(auMainMenuTabORM::addAll)
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::readFromCache);

        List<AUMainMenuTab> cachedResultList = readFromCache(null);
        if (cachedResultList != null && !cachedResultList.isEmpty())
            observable.mergeWith(Observable.just(cachedResultList));
        return observable;
    }

    private List<AUMainMenuTab> readFromCache(List<AUMainMenuTab> auMainMenuTabs) {
        return auMainMenuTabORM.findAll();
    }

}
