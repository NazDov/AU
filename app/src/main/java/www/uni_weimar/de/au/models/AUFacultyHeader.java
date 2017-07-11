package www.uni_weimar.de.au.models;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ndovhuy on 07.07.2017.
 */
public class AUFacultyHeader extends RealmObject implements AUItem {

    @PrimaryKey
    private String id;
    private String title;
    private String url;
    private RealmList<AUFacultyHeader> auFacultyHeaderLis;


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

    public RealmList<AUFacultyHeader> getAuFacultyHeaderLis() {
        return auFacultyHeaderLis;
    }

    public void setAuFacultyHeaderLis(RealmList<AUFacultyHeader> auFacultyHeaderLis) {
        this.auFacultyHeaderLis = auFacultyHeaderLis;
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
}
