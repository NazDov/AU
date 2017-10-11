package www.uni_weimar.de.au.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.service.impl.AUFacultyContentRequestService;
import www.uni_weimar.de.au.view.activity.AUEventItemDetailsActivity;
import www.uni_weimar.de.au.view.adapters.AUFacultyRecyclerViewAdapter;
import www.uni_weimar.de.au.view.components.AUSpinner;

import static www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory.*;

/**
 * Created by nazar on 13.07.17.
 */

public class AUAllScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.au_schedule_recycler_view)
    RecyclerView auScheduleRecyclerView;
    @InjectView(R.id.au_schedule_swipe_refresh)
    SwipeRefreshLayout auScheduleSwipeRefreshLayout;
    @InjectView(R.id.goToPreviousFacultyHeader)
    TextView auFacultyHeaderText;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;
    @InjectView(R.id.goToPreviousFacultyHeaderWrapper)
    LinearLayout goToPreviousFacultyHeader;
    @InjectView(R.id.auCoursesSpinnerId)
    ImageView spinnerView;
    AUSpinner auSpinner;
    AUFacultyContentRequestService auFacultyContentRequestService;
    AUFacultyRecyclerViewAdapter auFacultyRecyclerViewAdapter;
    private Realm realm;
    private List<AUFacultyHeader> auFacultyHeaderList;
    private AUFacultyHeader topLevelHeader;
    private List<AUFacultyHeader> cachedAUFAcultyHeaderList = new ArrayList<>();


    public static AUAllScheduleFragment newInstance() {
        Bundle args = new Bundle();
        AUAllScheduleFragment fragment = new AUAllScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.au_all_schedule_layout, container, false);
        ButterKnife.inject(this, root);
        auSpinner = new AUSpinner(spinnerView);
        auSpinner.startSpinner();
        progressBar.setVisibility(View.INVISIBLE);
        realm = Realm.getDefaultInstance();
        auFacultyHeaderText.setText(getDefaultLink(R.string.AU_FACULTY_TOP_HEADER));
        auScheduleSwipeRefreshLayout.setOnRefreshListener(this);
        auScheduleSwipeRefreshLayout.setRefreshing(true);
        auFacultyRecyclerViewAdapter = new AUFacultyRecyclerViewAdapter(getContext(), auFacultyHeaderList);
        auFacultyRecyclerViewAdapter.setOnItemClickListener(auItem -> {
            progressBar.setVisibility(View.VISIBLE);
            if (AUItem.EVENT.equals(auItem.getAutype())) {
                forwardToEventPage(auItem);
            } else {
                auSpinner.startSpinner();
                readAUFacultyDataFromCacheBy(auItem.getTitle());
                requestAUFacultyDataFromService(auItem);
            }

        });
        String defaultCoursesUrl = getDefaultLink(R.string.DEFAULT_COURSES_URL);
        auFacultyContentRequestService = AUFacultyContentRequestService.of(realm, defaultCoursesUrl);
        auFacultyContentRequestService
                .requestContent(defaultCoursesUrl, 1, null)
                .subscribe(this::onSuccess, this::onError);

        auFacultyContentRequestService.notifyContentOnCacheUpdate(content -> {
            auFacultyHeaderList = content;
            updateAUFacultyRecyclerViewAdapter();
            stopRefreshing();
        });
        auScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auScheduleRecyclerView.setAdapter(auFacultyRecyclerViewAdapter);
        goToPreviousFacultyHeader.setOnClickListener(v -> {
            if (!auFacultyHeaderList.isEmpty()) {
                topLevelHeader = auFacultyHeaderList.get(0).getTopLevelHeader();
            } else {
                AUFacultyHeader auFacultyHeader = cachedAUFAcultyHeaderList.isEmpty() ? null : cachedAUFAcultyHeaderList.get(0);
                topLevelHeader = auFacultyHeader != null ? auFacultyHeader.getTopLevelHeader() : null;
            }
            if (topLevelHeader != null) {
                topLevelHeader = topLevelHeader.getTopLevelHeader();
                if (topLevelHeader != null) {
                    auFacultyHeaderList = topLevelHeader.getAuFacultyHeaderList();
                    updateAUFacultyRecyclerViewAdapter();
                    auFacultyHeaderText.setText(topLevelHeader.getTitle());
                } else {
                    auFacultyHeaderText.setText(getDefaultLink(R.string.AU_FACULTY_TOP_HEADER));
                    auFacultyNotifyContentOnCacheUpdate();
                }
            }
        });
        return root;
    }

    private void requestAUFacultyDataFromService(AUFacultyHeader auItem) {
        auFacultyContentRequestService
                .requestContent(auItem.getUrl(), auItem.getHeaderLevel() + 1, new AUFacultyHeader(auItem))
                .subscribe((items) -> {
                    auFacultyHeaderList = items;
                    updateCachedAUFacultyHeaderList();
                    updateAUFacultyRecyclerViewAdapter();
                    auFacultyContentRequestService.update(auItem);
                    progressBar.setVisibility(View.INVISIBLE);
                    auSpinner.stopSpinner();
                }, this::onError);
        auFacultyHeaderText.setText(auItem.getTitle());
    }


    private void forwardToEventPage(AUFacultyHeader auItem) {
        Intent intent = new Intent(getContext(), AUEventItemDetailsActivity.class);
        intent.putExtra(AUItem.AU_ITEM_URL_TAG, auItem.getUrl());
        intent.putExtra(AUItem.AU_ITEM_NAME_TAG, auItem.getTitle());
        startActivity(intent);
        stopRefreshing();
    }

    private void updateCachedAUFacultyHeaderList() {
        if (!auFacultyHeaderList.isEmpty()) {
            cachedAUFAcultyHeaderList = auFacultyHeaderList;
        }
    }

    private void readAUFacultyDataFromCacheBy(String topLevelHeaderName) {
        auFacultyHeaderList = auFacultyContentRequestService.readFromCache("topLevelHeaderName", topLevelHeaderName);
        updateCachedAUFacultyHeaderList();
        updateAUFacultyRecyclerViewAdapter();
        stopRefreshing();
        auSpinner.stopSpinner();
    }

    private void onError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<AUFacultyHeader> auFacultyHeaders) {
        auFacultyHeaderList = auFacultyHeaders;
        updateAUFacultyRecyclerViewAdapter();
        stopRefreshing();
        auSpinner.stopSpinner();
    }

    private void auFacultyNotifyContentOnCacheUpdate() {
        auFacultyContentRequestService.notifyContentOnCacheUpdate(content -> {
            auFacultyHeaderList = content;
            updateAUFacultyRecyclerViewAdapter();
            stopRefreshing();
        });
    }

    private void updateAUFacultyRecyclerViewAdapter() {
        auFacultyRecyclerViewAdapter.setAuFacultyHeaders(auFacultyHeaderList);
        auFacultyRecyclerViewAdapter.notifyDataSetChanged();
    }


    private void stopRefreshing() {
        if (auScheduleSwipeRefreshLayout != null && auScheduleSwipeRefreshLayout.isRefreshing()) {
            auScheduleSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }


    @Override
    public void onRefresh() {
        auFacultyNotifyContentOnCacheUpdate();
        auFacultyHeaderText.setText(getDefaultLink(R.string.AU_FACULTY_TOP_HEADER));
    }
}
