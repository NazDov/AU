package www.uni_weimar.de.au.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by nazar on 06.08.17.
 */

public class AULibrariesFragment extends Fragment {

    public static AULibrariesFragment newInstance() {
        Bundle args = new Bundle();
        AULibrariesFragment fragment = new AULibrariesFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
