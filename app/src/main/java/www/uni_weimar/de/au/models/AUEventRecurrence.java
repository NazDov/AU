package www.uni_weimar.de.au.models;

/**
 * Created by user on 28.09.17.
 */

public enum AUEventRecurrence {
    DAILY("Einzel"),
    WEEKLY("wöch.");

    private final String name;

    AUEventRecurrence(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
