package www.uni_weimar.de.au.service.impl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.orm.AUMainMenuTabORM;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

/**
 * Created by nazar on 13.06.17.
 */

public class AUMainMenuContentRequestService extends AUAbstractContentRequestService<AUMainMenuTab> {

    private AUMainMenuTabParser auMainMenuTabParser;
    private String url;


    protected AUMainMenuContentRequestService() {

    }

    public AUMainMenuContentRequestService(Realm realm, String url) {
        super(new AUMainMenuTabORM(realm));
        this.url = url;
        auMainMenuTabParser = new AUMainMenuTabParser();
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
    public Observable<List<AUMainMenuTab>> requestContent(AUContentChangeListener<AUMainMenuTab> AUContentChangeListener) {
        notifyContentOnCacheUpdate(AUContentChangeListener);
        return Observable.create((ObservableOnSubscribe<List<AUMainMenuTab>>) e -> {
            try {
                List<AUMainMenuTab> auMainMenuTabList = auMainMenuTabParser.parseAllAU(url);
                e.onNext(auMainMenuTabList);
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
