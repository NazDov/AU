package www.uni_weimar.de.au.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUCafeteriaMenu;
import www.uni_weimar.de.au.models.AUCafeteriaMenuItem;
import www.uni_weimar.de.au.view.adapters.AUCafeteriaMenuItemRecyclerViewAdapter;

/**
 * Created by nazar on 20.08.17.
 */

public class AUCafeteriaMenuItemFragment extends Fragment {

    @InjectView(R.id.auCafeteriaMenuRecyclerView)
    RecyclerView auCafeteriaMenuRecyclerView;
    private AUCafeteriaMenu auCafeteriaMenu;
    private List<AUCafeteriaMenuItem> auCafeteriaMenuItemList;
    private AUCafeteriaMenuItemRecyclerViewAdapter adapter;

    public static AUCafeteriaMenuItemFragment newInstance(AUCafeteriaMenu auCafeteriaMenu) {
        AUCafeteriaMenuItemFragment fragment = new AUCafeteriaMenuItemFragment();
        fragment.auCafeteriaMenu = auCafeteriaMenu;
        fragment.auCafeteriaMenuItemList = auCafeteriaMenu.getAuCafeteriaMenuItems();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_cafeteria_menu_schedule, container, false);
        ButterKnife.inject(this, rootView);
        auCafeteriaMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AUCafeteriaMenuItemRecyclerViewAdapter(getContext(), auCafeteriaMenuItemList);
        auCafeteriaMenuRecyclerView.setAdapter(adapter);
        return rootView;
    }

    public AUCafeteriaMenu getAuCafeteriaMenu() {
        return auCafeteriaMenu;
    }
}
