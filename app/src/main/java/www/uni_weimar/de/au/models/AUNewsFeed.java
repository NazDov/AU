package www.uni_weimar.de.au.models;

import com.google.common.base.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ndovhuy on 15.06.2017.
 */

public class AUNewsFeed extends RealmObject implements AUItem {

    @PrimaryKey
    private String link;
    private String title;
    private String guid;
    private String author;
    private String categoryUrl;
    private String categoryName;
    private String desciption;
    private String pubDate;
    private String imgUrl;
    private Double timeElapsed;


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getTimeElapsed() {
        return timeElapsed;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUNewsFeed)) return false;
        AUNewsFeed that = (AUNewsFeed) o;
        return Objects.equal(getLink(), that.getLink()) &&
                Objects.equal(getTitle(), that.getTitle()) &&
                Objects.equal(getGuid(), that.getGuid()) &&
                Objects.equal(getAuthor(), that.getAuthor()) &&
                Objects.equal(getCategoryUrl(), that.getCategoryUrl()) &&
                Objects.equal(getDesciption(), that.getDesciption()) &&
                Objects.equal(getPubDate(), that.getPubDate()) &&
                Objects.equal(getImgUrl(), that.getImgUrl())
                && Objects.equal(getTimeElapsed(), that.getTimeElapsed());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getLink(),
                getTitle(),
                getGuid(),
                getAuthor(),
                getCategoryUrl(),
                getDesciption(),
                getPubDate(),
                getImgUrl(),
                getTimeElapsed());
    }

    @Override
    public String toString() {
        return "AUNewsFeed{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", guid='" + guid + '\'' +
                ", author='" + author + '\'' +
                ", categoryUrl='" + categoryUrl + '\'' +
                ", desciption='" + desciption + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }


    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
