package www.uni_weimar.de.au.utils;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ndovhuy on 25.08.2017.
 */

public class AUUtilityDefaultLinksFactory {
    private static Map<Integer, String> defaultAULinks = new ConcurrentHashMap<>();
    private static Map<Integer, Integer> defaultAUResources = new ConcurrentHashMap<>();

    private AUUtilityDefaultLinksFactory() {

    }

    public static String getDefaultLink(@NonNull Integer linkId) {
        return getDefaultLink(linkId, null);
    }

    public static int getDefaultResource(@NonNull Integer linkId) {
        return getDefaultResource(linkId, null);
    }

    public static String getDefaultLink(@NonNull Integer linkId, String defVal) {
        String defaultLinkVal = defaultAULinks.get(linkId);
        if (defaultLinkVal == null && defVal != null) {
            defaultLinkVal = defaultAULinks.put(linkId, defVal);
        }
        return defaultLinkVal;
    }

    /**
     *
     * @param linkId
     * @param defVal
     * @return
     */
    public static int getDefaultResource(@NonNull Integer linkId, Integer defVal) {
        Integer defaultLinkVal = defaultAUResources.get(linkId);
        if (defaultLinkVal == null && defVal != null) {
            defaultAUResources.put(linkId, defVal);
            defaultLinkVal = defaultAUResources.get(linkId);
        }
        return defaultLinkVal == null ? -1 : defaultLinkVal;
    }
}
