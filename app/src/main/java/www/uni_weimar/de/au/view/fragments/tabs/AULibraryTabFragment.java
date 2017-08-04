package www.uni_weimar.de.au.view.fragments.tabs;

import android.os.Bundle;

import www.uni_weimar.de.au.models.AUMainMenuTab;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AULibraryTabFragment extends AUMainMenuTabFragment {


    public static AULibraryTabFragment newInstance(AUMainMenuTab auMainMenuTab) {
        AULibraryTabFragment fragment = new AULibraryTabFragment();
        fragment.auMainMenuTab = auMainMenuTab;
        fragment.mainMenuTabTitle = auMainMenuTab.getTitle();
        return fragment;
    }
}
