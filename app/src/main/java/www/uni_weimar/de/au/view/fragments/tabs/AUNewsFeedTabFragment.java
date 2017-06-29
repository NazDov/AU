package www.uni_weimar.de.au.view.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.view.adapters.AUNewsFeedTabFragmentViewPagerAdapter;

/**
 * Created by ndovhuy on 17.06.2017.
 */

public class AUNewsFeedTabFragment extends AUMainMenuTabFragment implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.news_feed_tab_layout)
    TabLayout auNewsFeedTabLayout;
    @InjectView(R.id.newsFeedTextView)
    TextView textView;
    PagerAdapter auTabFragmentViewPagerAdapter;
    @InjectView(R.id.news_feed_tab_view_pager)
    ViewPager auNewsFeedTabsViewPager;


    public static AUNewsFeedTabFragment newInstance(AUMainMenuTab auMainMenuTab) {
        Bundle args = new Bundle();
        AUNewsFeedTabFragment fragment = new AUNewsFeedTabFragment();
        fragment.auMainMenuTab = auMainMenuTab;
        fragment.mainMenuTabTitle = auMainMenuTab.getTitle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_news_feed_tabs_layout, container, false);
        ButterKnife.inject(this, rootView);
        initAuNewsFeedTabs();
        return rootView;
    }

    private void initAuNewsFeedTabs() {
        List<AUMainMenuItem> auMainMenuItemList = auMainMenuTab.getAUMainMenuItemList();
        for (AUMainMenuItem auMainMenuItem : auMainMenuItemList) {
            auNewsFeedTabLayout.addTab(auNewsFeedTabLayout.newTab().setText(auMainMenuItem.getTitle().replace("All","").trim()));
        }
        auTabFragmentViewPagerAdapter = new AUNewsFeedTabFragmentViewPagerAdapter(getChildFragmentManager(), auNewsFeedTabLayout.getTabCount());
        auNewsFeedTabsViewPager.setAdapter(auTabFragmentViewPagerAdapter);
        auNewsFeedTabsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(auNewsFeedTabLayout));
        auNewsFeedTabLayout.addOnTabSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        auNewsFeedTabsViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
