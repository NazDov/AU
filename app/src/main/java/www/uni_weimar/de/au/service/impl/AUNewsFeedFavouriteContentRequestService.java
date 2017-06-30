package www.uni_weimar.de.au.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import io.reactivex.Observable;
import io.realm.Realm;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.models.AUNewsFeedFavourite;
import www.uni_weimar.de.au.orm.AUNewsFeedFavouriteORM;
import www.uni_weimar.de.au.orm.AUNewsFeedORM;
import www.uni_weimar.de.au.service.inter.AUAbstractContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;

/**
 * Created by ndovhuy on 30.06.2017.
 */

public class AUNewsFeedFavouriteContentRequestService extends AUAbstractContentRequestService<AUNewsFeed> {

    private AUNewsFeedFavouriteORM auNewsFeedFavouriteORM;

    public AUNewsFeedFavouriteContentRequestService(Realm realm) {
        super(new AUNewsFeedORM(realm), null);
        auNewsFeedFavouriteORM = new AUNewsFeedFavouriteORM(realm);
    }


    @Override
    public Observable<List<AUNewsFeed>> requestContent(AUContentChangeListener<AUNewsFeed> auContentChangeListener) {
        notifyContentOnCacheUpdate(auContentChangeListener);
        return null;
    }


    @Override
    public void notifyContentOnCacheUpdate(AUContentChangeListener<AUNewsFeed> auContentChangeListener) {
        List<AUNewsFeed> auNewsFeeds = new ArrayList<>();
        List<AUNewsFeedFavourite> auNewsFeedFavourites = auNewsFeedFavouriteORM.findAll();
        if (!auNewsFeedFavourites.isEmpty()) {
            AUNewsFeedORM auNewsFeedORM = (AUNewsFeedORM) getAuBaseORM();
            String[] auNewsItemSearchTokens = new String[auNewsFeedFavourites.size()];
            for (int i = 0; i < auNewsFeedFavourites.size(); i++) {
                auNewsItemSearchTokens[i] = auNewsFeedFavourites.get(i).getLink();
            }
            auNewsFeeds = auNewsFeedORM.findAllIn("link", auNewsItemSearchTokens);
        }
        auContentChangeListener.notifyContentChange(auNewsFeeds);
    }
}
