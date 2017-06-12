package www.uni_weimar.de.au.parsers;

import java.util.List;

import io.realm.RealmObject;
import www.uni_weimar.de.au.models.AUItem;

/**
 * Created by nazar on 12.06.17.
 */

public interface AUParser<T extends RealmObject> {

    List<T> parseAllAU();


}
