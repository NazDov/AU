package www.uni_weimar.de.au.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import www.uni_weimar.de.au.view.fragments.AUCafeteriaMenuProgramFragment;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaMyFavouritesFragment;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AUCafeteriaTabViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int tabCount;

    public AUCafeteriaTabViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return AUCafeteriaMenuProgramFragment.newInstance();
            case 1:
                return AUCafeteriaMyFavouritesFragment.newInstance();
            default:
                return AUCafeteriaMenuProgramFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
