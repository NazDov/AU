package www.uni_weimar.de.au.parsers.impl;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.models.AURssChannel;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 15.06.2017.
 */

public class AUNewsFeedParser implements AUParser<AUNewsFeed> {

    private static String TAG = AUNewsFeedParser.class.getSimpleName();
    private String newsFeedUrl;
    private AURssChannel auRssChannel;

    private AUNewsFeedParser(String url) {
        this.newsFeedUrl = url;
    }

    private AUNewsFeedParser(AURssChannel rssChannel) {
        this.auRssChannel = rssChannel;
        this.newsFeedUrl = rssChannel.getUrl();
    }

    public static AUNewsFeedParser of(String url) {
        url = checkNotNull(url);
        return new AUNewsFeedParser(url);
    }

    public static AUParser<AUNewsFeed> of(AURssChannel rssChannel) {
        rssChannel = checkNotNull(rssChannel);
        return new AUNewsFeedParser(rssChannel);
    }

    public List<AUNewsFeed> parseAU(AURssChannel auRssChannel) throws AUParseException {
        this.auRssChannel = checkNotNull(auRssChannel);
        return parseAU(auRssChannel.getUrl());

    }

    @Override
    public List<AUNewsFeed> parseAU(String url) throws AUParseException {
        List<AUNewsFeed> auNewsFeeds = new ArrayList<>();
        if (url == null) {
            url = newsFeedUrl;
        }
        Document document;
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
                auNewsFeed.setCategoryUrl(url);
                auNewsFeed.setCategoryName(auRssChannel != null ? auRssChannel.getTitle() : "All");
                String itemDescription = newsFeedItem.getElementsByTag(AUItem.DESCR).text();
                auNewsFeed.setDesciption(itemDescription);
                String itemPubDate = newsFeedItem.getElementsByTag(AUItem.PUB_DATE).text();
                auNewsFeed.setPubDate(itemPubDate);
                Elements itemImgElements = newsFeedItem.getElementsByTag(AUItem.ENCLOSURE);
                String itemImgUrl = itemImgElements.attr(AUItem.IMG_URL);
                auNewsFeed.setImgUrl(itemImgUrl);
                auNewsFeeds.add(auNewsFeed);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            throw new AUParseException(e.getMessage());
        }
        return auNewsFeeds;
    }

    @Override
    public List<AUNewsFeed> parseAU() throws AUParseException {
        return parseAU(newsFeedUrl);
    }
}
