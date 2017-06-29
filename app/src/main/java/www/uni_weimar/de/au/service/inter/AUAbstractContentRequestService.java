package www.uni_weimar.de.au.service.inter;

import android.util.Log;

import java.util.List;


import io.reactivex.Observable;
import io.realm.RealmObject;
import www.uni_weimar.de.au.orm.AUBaseORM;

/**
 * Created by nazar on 13.06.17.
 */

public abstract class AUAbstractContentRequestService<T extends RealmObject> implements AUCacheService<T>, AUCacheNotifier<T> {

    private AUBaseORM<T> auBaseORM;

    public AUAbstractContentRequestService(AUBaseORM<T> auBaseORm) {
        this.auBaseORM = auBaseORm;
    }

    public abstract Observable<List<T>> requestContent(AUContentChangeListener<T> AUContentChangeListener);

    @Override
    public void notifyContentOnCacheUpdate(AUContentChangeListener<T> auContentChangeListener) {
        List<T> cachedContent = readFromCache(null);
        auContentChangeListener.notifyContentChange(cachedContent);
    }

    @Override
    public List<T> writeToCache(List<T> realmObjects) {
        return auBaseORM.addAll(realmObjects);
    }

    @Override
    public List<T> readFromCache(List<T> objects) {
        return  auBaseORM.findAll();
    }

    public AUBaseORM<T> getAuBaseORM() {
        return auBaseORM;
    }
}
