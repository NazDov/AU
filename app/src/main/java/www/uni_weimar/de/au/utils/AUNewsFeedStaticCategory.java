package www.uni_weimar.de.au.utils;

/**
 * Created by ndovhuy on 03.07.2017.
 */

public enum AUNewsFeedStaticCategory {
    ALL("Alle"),
    FAVOURITE("Favourite");

    private final String name;

    AUNewsFeedStaticCategory(String favourite) {
        this.name = favourite;
    }

    @Override
    public String toString() {
        return name;
    }
}
