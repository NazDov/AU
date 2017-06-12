package www.uni_weimar.de.au.models;

import java.util.*;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazar on 12.06.17.
 */

public class AUMainMenuTab extends RealmObject implements AUItem {

    public static final String AUTYPE = "TAB";

    @PrimaryKey
    private String title;
    private RealmList<AUMainMenuItem> AUMainMenuItemList;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AUMainMenuItem> getAUMainMenuItemList() {
        return AUMainMenuItemList;
    }

    public void setAUMainMenuItemList(RealmList<AUMainMenuItem> AUMainMenuItemList) {
        this.AUMainMenuItemList = AUMainMenuItemList;
    }
}
