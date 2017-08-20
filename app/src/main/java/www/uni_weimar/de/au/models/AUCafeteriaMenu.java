package www.uni_weimar.de.au.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazar on 20.08.17.
 */

public class AUCafeteriaMenu extends RealmObject implements AUItem {

    @PrimaryKey
    private String auCafeteriaMenuDayTitle;
    private RealmList<AUCafeteriaMenuItem> auCafeteriaMenuItems;

    public AUCafeteriaMenu(){

    }

    public AUCafeteriaMenu(String auCafeteriaMenuDayTitle, RealmList<AUCafeteriaMenuItem> auCafeteriaMenuItems) {
        this.auCafeteriaMenuDayTitle = auCafeteriaMenuDayTitle;
        this.auCafeteriaMenuItems = auCafeteriaMenuItems;
    }

    public String getAuCafeteriaMenuDayTitle() {
        return auCafeteriaMenuDayTitle;
    }


    public RealmList<AUCafeteriaMenuItem> getAuCafeteriaMenuItems() {
        return auCafeteriaMenuItems;
    }

    public void setAuCafeteriaMenuDayTitle(String auCafeteriaMenuDayTitle) {
        this.auCafeteriaMenuDayTitle = auCafeteriaMenuDayTitle;
    }

    public void setAuCafeteriaMenuItems(RealmList<AUCafeteriaMenuItem> auCafeteriaMenuItems) {
        this.auCafeteriaMenuItems = auCafeteriaMenuItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUCafeteriaMenu)) return false;
        AUCafeteriaMenu that = (AUCafeteriaMenu) o;
        return Objects.equal(getAuCafeteriaMenuDayTitle(), that.getAuCafeteriaMenuDayTitle()) &&
                Objects.equal(getAuCafeteriaMenuItems(), that.getAuCafeteriaMenuItems());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAuCafeteriaMenuDayTitle(), getAuCafeteriaMenuItems());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("auCafeteriaMenuDayTitle", auCafeteriaMenuDayTitle)
                .add("auCafeteriaMenuItems", auCafeteriaMenuItems)
                .toString();
    }
}
