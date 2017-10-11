package www.uni_weimar.de.au.view.fragments;

import android.support.design.widget.TabLayout;

import www.uni_weimar.de.au.utils.AUNewsFeedStaticCategory;

/**
 * Created by user on 06.10.17.
 */

public class AUAllLMUNewsFeedFragment extends AUAllNewsFeedFragment {

    public static AUAllNewsFeedFragment newInstance() {
        return new AUAllLMUNewsFeedFragment();
    }

    @Override
    protected void initNewsFeedCategoryTabs() {
        newsFeedCategoryTabLayout.addTab(
                newsFeedCategoryTabLayout.newTab().setText(AUNewsFeedStaticCategory.ALL.toString()), 0);
        // specify 'favourite' news category menu
        newsFeedCategoryTabLayout
                .addTab(newsFeedCategoryTabLayout
                        .newTab()
                        .setText(AUNewsFeedStaticCategory.FAVOURITE.name()), 1);
        for (String rssChannelTabName : getRssChannelsTabNames()) {
            // do not instantiate this news source
            if (rssChannelTabName.equalsIgnoreCase("BWL Veranstaltungen")) {
                break;
            }
            TabLayout.Tab auMenuTab = newsFeedCategoryTabLayout.newTab();
            newsFeedCategoryTabLayout.addTab(auMenuTab.setText(rssChannelTabName));
        }
    }


    @Override
    protected void switchTab(TabLayout.Tab tab) {
        String categoryName = (String) tab.getText();
        int categoryPosition = tab.getPosition() != 0 ? tab.getPosition() - 2 : tab.getPosition();
        super.switchTabByCategoryNameAndPosition(categoryName, categoryPosition);
    }
}
