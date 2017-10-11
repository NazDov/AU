package www.uni_weimar.de.au.models;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * this model represents menu.xml data row with autype=RSS
 */

public class AURssChannel extends RealmObject implements AUItem, Serializable {

    private static final long serialVersionUID = 1589463782L;

    @PrimaryKey
    private String url;
    private String title;

    public AURssChannel() {

    }

    public AURssChannel(String rssURL, String rssTitle) {
        this.url = rssURL;
        this.title = rssTitle;
    }

    public AURssChannel(AURssChannel auRssChannel) {
        this.url = auRssChannel.getUrl();
        this.title = auRssChannel.getTitle();
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
        if (o == null || getClass() != o.getClass()) return false;

        AURssChannel that = (AURssChannel) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return title != null ? title.equals(that.title) : that.title == null;

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AURssChannel{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
