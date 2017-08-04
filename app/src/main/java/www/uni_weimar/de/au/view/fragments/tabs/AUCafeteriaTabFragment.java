package www.uni_weimar.de.au.view.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.view.adapters.AUCafeteriaTabViewPagerAdapter;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AUCafeteriaTabFragment extends AUMainMenuTabFragment implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.au_cafeteria_tab_layout)
    TabLayout auCafeteriaTabLayout;
    @InjectView(R.id.au_cafeteria_tab_view_pager)
    ViewPager auCafeteriaTabViewPager;
    PagerAdapter auCafeteriaViewPagerAdapter;

    public static AUCafeteriaTabFragment newInstance(AUMainMenuTab auMainMenuTab) {
        AUCafeteriaTabFragment fragment = new AUCafeteriaTabFragment();
        fragment.auMainMenuTab = auMainMenuTab;
        fragment.mainMenuTabTitle = auMainMenuTab.getTitle();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_cafeteria_tabs_layout, container, false);
        ButterKnife.inject(this, rootView);
        initAUCafeteriaTabs();
        return rootView;
    }

    private void initAUCafeteriaTabs() {
        List<AUMainMenuItem> auCafeteriaMenuItems = auMainMenuTab.getAuMainMenuItemList();
        for (AUMainMenuItem menuItem : auCafeteriaMenuItems) {
            auCafeteriaTabLayout.addTab(auCafeteriaTabLayout.newTab().setText(menuItem.getTitle()));
        }
        auCafeteriaViewPagerAdapter = new AUCafeteriaTabViewPagerAdapter(getChildFragmentManager(), auCafeteriaTabLayout.getTabCount());
        auCafeteriaTabViewPager.setAdapter(auCafeteriaViewPagerAdapter);
        auCafeteriaTabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(auCafeteriaTabLayout));
        auCafeteriaTabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        auCafeteriaTabViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
