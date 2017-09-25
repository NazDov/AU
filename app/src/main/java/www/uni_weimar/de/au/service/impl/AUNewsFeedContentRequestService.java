package www.uni_weimar.de.au.service.impl;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.models.AUNewsFeedFavourite;
import www.uni_weimar.de.au.models.AURssChannel;
import www.uni_weimar.de.au.orm.AUNewsFeedFavouriteORM;
import www.uni_weimar.de.au.orm.AUNewsFeedORM;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.impl.AUNewsFeedParser;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

/**
 * Created by ndovhuy on 16.06.2017.
 */

public class AUNewsFeedContentRequestService extends AUAbstractContentRequestService<AUNewsFeed> {

    private final Realm realmUI;
    private AUNewsFeedFavouriteORM auNewsFeedFavouriteORM;

    public enum AUNewsFeedSearchKey {
        CATEGORY_URL("categoryUrl");

        private String category;

        AUNewsFeedSearchKey(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return category;
        }
    }

    public AUNewsFeedContentRequestService(Realm realm, String url) {
        super(new AUNewsFeedORM(realm), AUNewsFeedParser.of(url));
        this.realmUI = realm;
    }

    public AUNewsFeedContentRequestService(Realm realm, AURssChannel rssChannel) {
        super(new AUNewsFeedORM(realm), AUNewsFeedParser.of(rssChannel));
        this.realmUI = realm;
    }

    /**
     * @param rssChannel
     * @return
     */
    public Observable<List<AUNewsFeed>> requestNewContent(@NonNull AURssChannel rssChannel) {
        return Observable.create((ObservableOnSubscribe<List<AUNewsFeed>>) e -> {
            try {
                e.onNext(((AUNewsFeedParser) getAuParser()).parseAU(rssChannel));
                e.onComplete();
            } catch (AUParseException a) {
                e.onError(a);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(this::writeToCache)
                .observeOn(AndroidSchedulers.mainThread())
                .map(auNewsFeeds -> findByCategory(rssChannel.getUrl()));
    }

    public void requestFavouriteNewsFeeds(AUContentChangeListener<AUNewsFeed> contentChangeListener) {
        if (auNewsFeedFavouriteORM == null) {
            auNewsFeedFavouriteORM = new AUNewsFeedFavouriteORM(realmUI);
        }
        List<AUNewsFeed> auNewsFeeds = getAllFavouriteNewsFeedsIfExist();
        contentChangeListener.notifyContentChange(auNewsFeeds);
    }

    private List<AUNewsFeed> getAllFavouriteNewsFeedsIfExist() {
        List<AUNewsFeed> auNewsFeeds = new ArrayList<>();
        List<AUNewsFeedFavourite> auNewsFeedFavourites = auNewsFeedFavouriteORM.findAll();
        if (!auNewsFeedFavourites.isEmpty()) {
            auNewsFeeds = convertToNewsFeeds(auNewsFeedFavourites);
        }
        return auNewsFeeds;
    }

    private List<AUNewsFeed> convertToNewsFeeds(List<AUNewsFeedFavourite> auNewsFeedFavourites) {
        List<AUNewsFeed> auNewsFeeds;AUNewsFeedORM auNewsFeedORM = (AUNewsFeedORM) getAuBaseORM();
        String[] auNewsItemSearchTokens = new String[auNewsFeedFavourites.size()];
        for (int i = 0; i < auNewsFeedFavourites.size(); i++) {
            auNewsItemSearchTokens[i] = auNewsFeedFavourites.get(i).getLink();
        }
        auNewsFeeds = auNewsFeedORM.findAllIn(AUItem.LINK, auNewsItemSearchTokens);
        return auNewsFeeds;
    }

    private List<AUNewsFeed> findByCategory(@NonNull String url) {
        return getAuBaseORM().findAllBy(AUNewsFeedSearchKey.CATEGORY_URL.toString(), url);
    }


    public List<AUNewsFeed> findAllBy(AUNewsFeedSearchKey category, String categoryName) {
        return getAuBaseORM().findAllBy(category.toString(), categoryName);
    }
}
