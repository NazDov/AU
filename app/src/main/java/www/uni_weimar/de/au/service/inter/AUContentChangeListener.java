package www.uni_weimar.de.au.service.inter;

import java.util.List;

/**
 * Created by ndovhuy on 16.06.2017.
 */
public interface AUContentChangeListener<T> {

   void notifyContentChange(List<T> content);
}
