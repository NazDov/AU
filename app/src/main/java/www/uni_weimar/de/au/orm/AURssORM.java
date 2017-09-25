package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AURssChannel;

/**
 * Created by user on 19.09.17.
 */

public class AURssORM implements AUBaseORM<AURssChannel> {

    private final Realm realm;

    public AURssORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AURssChannel add(AURssChannel item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AURssChannel> addAll(List<AURssChannel> items) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteAll(Class<AURssChannel> auRssItemClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String key, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AURssChannel> findAll() {
        return realm.where(AURssChannel.class).findAll();
    }

    @Override
    public AURssChannel findBy(String key, String name) {
        return realm.where(AURssChannel.class).equalTo(key, name).findFirstAsync();
    }

    @Override
    public List<AURssChannel> findAllBy(String key, String name) {
        return realm.where(AURssChannel.class).equalTo(key, name).findAllAsync();
    }
}
