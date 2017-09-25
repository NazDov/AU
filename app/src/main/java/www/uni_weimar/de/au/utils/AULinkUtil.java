package www.uni_weimar.de.au.utils;

/**
 * utility class the to work with links
 */

public class AULinkUtil {

    private static final String JSESSIONID = "jsessionid";
    private static final String JSESSIONID_REGEX = "jsessionid=[^?]*";

    /**
     * returns unique part of an url with stripped jsessionid or
     * the original link if jsessionid is not found in the link
     *
     * @param link - URL
     * @return uniqueLinkPart
     */
    public static String returnUniqueLinkPart(String link) {
        String uniqueLinkPart;
        if (link.contains(JSESSIONID)) {
            uniqueLinkPart = link.replaceAll(JSESSIONID_REGEX, "");
        } else {
            uniqueLinkPart = link;
        }
        return uniqueLinkPart;
    }
}
