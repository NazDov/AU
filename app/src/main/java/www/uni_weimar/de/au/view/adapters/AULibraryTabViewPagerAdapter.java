package www.uni_weimar.de.au.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import www.uni_weimar.de.au.view.fragments.AULibrariesFragment;

/**
 * Created by nazar on 06.08.17.
 */

public class AULibraryTabViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int tabCount;

    public AULibraryTabViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        return AULibrariesFragment.newInstance();
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
