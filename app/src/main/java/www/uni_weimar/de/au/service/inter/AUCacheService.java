package www.uni_weimar.de.au.service.inter;

import java.util.List;

/**
 * Created by nazar on 13.06.17.
 */

public interface AUCacheService<T> {

    List<T> writeToCache(List<T> objects);

    List<T> readFromCache(List<T> objects);


}
