package www.uni_weimar.de.au.parsers.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmList;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUCafeteriaMenu;
import www.uni_weimar.de.au.models.AUCafeteriaMenuItem;
import www.uni_weimar.de.au.models.AUDays;
import www.uni_weimar.de.au.parsers.exception.AUParseException;
import www.uni_weimar.de.au.parsers.inter.AUParser;

import static com.google.common.base.Preconditions.checkNotNull;
import static www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory.getDefaultLink;

/**
 * Created by user on 06.10.17.
 */

public class AUCafeteriaWeimarMenuParser implements AUParser<AUCafeteriaMenu> {

    private static final String ESCAPE_REGEX = "";
    private static final String MENU_TABBERS = "tabbertab";
    private static final int MENU_DAY_NAV_BAR_INDX = 0;
    private static final String SELECT_NAME_SEL_WEEK = "select[name='selWeek']";
    private static final String SELECTED = "selected";
    private String url;

    public AUCafeteriaWeimarMenuParser() {

    }

    private AUCafeteriaWeimarMenuParser(String url) {
        this.url = url;
    }

    public static AUCafeteriaWeimarMenuParser of(String url) {
        url = checkNotNull(url);
        return new AUCafeteriaWeimarMenuParser(url);
    }

    @Override
    public List<AUCafeteriaMenu> parseAU(String url) throws AUParseException {
        List<AUCafeteriaMenu> cafeteriaMenus = new RealmList<>();
        try {
            Document htmlDoc = Jsoup.connect(url).execute().parse();
            Elements menuTabbers = htmlDoc.getElementsByClass(MENU_TABBERS);
            Elements selWeek = htmlDoc.select(SELECT_NAME_SEL_WEEK);
            List<String> dateItems = getAllDateItems(selWeek);
            for (Element menuDay : menuTabbers) {
                AUCafeteriaMenu cafeteriaMenu = new AUCafeteriaMenu();
                String dayTitle = menuDay.attr("title");
                dayTitle = composeDayTitleWithDate(dayTitle, dateItems);
                cafeteriaMenu.setAuCafeteriaMenuDayTitle(dayTitle);
                cafeteriaMenu.setAuCafeteriaMenuItems(parseCafeteriaMenuItems(menuDay));
                cafeteriaMenu.setCafeteriaUrl(getDefaultLink(R.string.DEFAULT_CAFETERIA_URL));
                cafeteriaMenus.add(cafeteriaMenu);
            }
        } catch (IOException e) {
            throw new AUParseException(e.getMessage());
        }
        return cafeteriaMenus;
    }

    private RealmList<AUCafeteriaMenuItem> parseCafeteriaMenuItems(Element menuDay) {
        RealmList<AUCafeteriaMenuItem> cafeteriaMenuItems = new RealmList<>();
        Elements menuTable = menuDay.getElementsByTag("table");
        Elements menuTableRows = menuTable.get(0).children().select("tr");
        for (int index = 0; index < menuTableRows.size(); index++) {
            if (index == 0) continue;
            AUCafeteriaMenuItem cafeteriaMenuItem = new AUCafeteriaMenuItem();
            Element menuTableRowItem = menuTableRows.get(index);
            Elements menuTableRowItems = menuTableRowItem.select("td");
            if (!menuTableRowItems.isEmpty() && menuTableRowItems.size() >= 3) {
                cafeteriaMenuItem.setAuMenuItemTag(menuTableRowItems.get(0).text());
                cafeteriaMenuItem.setAuMenuItemDescription(menuTableRowItems.get(1).text());
                cafeteriaMenuItem.setAuMenuItemPrice(menuTableRowItems.get(2).text());
            }
            cafeteriaMenuItems.add(cafeteriaMenuItem);
        }
        return cafeteriaMenuItems;
    }

    private String composeDayTitleWithDate(String dayTitle, List<String> dateItems) {
        StringBuilder dayTitleWithDateBuilder = new StringBuilder();
        switch (dayTitle) {
            case "Montag":
                dayTitleWithDateBuilder.append(dayTitle).append(",").append(dateItems.get(0));
                break;
            case "Dienstag":
                dayTitleWithDateBuilder.append(dayTitle).append(",").append(dateItems.get(1));
                break;
            case "Mittwoch":
                dayTitleWithDateBuilder.append(dayTitle).append(",").append(dateItems.get(2));
                break;
            case "Donnerstag":
                dayTitleWithDateBuilder.append(dayTitle).append(",").append(dateItems.get(3));
                break;
            case "Freitag":
                dayTitleWithDateBuilder.append(dayTitle).append(",").append(dateItems.get(4));
                break;
        }
        return dayTitleWithDateBuilder.toString();
    }

    private List<String> getAllDateItems(Elements selWeek) {
        List<String> dateItems = new ArrayList<>();
        Element currentWeekElement = getSelectedWeekSchedule(selWeek);
        String dateRange = currentWeekElement.text();
        String[] dateRanges = dateRange.split("\\(")[1].split("-");
        String dateRangeFrom = dateRanges[0].trim();
        String dateRangeTo = dateRanges[1].replaceAll(Pattern.quote(")"), "").trim();
        String dayFromPart = dateRangeFrom.substring(0, 2);
        String dayToPart = dateRangeTo.substring(0, 2);
        String monthYearPart = dateRangeFrom.substring(2);
        int dayFrom = formatToInt(dayFromPart);
        int dayTo = formatToInt(dayToPart);
        StringBuilder dateItemBuilder = new StringBuilder();
        String dateItemFirst = dateItemBuilder.append(dateRangeFrom).toString();
        dateItems.add(dateItemFirst);
        dayFrom += 1;
        for (; dayFrom < dayTo; dayFrom++) {
            dateItemBuilder = new StringBuilder();
            String dayFromFormatted = String.format("%s", dayFrom).length() == 1 ? "0" + dayFrom : "" + dayFrom;
            String dateItem = dateItemBuilder
                    .append(dayFromFormatted)
                    .append(monthYearPart)
                    .toString();
            dateItems.add(dateItem);
        }
        dateItemBuilder = new StringBuilder();
        String dateItemLast = dateItemBuilder.append(dateRangeTo).toString();
        dateItems.add(dateItemLast);
        return dateItems;
    }

    private Element getSelectedWeekSchedule(Elements selWeek) {
        Element selWeekElements = selWeek.get(0);
        Elements weekScheduleOptions = selWeekElements.children();
        Element selectedWeekSchedule = selWeekElements.child(1);
        for (Element weekScheduleOption : weekScheduleOptions) {
            if (weekScheduleOption.hasAttr(SELECTED)) {
                selectedWeekSchedule = weekScheduleOption;
            }
        }
        return selectedWeekSchedule;
    }

    private int formatToInt(String dayFromPart) {
        int intDay;
        if (dayFromPart.charAt(0) == '0') {
            intDay = Integer.parseInt(dayFromPart.substring(1));
        } else {
            intDay = Integer.parseInt(dayFromPart);
        }

        return intDay;
    }

    @Override
    public List<AUCafeteriaMenu> parseAU() throws AUParseException {
        return parseAU(url);
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}
