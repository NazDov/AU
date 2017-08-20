package www.uni_weimar.de.au.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import www.uni_weimar.de.au.models.AUCafeteriaMenu;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaMenuItemFragment;

/**
 * Created by nazar on 20.08.17.
 */

public class AUCafeteriaMainMenuPagerAdapter extends FragmentStatePagerAdapter {

    private List<AUCafeteriaMenuItemFragment> auCafeteriaMenuItemFragments;

    public AUCafeteriaMainMenuPagerAdapter(FragmentManager fm, List<AUCafeteriaMenuItemFragment> auCafeteriaMenus) {
        super(fm);
        this.auCafeteriaMenuItemFragments = auCafeteriaMenus;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (hasItems()) {
            fragment = auCafeteriaMenuItemFragments.get(position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return hasItems() ? auCafeteriaMenuItemFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = null;
        if (hasItems()) {
            pageTitle = auCafeteriaMenuItemFragments
                    .get(position)
                    .getAuCafeteriaMenu()
                    .getAuCafeteriaMenuDayTitle().toUpperCase();
        }
        return pageTitle;
    }

    private boolean hasItems() {
        return auCafeteriaMenuItemFragments != null && !auCafeteriaMenuItemFragments.isEmpty();
    }

    public void setAuCafeteriaMenuItemFragments(List<AUCafeteriaMenuItemFragment> auCafeteriaMenuItemFragments) {
        this.auCafeteriaMenuItemFragments = auCafeteriaMenuItemFragments;
    }
}
