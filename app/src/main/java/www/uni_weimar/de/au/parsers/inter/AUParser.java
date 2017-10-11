package www.uni_weimar.de.au.parsers.inter;

import java.util.List;

import io.realm.RealmObject;
import www.uni_weimar.de.au.parsers.exception.AUParseException;

/**
 * Created by nazar on 12.06.17.
 */

public interface AUParser<T extends RealmObject> {

    List<T> parseAU(String url) throws AUParseException;

    List<T> parseAU() throws AUParseException;

    void setUrl(String url);
}
