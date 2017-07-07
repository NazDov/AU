package www.uni_weimar.de.au.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ndovhuy on 07.07.2017.
 */
public class AUFaculty extends RealmObject implements AUItem {

    private static String AUTYPE = "ueb";
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String title;
    private String url;
    private RealmList<AUFacultyHeader> auFacultyHeaders;
    private RealmList<AUFacultyEvent> auFacultyEvents;



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
}
