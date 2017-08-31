package www.uni_weimar.de.au.service.inter;

/**
 * Created by ndovhuy on 16.06.2017.
 */
interface AUCacheNotifier<T> {

    AUAbstractContentRequestService<?> notifyContentOnCacheUpdate(AUContentChangeListener<T> auContentChangeListener);

}
