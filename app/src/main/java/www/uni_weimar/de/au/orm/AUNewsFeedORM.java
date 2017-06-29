package www.uni_weimar.de.au.orm;

import android.util.Log;

import org.jsoup.select.Evaluator;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import www.uni_weimar.de.au.models.AUNewsFeed;

/**
 * Created by ndovhuy on 16.06.2017.
 */

public class AUNewsFeedORM implements AUBaseORM<AUNewsFeed> {

    private Realm realm;

    public AUNewsFeedORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AUNewsFeed add(AUNewsFeed item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealm(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUNewsFeed> addAll(List<AUNewsFeed> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(AUNewsFeed.class);
        items = realm.copyToRealm(items);
        Log.v("ORM NEWSFEED SAVED: ", items.size() + "");
        realm.commitTransaction();
        return items;
    }

    @Override
    public boolean deleteAll(Class<AUNewsFeed> auNewsFeedClass) {
        realm.delete(auNewsFeedClass);
        return true;
    }

    @Override
    public List<AUNewsFeed> findAll() {
        return realm.where(AUNewsFeed.class).findAll();
    }

    @Override
    public AUNewsFeed findBy(String key, String name) {
        return realm.where(AUNewsFeed.class).equalTo(key, name).findFirst();
    }

    @Override
    public List<AUNewsFeed> findAllBy(String key, String name) {
        return realm.where(AUNewsFeed.class).equalTo(key, name).findAll();
    }
}
