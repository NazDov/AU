package www.uni_weimar.de.au.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ndovhuy on 25.08.2017.
 */

public class AUUtilityDefaultLinksFactory {
    private static Map<Integer, String> defaultAULinks = new ConcurrentHashMap<>();

    private AUUtilityDefaultLinksFactory() {

    }

    public static String getDefaultLink(Integer linkId) {
        return getDefaultLink(linkId, null);
    }

    static String getDefaultLink(Integer linkId, String defVal) {
        String defaultLinkVal = defaultAULinks.get(linkId);
        if (defaultLinkVal == null && defVal != null) {
            defaultLinkVal = defaultAULinks.put(linkId, defVal);
        }
        return defaultLinkVal;
    }
}
