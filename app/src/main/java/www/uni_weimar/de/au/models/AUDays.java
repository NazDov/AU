package www.uni_weimar.de.au.models;

/**
 * Created by nazar on 30.07.17.
 */

public enum AUDays {

    MO("Mo."),
    TUE("Di."),
    WED("Mi."),
    THU("Do."),
    FR("Fr.");

    private final String tag;

    AUDays(String tag) {
        this.tag = tag;
    }


    @Override
    public String toString() {
        return tag;
    }
}
