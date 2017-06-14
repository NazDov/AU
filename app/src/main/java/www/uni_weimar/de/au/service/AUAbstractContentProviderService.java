package www.uni_weimar.de.au.service;

import android.content.Context;

import java.util.List;


import io.reactivex.Observable;
import io.realm.RealmObject;

/**
 * Created by nazar on 13.06.17.
 */

interface AUAbstractContentProviderService<T>{

     Observable<List<T>> provideContent();

}
