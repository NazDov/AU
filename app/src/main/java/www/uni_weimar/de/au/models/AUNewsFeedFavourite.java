package www.uni_weimar.de.au.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ndovhuy on 30.06.2017.
 */

public class AUNewsFeedFavourite extends RealmObject implements AUItem  {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String link;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUNewsFeedFavourite)) return false;

        AUNewsFeedFavourite that = (AUNewsFeedFavourite) o;

        if (!getId().equals(that.getId())) return false;
        return getLink().equals(that.getLink());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getLink().hashCode();
        return result;
    }
}
