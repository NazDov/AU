package www.uni_weimar.de.au.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.view.adapters.AUCafeteriaMenuProgramRecyclerViewAdapter;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AUCafeteriaMenuProgramFragment extends Fragment {

    @InjectView(R.id.au_cafeteria_menu_program_recycler_view)
    RecyclerView auCafeteriaMenuProgramRecyclerView;
    AUCafeteriaMenuProgramRecyclerViewAdapter adapter;
    List<String> auCafeteriaMainMenuItems = new ArrayList<>();


    public static AUCafeteriaMenuProgramFragment newInstance() {
        Bundle args = new Bundle();
        AUCafeteriaMenuProgramFragment fragment = new AUCafeteriaMenuProgramFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_cafeteria_menu_program_layout, container, false);
        ButterKnife.inject(this, rootView);
        for (int i = 0; i < 10; i++) {
            auCafeteriaMainMenuItems.add("item");
        }
        adapter = new AUCafeteriaMenuProgramRecyclerViewAdapter();
        auCafeteriaMenuProgramRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auCafeteriaMenuProgramRecyclerView.setAdapter(adapter);
        return rootView;
    }
}
