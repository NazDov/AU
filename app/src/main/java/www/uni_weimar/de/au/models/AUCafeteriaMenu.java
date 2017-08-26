package www.uni_weimar.de.au.models;

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
    private String cafeteriaUrl;

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

    public String getCafeteriaUrl() {
        return cafeteriaUrl;
    }

    public void setCafeteriaUrl(String cafeteriaUrl) {
        this.cafeteriaUrl = cafeteriaUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AUCafeteriaMenu that = (AUCafeteriaMenu) o;
        return Objects.equal(auCafeteriaMenuDayTitle, that.auCafeteriaMenuDayTitle) &&
                Objects.equal(auCafeteriaMenuItems, that.auCafeteriaMenuItems) &&
                Objects.equal(cafeteriaUrl, that.cafeteriaUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(auCafeteriaMenuDayTitle, auCafeteriaMenuItems, cafeteriaUrl);
    }

    @Override
    public String toString() {
        return "AUCafeteriaMenu{" +
                "auCafeteriaMenuDayTitle='" + auCafeteriaMenuDayTitle + '\'' +
                ", auCafeteriaMenuItems=" + auCafeteriaMenuItems +
                ", cafeteriaUrl='" + cafeteriaUrl + '\'' +
                '}';
    }
}
