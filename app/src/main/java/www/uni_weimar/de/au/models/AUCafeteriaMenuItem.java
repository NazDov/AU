package www.uni_weimar.de.au.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.primitives.Booleans;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazar on 20.08.17
 */

public class AUCafeteriaMenuItem extends RealmObject implements AUItem {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String auMenuItemTag;
    private String auMenuItemDescription;
    private String auMenuItemPrice;


    public AUCafeteriaMenuItem() {

    }

    public AUCafeteriaMenuItem(String auMenuItemTag, String auMenuItemDescription) {
        this.auMenuItemTag = auMenuItemTag;
        this.auMenuItemDescription = auMenuItemDescription;
    }

    public String getAuMenuItemTag() {
        return auMenuItemTag;
    }

    public String getAuMenuItemDescription() {
        return auMenuItemDescription;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuMenuItemTag(String auMenuItemTag) {
        this.auMenuItemTag = auMenuItemTag;
    }

    public void setAuMenuItemDescription(String auMenuItemDescription) {
        this.auMenuItemDescription = auMenuItemDescription;
    }

    public String getAuMenuItemPrice() {
        return auMenuItemPrice;
    }

    public void setAuMenuItemPrice(String auMenuItemPrice) {
        this.auMenuItemPrice = auMenuItemPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUCafeteriaMenuItem)) return false;
        AUCafeteriaMenuItem that = (AUCafeteriaMenuItem) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(getAuMenuItemTag(), that.getAuMenuItemTag()) &&
                Objects.equal(getAuMenuItemDescription(), that.getAuMenuItemDescription()) &&
                Objects.equal(getAuMenuItemPrice(), that.getAuMenuItemPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, getAuMenuItemTag(), getAuMenuItemDescription(), getAuMenuItemPrice());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("auMenuItemTag", auMenuItemTag)
                .add("auMenuItemDescription", auMenuItemDescription)
                .add("auMenuItemPrice", auMenuItemPrice)
                .toString();
    }
}
