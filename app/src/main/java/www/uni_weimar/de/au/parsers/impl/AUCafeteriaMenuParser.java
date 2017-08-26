package www.uni_weimar.de.au.parsers.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import io.realm.RealmList;
import www.uni_weimar.de.au.models.AUCafeteriaMenu;
import www.uni_weimar.de.au.models.AUCafeteriaMenuItem;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nazar on 20.08.17.
 */

public class AUCafeteriaMenuParser implements AUParser<AUCafeteriaMenu> {

    private static final String MENU_ITEM_HEADER = "c-schedule__header";
    private static final String MENU_ITEM_SCHEDULE = "c-schedule__item";
    private static final String MENU_ITEM_SCHEDULE_HEADER = ".c-schedule__header span";
    private static final String MENU_SCHEDULE_LIST_ITEM = "c-schedule__list-item";
    private static final String MENU_ITEM_TAG = ".stwm-artname";
    private static final String MENU_ITEM_DESCRIPTION = ".js-schedule-dish-description";
    private final String url;

    private AUCafeteriaMenuParser(String url) {
        this.url = url;
    }

    public static AUCafeteriaMenuParser of(String url) {
        checkNotNull(url);
        return new AUCafeteriaMenuParser(url);
    }


    @Override
    public List<AUCafeteriaMenu> parseAU(String url) throws AUParseException {
        checkNotNull(url);
        Document htmlDoc;
        List<AUCafeteriaMenu> auCafeteriaMenuList = new RealmList<>();
        AUCafeteriaMenu auCafeteriaMenu;
        Elements menuItemSchedules;
        try {
            htmlDoc = Jsoup.connect(url).get();
            menuItemSchedules = htmlDoc.getElementsByClass(MENU_ITEM_SCHEDULE);
            for (Element menuItemSchedule : menuItemSchedules) {
                auCafeteriaMenu = new AUCafeteriaMenu();
                String menuItemScheduleHeader = menuItemSchedule.select(MENU_ITEM_SCHEDULE_HEADER).text();
                RealmList<AUCafeteriaMenuItem> auCafeteriaMenuItems = parseAUCafeteriaMenuItems(menuItemSchedule);
                auCafeteriaMenu.setAuCafeteriaMenuDayTitle(menuItemScheduleHeader);
                auCafeteriaMenu.setAuCafeteriaMenuItems(auCafeteriaMenuItems);
                auCafeteriaMenu.setCafeteriaUrl(url);
                auCafeteriaMenuList.add(auCafeteriaMenu);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }
        return auCafeteriaMenuList;
    }

    private RealmList<AUCafeteriaMenuItem> parseAUCafeteriaMenuItems(Element menuItemSchedule) {
        RealmList<AUCafeteriaMenuItem> auCafeteriaMenuItems = new RealmList<>();
        Elements menuScheduleListItems = menuItemSchedule.getElementsByClass(MENU_SCHEDULE_LIST_ITEM);
        for (Element menuScheduleItem : menuScheduleListItems) {
            AUCafeteriaMenuItem auCafeteriaMenuItem = new AUCafeteriaMenuItem();
            String menuItemTag = menuScheduleItem.select(MENU_ITEM_TAG).text();
            String menuItemDescription = menuScheduleItem.select(MENU_ITEM_DESCRIPTION).text();
            auCafeteriaMenuItem.setAuMenuItemTag(menuItemTag);
            auCafeteriaMenuItem.setAuMenuItemDescription(menuItemDescription);
            auCafeteriaMenuItems.add(auCafeteriaMenuItem);
        }
        return auCafeteriaMenuItems;
    }

    @Override
    public List<AUCafeteriaMenu> parseAU() throws AUParseException {
        return parseAU(this.url);
    }
}
