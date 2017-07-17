package www.uni_weimar.de.au.models;

import com.google.common.base.MoreObjects;
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
    private String title;
    private String url;
    private String autype;
    private String topLevelHeaderName;
    private int headerLevel;
    private RealmList<AUFacultyHeader> auFacultyHeaderLis;
    private RealmList<AUFacultyEvent> auFacultyEvents;
    private AUFacultyHeader topLevelHeader;


    public AUFacultyHeader() {

    }

    public AUFacultyHeader(AUFacultyHeader auItem) {
        this.title = auItem.getTitle();
        this.url = auItem.getUrl();
        this.autype = auItem.getAutype();
        this.headerLevel = auItem.getHeaderLevel();
        this.auFacultyHeaderLis = auItem.getAuFacultyHeaderList();
        this.auFacultyEvents = auItem.getAuFacultyEvents();
        this.topLevelHeader = auItem.getTopLevelHeader();
        this.topLevelHeaderName = auItem.getTopLevelHeaderName();
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

    public RealmList<AUFacultyEvent> getAuFacultyEvents() {
        return auFacultyEvents;
    }

    public void setAuFacultyEvents(RealmList<AUFacultyEvent> auFacultyEvents) {
        this.auFacultyEvents = auFacultyEvents;
    }

    public int getHeaderLevel() {
        return headerLevel;
    }

    public void setHeaderLevel(int headerLevel) {
        this.headerLevel = headerLevel;
    }

    public String getAutype() {
        return autype;
    }

    public AUFacultyHeader getTopLevelHeader() {
        return topLevelHeader;
    }

    public void setTopLevelHeader(AUFacultyHeader topLevelHeader) {
        this.topLevelHeader = topLevelHeader;
    }

    public String getTopLevelHeaderName() {
        return topLevelHeaderName;
    }

    public void setTopLevelHeaderName(String topLevelHeaderName) {
        this.topLevelHeaderName = topLevelHeaderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUFacultyHeader)) return false;
        AUFacultyHeader that = (AUFacultyHeader) o;
        return Objects.equal(getTitle(), that.getTitle()) &&
                Objects.equal(getUrl(), that.getUrl()) &&
                Objects.equal(autype, that.autype);
    }


    public void setAUFacultyType(String autype) {
        this.autype = autype;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTitle(), getUrl(), autype);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("url", url)
                .add("autype", autype)
                .add("auFacultyHeaderLis", auFacultyHeaderLis)
                .add("topLevelHeader", topLevelHeader)
                .toString();
    }


}
