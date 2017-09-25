package www.uni_weimar.de.au.view.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.view.adapters.AUScheduleTabFragmentViewPagerAdapter;

/**
 * Created by ndovhuy on 18.06.2017.
 */
public class AUScheduleTabFragment extends AUMainMenuTabFragment implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.au_schedule_tab_layout)
    TabLayout auScheduleTabLayout;
    @InjectView(R.id.au_schedule_tab_view_pager)
    ViewPager auScheduleViewPager;
    PagerAdapter auScheduleTabFragmentViewPagerAdapter;

    public static AUScheduleTabFragment newInstance(AUMainMenuTab auMainMenuTab) {
        Bundle args = new Bundle();
        AUScheduleTabFragment fragment = new AUScheduleTabFragment();
        fragment.mainMenuTabTitle = auMainMenuTab.getTitle();
        fragment.auMainMenuTab = auMainMenuTab;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_schedule_tabs_layout, container, false);
        ButterKnife.inject(this, rootView);
        initAUScheduleTabs();
        return rootView;
    }

    private void initAUScheduleTabs() {
        List<AUMainMenuItem> mainMenuItems = auMainMenuTab.getAuMainMenuItemList();
        for (AUMainMenuItem mainMenuItem: mainMenuItems) {
            auScheduleTabLayout.addTab(auScheduleTabLayout.newTab().setText(mainMenuItem.getTitle()));
        }
        auScheduleTabFragmentViewPagerAdapter = new AUScheduleTabFragmentViewPagerAdapter(getChildFragmentManager(), auScheduleTabLayout.getTabCount());
        auScheduleViewPager.setAdapter(auScheduleTabFragmentViewPagerAdapter);
        auScheduleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(auScheduleTabLayout));
        auScheduleTabLayout.addOnTabSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        auScheduleViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
