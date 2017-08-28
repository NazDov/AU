package www.uni_weimar.de.au.utils;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 28.08.17.
 */

public class AUInstanceFactory {

    private static Map<Class<?>, Object> instanceMap = new ConcurrentHashMap<>();

    private AUInstanceFactory() {

    }

    public static void storeInstance(@NonNull Class<?> key, @NonNull Object instance) {
        instanceMap.put(key, instance);
    }

    public static Object getInstance(@NonNull Class<?> key){
        return instanceMap.get(key);
    }
}
