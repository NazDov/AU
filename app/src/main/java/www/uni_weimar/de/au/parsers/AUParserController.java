package www.uni_weimar.de.au.parsers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;

/**
 * Created by nazar on 12.06.17.
 */

public class AUParserController {

    private static AUParserController auParserController;
    private Map<String, AUParser> parserMap = new ConcurrentHashMap<>();

    public static AUParserController getInstance() {

        if (auParserController == null) {
            auParserController = new AUParserController();
        }

        return auParserController;
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
