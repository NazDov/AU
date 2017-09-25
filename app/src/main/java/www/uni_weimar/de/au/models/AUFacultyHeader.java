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
    private String guid;
    private String url;
    private String title;
    private String autype;
    private String topLevelHeaderName;
    private int headerLevel;
    private RealmList<AUFacultyHeader> auFacultyHeaderLis;
    private AUFacultyHeader topLevelHeader;


    public AUFacultyHeader() {

    }

    public AUFacultyHeader(AUFacultyHeader auItem) {
        this.title = auItem.getTitle();
        this.url = auItem.getUrl();
        this.guid = auItem.getGuid();
        this.autype = auItem.getAutype();
        this.headerLevel = auItem.getHeaderLevel();
        this.auFacultyHeaderLis = auItem.getAuFacultyHeaderList();
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

    public void setAuFacultyHeaderList(RealmList<AUFacultyHeader> auFacultyHeaderLis) {
        this.auFacultyHeaderLis = auFacultyHeaderLis;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }


    public void setAUFacultyType(String autype) {
        this.autype = autype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AUFacultyHeader that = (AUFacultyHeader) o;
        return headerLevel == that.headerLevel &&
                Objects.equal(guid, that.guid) &&
                Objects.equal(url, that.url) &&
                Objects.equal(title, that.title) &&
                Objects.equal(autype, that.autype) &&
                Objects.equal(topLevelHeaderName, that.topLevelHeaderName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(guid, url, title, autype, topLevelHeaderName, headerLevel);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("guid", guid)
                .add("url", url)
                .add("title", title)
                .add("autype", autype)
                .add("topLevelHeaderName", topLevelHeaderName)
                .add("headerLevel", headerLevel)
                .add("auFacultyHeaderLis", auFacultyHeaderLis)
                .add("topLevelHeader", topLevelHeader)
                .toString();
    }
}
