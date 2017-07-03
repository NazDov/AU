package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUNewsFeedFavourite;

/**
 * Created by ndovhuy on 30.06.2017.
 */

public class AUNewsFeedFavouriteORM implements AUBaseORM<AUNewsFeedFavourite> {

    private Realm realmUI;

    public AUNewsFeedFavouriteORM(Realm realmUI) {
        this.realmUI = realmUI;
    }

    @Override
    public AUNewsFeedFavourite add(AUNewsFeedFavourite item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealm(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUNewsFeedFavourite> addAll(List<AUNewsFeedFavourite> items) {
        throw new UnsupportedOperationException();
    }


    public boolean hasItem(String key, String name) {
        AUNewsFeedFavourite auNewsFeedFavourite = realmUI.where(AUNewsFeedFavourite.class).equalTo(key, name).findFirst();
        return auNewsFeedFavourite != null;
    }

    @Override
    public boolean deleteAll(Class<AUNewsFeedFavourite> auNewsFeedFavouriteClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String key, String name) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            AUNewsFeedFavourite auNewsFeedFavourite = r.where(AUNewsFeedFavourite.class).equalTo(key, name).findFirst();
            auNewsFeedFavourite.deleteFromRealm();
        });
    }

    @Override
    public List<AUNewsFeedFavourite> findAll() {
        return realmUI.where(AUNewsFeedFavourite.class).findAll();
    }

    @Override
    public AUNewsFeedFavourite findBy(String key, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AUNewsFeedFavourite> findAllBy(String key, String name) {
        throw new UnsupportedOperationException();
    }
}
