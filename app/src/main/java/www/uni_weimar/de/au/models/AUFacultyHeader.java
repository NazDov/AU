package www.uni_weimar.de.au.models;
import com.google.common.base.Objects;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ndovhuy on 07.07.2017.
 */
public class AUFacultyHeader extends RealmObject implements AUItem {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String title;
    private String url;
    private String autype;
    private RealmList<AUFacultyHeader> auFacultyHeaderLis;
    private AUFacultyHeader topLevelHeader;


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

    public RealmList<AUFacultyHeader> getAuFacultyHeaderList() {
        return auFacultyHeaderLis;
    }

    public void setAuFacultyHeaderLis(RealmList<AUFacultyHeader> auFacultyHeaderLis) {
        this.auFacultyHeaderLis = auFacultyHeaderLis;
    }

    public AUFacultyHeader getTopLevelHeader() {
        return topLevelHeader;
    }

    public void setTopLevelHeader(AUFacultyHeader topLevelHeader) {
        this.topLevelHeader = topLevelHeader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUFacultyHeader)) return false;
        AUFacultyHeader that = (AUFacultyHeader) o;
        return Objects.equal(getId(), that.getId()) &&
                Objects.equal(getTitle(), that.getTitle()) &&
                Objects.equal(getUrl(), that.getUrl()) &&
                Objects.equal(autype, that.autype);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getTitle(), getUrl(), autype);
    }

    @Override
    public String toString() {
        return "AUFacultyHeader{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", auFacultyHeaderLis=" + auFacultyHeaderLis +
                '}';
    }

    public void setAUFacultyType(String autype) {
        this.autype = autype;
    }
}
