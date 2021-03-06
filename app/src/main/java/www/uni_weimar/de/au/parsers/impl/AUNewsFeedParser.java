package www.uni_weimar.de.au.parsers.impl;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.models.AURssChannel;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;
import www.uni_weimar.de.au.utils.StaticDateUtils;

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
                auNewsFeed.setTimeElapsed(getFormattedPubDate(itemPubDate));
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

    private double getFormattedPubDate(String itemPubDate) {
        String pubDate = itemPubDate.replace("CEST", "").trim();
        String[] dates = pubDate.split(" ");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dates[1]));
        cal.set(Calendar.MONTH, StaticDateUtils.months.get(dates[2]));
        cal.set(Calendar.YEAR, Integer.valueOf(dates[3]));
        String[] hours = dates[4].split(":");
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hours[0]));
        cal.set(Calendar.MINUTE, Integer.valueOf(hours[1]));
        cal.set(Calendar.SECOND, Integer.valueOf(hours[2]));
        Date dateOfPublication = cal.getTime();
        return getElapsedTime(dateOfPublication, new Date());
    }

    private double getElapsedTime(Date dateOfPublication, Date currentDate) {
        long timeElapsed = currentDate.getTime() - dateOfPublication.getTime();
        long diffInHours = TimeUnit.MILLISECONDS.toHours(timeElapsed);
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed - TimeUnit.HOURS.toMillis(diffInHours));
        double dih = diffInHours * 1.0 / 24 * 1.0;
        double dim = (diffInMinutes * 1.0 / 60 * 1.0) / 24 * 1.0;
        return dih + dim;
    }

    @Override
    public List<AUNewsFeed> parseAU() throws AUParseException {
        return parseAU(newsFeedUrl);
    }
}
