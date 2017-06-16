package www.uni_weimar.de.au.service.inter;

/**
 * Created by ndovhuy on 16.06.2017.
 */
public interface AUCacheNotifier<T> {

    void notifyContentOnCacheUpdate(AUContentChangeListener<T> auContentChangeListener);

}
