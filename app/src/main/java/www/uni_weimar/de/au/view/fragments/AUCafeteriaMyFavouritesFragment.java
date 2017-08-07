package www.uni_weimar.de.au.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nazar on 06.08.17.
 */

public class AUCafeteriaMyFavouritesFragment extends Fragment {

    public static AUCafeteriaMyFavouritesFragment newInstance() {
        Bundle args = new Bundle();
        AUCafeteriaMyFavouritesFragment fragment = new AUCafeteriaMyFavouritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
