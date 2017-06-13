package www.uni_weimar.de.au.service;

import android.content.Context;

import java.util.List;


import io.reactivex.Observable;
import io.realm.RealmObject;

/**
 * Created by nazar on 13.06.17.
 */

public abstract class AUAbstractContentProviderService<T extends RealmObject> {

    protected Context context;

    public AUAbstractContentProviderService(Context context) {
        this.context = context;
    }

    protected Observable<List<T>> provideContent(){
        return null;
    }


}
