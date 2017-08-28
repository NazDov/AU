package www.uni_weimar.de.au.models;

import java.io.Serializable;

/**
 * Created by nazar on 12.06.17.
 */

public interface AUItem extends Serializable {
    String AUTYPE = "MENUITEM";
    String ITEM = "item";
    String URL = "url";
    String LINK = "link";
    String TITLE = "title";
    String GUID = "guid";
    String AUTHOR = "author";
    String CATEGORY = "category";
    String DESCR = "description";
    String PUB_DATE = "pubDate";
    String ENCLOSURE = "enclosure";
    String IMG_URL = "url";
    String HREF = "href";
    String FACULTY = "faculty";
    String UEB = "ueb";
    String EVENT = "regular";
    String AU_ITEM_URL_TAG = "AU_ITEM_URL";
    String AU_ITEM_NAME_TAG = "AU_ITEM_NAME";
}
