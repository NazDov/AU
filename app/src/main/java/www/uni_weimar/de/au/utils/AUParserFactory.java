package www.uni_weimar.de.au.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.RealmObject;
import www.uni_weimar.de.au.parsers.inter.AUParser;

/**
 * Created by user on 06.10.17.
 */

public class AUParserFactory {

    private static Map<Class, Class> classMap = new ConcurrentHashMap<>();

    public static void addParserClass(Class tag, Class instance) {
        classMap.put(tag, instance);
    }

    public static AUParser newParser(Class tag, String url) {
        Class parserClass = classMap.get(tag);
        AUParser auParser = null;
        try {
            auParser = (AUParser) parserClass.newInstance();
            auParser.setUrl(url);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return auParser;
    }
}
