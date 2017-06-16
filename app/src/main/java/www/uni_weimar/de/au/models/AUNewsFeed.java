package www.uni_weimar.de.au.models;

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
    private String category;
    private String desciption;
    private String pubDate;
    private String imgUrl;


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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AUNewsFeed)) return false;

        AUNewsFeed that = (AUNewsFeed) o;

        if (getGuid() != null ? !getGuid().equals(that.getGuid()) : that.getGuid() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null)
            return false;
        if (getLink() != null ? !getLink().equals(that.getLink()) : that.getLink() != null)
            return false;
        if (getAuthor() != null ? !getAuthor().equals(that.getAuthor()) : that.getAuthor() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(that.getCategory()) : that.getCategory() != null)
            return false;
        if (getPubDate() != null ? !getPubDate().equals(that.getPubDate()) : that.getPubDate() != null)
            return false;
        return getImgUrl() != null ? getImgUrl().equals(that.getImgUrl()) : that.getImgUrl() == null;

    }

    @Override
    public int hashCode() {
        int result = getGuid() != null ? getGuid().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getLink() != null ? getLink().hashCode() : 0);
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getPubDate() != null ? getPubDate().hashCode() : 0);
        result = 31 * result + (getImgUrl() != null ? getImgUrl().hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "AUNewsFeed{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", guid='" + guid + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", desciption='" + desciption + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
