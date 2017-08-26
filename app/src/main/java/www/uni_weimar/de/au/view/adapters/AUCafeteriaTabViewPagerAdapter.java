package www.uni_weimar.de.au.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.*;

import www.uni_weimar.de.au.models.AUCafeteria;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaListFragment;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaMenuProgramFragment;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaMyFavouritesFragment;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AUCafeteriaTabViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragments;

    public AUCafeteriaTabViewPagerAdapter(FragmentManager fm, List<Fragment> auCafeteriaFragments) {
        super(fm);
        mFragments = auCafeteriaFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        return fragment;
    }


    public void replaceFragmentItem(int position, Fragment fragment) {
        mFragments.set(position, fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

}
