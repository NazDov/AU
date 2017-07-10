package www.uni_weimar.de.au.models;

import java.util.*;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 12.06.17.
 */

public class AUMainMenuTab extends RealmObject implements AUItem {

    public static final String AUTYPE = "TAB";
    public static final String TITLE = "title";

    @PrimaryKey
    private String title;
    private RealmList<AUMainMenuItem> auMainMenuItemList;

    public AUMainMenuTab(){

    }

    private AUMainMenuTab(String title, RealmList<AUMainMenuItem> auMainMenuItemList) {
        this.title = title;
        this.auMainMenuItemList = auMainMenuItemList;
    }

    public static AUMainMenuTab of(String title, RealmList<AUMainMenuItem> auMainMenuItemList) {
        checkNotNull(title);
        checkNotNull(auMainMenuItemList);
        return new AUMainMenuTab(title, auMainMenuItemList);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AUMainMenuItem> getAuMainMenuItemList() {
        return Collections.unmodifiableList(auMainMenuItemList);
    }

    public void setAuMainMenuItemList(RealmList<AUMainMenuItem> auMainMenuItems) {
        this.auMainMenuItemList = auMainMenuItems;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUMainMenuTab)) return false;

        AUMainMenuTab that = (AUMainMenuTab) o;

        if (!getTitle().equals(that.getTitle())) return false;
        return getAuMainMenuItemList().equals(that.getAuMainMenuItemList());

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getAuMainMenuItemList().hashCode();
        return result;
    }
}
