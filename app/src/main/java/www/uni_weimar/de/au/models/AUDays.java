package www.uni_weimar.de.au.models;

/**
 * Created by nazar on 30.07.17.
 */

public enum AUDays {

    MO("Mo."),
    TUE("Di."),
    WED("Mi."),
    THU("Do."),
    FR("Fr."),

    MONTAG("Montag"),
    DIENSTAG("Dienstag"),
    MITTWOCH("Mittwoch"),
    DONNERSTAG("Donnerstag"),
    FREITAG("Freitag");

    private final String tag;

    AUDays(String tag) {
        this.tag = tag;
    }


    @Override
    public String toString() {
        return tag;
    }
}
