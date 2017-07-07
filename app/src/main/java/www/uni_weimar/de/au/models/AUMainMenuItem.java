package www.uni_weimar.de.au.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nazar on 12.06.17.
 */

public class AUMainMenuItem extends RealmObject implements AUItem {

    public static final String AUTYPE = "MENUITEM";

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String title;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUMainMenuItem)) return false;

        AUMainMenuItem that = (AUMainMenuItem) o;

        if (!getId().equals(that.getId())) return false;
        if (!getTitle().equals(that.getTitle())) return false;
        return getUrl().equals(that.getUrl());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getUrl().hashCode();
        return result;
    }
}
