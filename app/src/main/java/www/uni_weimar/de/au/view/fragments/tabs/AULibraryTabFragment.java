package www.uni_weimar.de.au.view.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import www.uni_weimar.de.au.view.adapters.AULibraryTabViewPagerAdapter;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AULibraryTabFragment extends AUMainMenuTabFragment implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.au_library_tab_layout)
    TabLayout auLibraryTabLayout;
    @InjectView(R.id.au_library_tab_view_pager)
    ViewPager auLibraryTabViewPager;
    PagerAdapter auLibraryTabPagerAdapter;


    public static AULibraryTabFragment newInstance(AUMainMenuTab auMainMenuTab) {
        AULibraryTabFragment fragment = new AULibraryTabFragment();
        fragment.auMainMenuTab = auMainMenuTab;
        fragment.mainMenuTabTitle = auMainMenuTab.getTitle();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_library_tabs_layout, container, false);
        ButterKnife.inject(this, rootView);
        initAULibraryTabs();
        return rootView;
    }

    private void initAULibraryTabs() {
        List<AUMainMenuItem> mainMenuItemList = auMainMenuTab.getAuMainMenuItemList();
        for (AUMainMenuItem auMainMenuItem : mainMenuItemList) {
            auLibraryTabLayout.addTab(auLibraryTabLayout.newTab().setText(auMainMenuItem.getTitle()));
        }
        auLibraryTabPagerAdapter = new AULibraryTabViewPagerAdapter(getChildFragmentManager(), auLibraryTabLayout.getTabCount());
        auLibraryTabLayout.addOnTabSelectedListener(this);
        auLibraryTabViewPager.setAdapter(auLibraryTabPagerAdapter);
        auLibraryTabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(auLibraryTabLayout));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        auLibraryTabViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
