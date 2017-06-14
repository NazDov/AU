package www.uni_weimar.de.au.orm;

import java.util.List;

import io.realm.Realm;
import www.uni_weimar.de.au.models.AUMainMenuTab;

/**
 * Created by nazar on 13.06.17.
 */

public class AUMainMenuTabORM implements AUBaseORM<AUMainMenuTab> {

    private Realm realmUI;

    public AUMainMenuTabORM(Realm realm){
        this.realmUI = realm;
    }

    @Override
    public AUMainMenuTab add(AUMainMenuTab item) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        item = realm.copyToRealm(item);
        realm.commitTransaction();
        realm.close();
        return item;
    }

    @Override
    public List<AUMainMenuTab> addAll(List<AUMainMenuTab> items) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<AUMainMenuTab> auMainMenuTabList = realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
        realm.close();
        return auMainMenuTabList;
    }

    @Override
    public boolean delete(AUMainMenuTab item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AUMainMenuTab> findAll() {
        return realmUI.where(AUMainMenuTab.class).findAll();
    }

    @Override
    public AUMainMenuTab findBy(String key, String name) {
        return realmUI.where(AUMainMenuTab.class).equalTo(key, name).findFirst();
    }
}
