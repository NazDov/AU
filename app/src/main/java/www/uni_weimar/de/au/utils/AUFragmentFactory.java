package www.uni_weimar.de.au.utils;

import android.support.v4.app.Fragment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 06.10.17.
 */

public class AUFragmentFactory {

    private static Map<Class, Fragment> fragmentMap = new ConcurrentHashMap<>();

    private AUFragmentFactory() {

    }

    public static void addFragment(Class tag, Fragment fragment) {
        fragmentMap.put(tag, fragment);
    }

    public static Fragment getFragment(Class tag) {
        return fragmentMap.get(tag);
    }

}
