package www.uni_weimar.de.au.parsers.impl;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.application.AUApplicationConfiguration;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

/**
 * Created by ndovhuy on 15.06.2017.
 */

public class AUNewsFeedParser implements AUParser<AUNewsFeed> {

    private static String TAG = AUNewsFeedParser.class.getSimpleName();
    private String newsFeedUrl;

    public AUNewsFeedParser() {
        Context context = AUApplicationConfiguration.getContext();
        if (context != null)
            newsFeedUrl = context.getApplicationContext().getString(R.string.ALL_NEWS);
    }


    @Override
    public List<AUNewsFeed> parseAU(String url) throws AUParseException {
        List<AUNewsFeed> auNewsFeeds = new ArrayList<>();
        if (url == null) {
            url = newsFeedUrl;
        }
        Document document;
        Set<String> newsFeedCategories = new HashSet<>();
        try {
            document = Jsoup.connect(url).get();
            Elements newsFeedItems = document.getElementsByTag(AUItem.ITEM);
            for (Element newsFeedItem : newsFeedItems) {
                AUNewsFeed auNewsFeed = new AUNewsFeed();
                String itemTitle = newsFeedItem.getElementsByTag(AUItem.TITLE).text();
                auNewsFeed.setTitle(itemTitle);
                String itemLink = newsFeedItem.getElementsByTag(AUItem.LINK).text();
                auNewsFeed.setLink(itemLink);
                String itemAuthor = newsFeedItem.getElementsByTag(AUItem.AUTHOR).text();
                auNewsFeed.setAuthor(itemAuthor);
                String itemCategory = newsFeedItem.getElementsByTag(AUItem.CATEGORY).text();
                auNewsFeed.setCategory(itemCategory);
                newsFeedCategories.add(itemCategory);
                String itemDescription = newsFeedItem.getElementsByTag(AUItem.DESCR).text();
                auNewsFeed.setDesciption(itemDescription);
                String itemPubDate = newsFeedItem.getElementsByTag(AUItem.PUB_DATE).text();
                auNewsFeed.setPubDate(itemPubDate);
                Elements itemImgElements = newsFeedItem.getElementsByTag(AUItem.ENCLOSURE);
                String itemImgUrl = itemImgElements.attr(AUItem.IMG_URL);
                auNewsFeed.setImgUrl(itemImgUrl);
                auNewsFeeds.add(auNewsFeed);
            }
            Log.v(TAG, newsFeedCategories.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            throw new AUParseException(e.getMessage());
        }
        return auNewsFeeds;
    }


    public void setNewsFeedUrl(String newsFeedUrl) {
        this.newsFeedUrl = newsFeedUrl;
    }

    public String getNewsFeedUrl() {
        return newsFeedUrl;
    }

    @Override
    public List<AUNewsFeed> parseAU() {
        return null;
    }
}
