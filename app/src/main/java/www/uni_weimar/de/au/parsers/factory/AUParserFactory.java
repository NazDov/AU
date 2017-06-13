package www.uni_weimar.de.au.parsers.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.parsers.impl.AUMainMenuTabParser;
import www.uni_weimar.de.au.parsers.inter.AUParser;

/**
 * Created by nazar on 12.06.17.
 */

public class AUParserFactory {

    private static AUParserFactory auParserFactory;
    private Map<String, AUParser> parserMap = new ConcurrentHashMap<>();

    public static AUParserFactory getInstance() {

        if (auParserFactory == null) {
            auParserFactory = new AUParserFactory();
        }

        return auParserFactory;
    }


    public AUParser getAUParser(String autype) {

        AUParser auParser = parserMap.get(autype);
        if (auParser == null) {
            switch (autype) {
                case AUMainMenuTab.AUTYPE:
                    auParser = new AUMainMenuTabParser();
                    autype = AUMainMenuTab.AUTYPE;
                    break;
            }

            parserMap.put(autype, auParser);
        }

        return auParser;
    }


}
