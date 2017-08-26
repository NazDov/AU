package www.uni_weimar.de.au.models;

import com.google.common.base.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ndovhuy on 26.08.2017.
 */

public class AUCafeteria extends RealmObject implements AUItem {

    @PrimaryKey
    private String url;
    private String name;
    private String location;

    public AUCafeteria() {

    }

    public AUCafeteria(String url, String name, String location) {
        this.url = url;
        this.name = name;
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AUCafeteria that = (AUCafeteria) o;
        return Objects.equal(url, that.url) &&
                Objects.equal(name, that.name) &&
                Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url, name, location);
    }

    @Override
    public String toString() {
        return "AUCafeteria{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
