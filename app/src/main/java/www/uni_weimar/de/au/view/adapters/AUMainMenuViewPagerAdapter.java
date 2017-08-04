package www.uni_weimar.de.au.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import www.uni_weimar.de.au.view.fragments.tabs.AUMainMenuTabFragment;

/**
 * Created by ndovhuy on 17.06.2017.
 */

public class AUMainMenuViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<AUMainMenuTabFragment> mainMenuTabFragments;

    public AUMainMenuViewPagerAdapter(FragmentManager fm, List<AUMainMenuTabFragment> auMainMenuTabFragments) {
        super(fm);
        this.mainMenuTabFragments = auMainMenuTabFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mainMenuTabFragments.get(position);
    }

    @Override
    public int getCount() {
        return mainMenuTabFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mainMenuTabFragments != null && !mainMenuTabFragments.isEmpty() ?
                mainMenuTabFragments.get(position).getMainMenuTabTitle().toUpperCase() : "";
    }
}
