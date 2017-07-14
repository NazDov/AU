package www.uni_weimar.de.au.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import www.uni_weimar.de.au.view.fragments.AUAllScheduleFragment;
import www.uni_weimar.de.au.view.fragments.AUMyScheduleFragment;

/**
 * Created by nazar on 13.07.17.
 */

public class AUScheduleTabFragmentViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int tabCount;

    public AUScheduleTabFragmentViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return AUMyScheduleFragment.newInstance();
            case 1:
                return AUAllScheduleFragment.newInstance();
            default:
                return AUAllScheduleFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
