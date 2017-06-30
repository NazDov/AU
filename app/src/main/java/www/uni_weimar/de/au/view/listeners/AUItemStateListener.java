package www.uni_weimar.de.au.view.listeners;

import www.uni_weimar.de.au.models.AUNewsFeed;

/**
 * Created by ndovhuy on 30.06.2017.
 */
public interface AUItemStateListener<T> {
   void onLiked(T auItem);
}
