package www.uni_weimar.de.au.view.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUCafeteria;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.view.adapters.AUCafeteriaTabViewPagerAdapter;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaListFragment;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaMenuProgramFragment;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaMyFavouritesFragment;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AUCafeteriaTabFragment extends AUMainMenuTabFragment implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.au_cafeteria_tab_layout)
    TabLayout auCafeteriaTabLayout;
    @InjectView(R.id.au_cafeteria_tab_view_pager)
    ViewPager auCafeteriaTabViewPager;
    private AUCafeteriaTabViewPagerAdapter auCafeteriaViewPagerAdapter;
    private AUCafeteriaTabFragmentSwitcher tabFragmentSwitcher;

    public static AUCafeteriaTabFragment newInstance(AUMainMenuTab auMainMenuTab) {
        AUCafeteriaTabFragment fragment = new AUCafeteriaTabFragment();
        fragment.auMainMenuTab = auMainMenuTab;
        fragment.mainMenuTabTitle = auMainMenuTab.getTitle();
        return fragment;
    }


    public class AUCafeteriaTabFragmentSwitcher {
        private static final int DEFAULT_REPLACE_POSITION = 0;

        public void switchToCafeteriaMenuProgramFragment(AUCafeteria auCafeteria) {
            auCafeteriaViewPagerAdapter.replaceFragmentItem(DEFAULT_REPLACE_POSITION, AUCafeteriaMenuProgramFragment.newInstance(auCafeteria, this));
        }

        public void switchToCafeteriaListFragment() {
            auCafeteriaViewPagerAdapter.replaceFragmentItem(DEFAULT_REPLACE_POSITION, AUCafeteriaListFragment.newInstance(this));
        }
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
        tabFragmentSwitcher = new AUCafeteriaTabFragmentSwitcher();
        List<AUMainMenuItem> auCafeteriaMenuItems = auMainMenuTab.getAuMainMenuItemList();
        for (AUMainMenuItem menuItem : auCafeteriaMenuItems) {
            auCafeteriaTabLayout.addTab(auCafeteriaTabLayout.newTab().setText(menuItem.getTitle()));
        }
        auCafeteriaViewPagerAdapter = new AUCafeteriaTabViewPagerAdapter(getChildFragmentManager(), getAUCafeteriaTabFragments());
        auCafeteriaTabViewPager.setAdapter(auCafeteriaViewPagerAdapter);
        auCafeteriaTabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(auCafeteriaTabLayout));
        auCafeteriaTabLayout.addOnTabSelectedListener(this);
    }

    private List<Fragment> getAUCafeteriaTabFragments() {
        List<Fragment> auCafeteriaTabFragments = new ArrayList<>();
        auCafeteriaTabFragments.add(AUCafeteriaListFragment.newInstance(tabFragmentSwitcher));
        auCafeteriaTabFragments.add(AUCafeteriaMyFavouritesFragment.newInstance());
        return auCafeteriaTabFragments;
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
