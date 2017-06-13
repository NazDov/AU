package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUMainMenuTab;

/**
 * Created by nazar on 13.06.17.
 */

public class AUMainMenuTabORM implements AUBaseORM<AUMainMenuTab> {

    private Realm realm;

    public AUMainMenuTabORM(Realm realm) {
        this.realm = realm;
    }

    @Override
    public AUMainMenuTab add(AUMainMenuTab item) {
        realm.beginTransaction();
        item = realm.copyToRealm(item);
        realm.commitTransaction();
        return item;
    }

    @Override
    public List<AUMainMenuTab> addAll(List<AUMainMenuTab> items) {
        realm.beginTransaction();
        List<AUMainMenuTab> auMainMenuTabList = realm.copyToRealm(items);
        realm.commitTransaction();
        return auMainMenuTabList;
    }

    @Override
    public boolean delete(AUMainMenuTab item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AUMainMenuTab> findAll() {
        return realm.where(AUMainMenuTab.class).findAll();
    }

    @Override
    public AUMainMenuTab findBy(String key, String name) {
        return realm.where(AUMainMenuTab.class).equalTo(key, name).findFirst();
    }
}
