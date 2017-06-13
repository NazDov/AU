package www.uni_weimar.de.au.parsers.inter;

import java.util.Map;

import io.realm.RealmObject;

/**
 * Created by nazar on 13.06.17.
 */

public interface SystemRootAUParser<K extends RealmObject, T extends RealmObject> {

    Map<K, T> parseAllAU();


}
