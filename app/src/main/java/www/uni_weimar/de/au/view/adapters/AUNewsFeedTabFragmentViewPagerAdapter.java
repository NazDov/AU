package www.uni_weimar.de.au.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import www.uni_weimar.de.au.view.fragments.AUAllNewsFeedFragment;
import www.uni_weimar.de.au.view.fragments.AUMyNewsFeedFragment;

/**
 * Created by ndovhuy on 19.06.2017.
 */
public class AUNewsFeedTabFragmentViewPagerAdapter extends FragmentStatePagerAdapter  {

    private int tabCount;

    public AUNewsFeedTabFragmentViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return AUAllNewsFeedFragment.newInstance();
            case 1:
                return new AUMyNewsFeedFragment();
            default:
                return AUAllNewsFeedFragment.newInstance();
        }

    }



    @Override
    public int getCount() {
        return 2;
    }
}
