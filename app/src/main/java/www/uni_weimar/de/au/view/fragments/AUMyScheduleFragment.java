package www.uni_weimar.de.au.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEventSchedule;
import www.uni_weimar.de.au.service.impl.AUEventScheduleFavouriteContentRequestService;
import www.uni_weimar.de.au.view.adapters.AUMyEventSchedulesRecyclerViewAdapter;

/**
 * Created by nazar on 13.07.17.
 */

public class AUMyScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.au_my_schedule_recycler_view)
    RecyclerView eventScheduleRecyclerView;
    @InjectView(R.id.au_my_schedule_swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    AUMyEventSchedulesRecyclerViewAdapter eventSchedulesRecyclerViewAdapter;
    AUEventScheduleFavouriteContentRequestService eventScheduleFavouriteContentRequestService;
    Realm mRealm;
    List<AUFacultyEventSchedule> facultyEventSchedules;

    public static AUMyScheduleFragment newInstance() {
        Bundle args = new Bundle();
        AUMyScheduleFragment fragment = new AUMyScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.au_my_schedule_layout, container, false);
        ButterKnife.inject(this, root);
        refreshLayout.setOnRefreshListener(this);
        initScheduleFragmentServicesAndComponents();
        initFavouriteEventScheduleItemsList();
        return root;
    }

    private void initScheduleFragmentServicesAndComponents() {
        mRealm = Realm.getDefaultInstance();
        eventScheduleFavouriteContentRequestService = new AUEventScheduleFavouriteContentRequestService(mRealm);
        eventScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventSchedulesRecyclerViewAdapter = new AUMyEventSchedulesRecyclerViewAdapter(facultyEventSchedules);
        eventScheduleRecyclerView.setAdapter(eventSchedulesRecyclerViewAdapter);
    }

    private void initFavouriteEventScheduleItemsList() {
        eventScheduleFavouriteContentRequestService.notifyContentOnCacheUpdate(this::onCacheUpdate);
    }

    @Override
    public void onResume() {
        super.onResume();
        initFavouriteEventScheduleItemsList();
    }

    public void onCacheUpdate(List<AUFacultyEventSchedule> content) {
        facultyEventSchedules = content;
        if (facultyEventSchedules.isEmpty()) {
            Toast.makeText(getContext(), "no items found", Toast.LENGTH_SHORT).show();
        }
        eventSchedulesRecyclerViewAdapter.setEventSchedules(facultyEventSchedules);
        eventSchedulesRecyclerViewAdapter.notifyDataSetChanged();
        cancelRefreshCircleIfActive();
    }

    private void cancelRefreshCircleIfActive() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public void onRefresh() {
        initFavouriteEventScheduleItemsList();
    }
}

