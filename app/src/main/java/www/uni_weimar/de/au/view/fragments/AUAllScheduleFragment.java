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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyEvent;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.models.AUItem;
import www.uni_weimar.de.au.service.impl.AUFacultyContentRequestService;
import www.uni_weimar.de.au.view.activity.AUEventItemDetailsActivity;
import www.uni_weimar.de.au.view.adapters.AUFacultyRecyclerViewAdapter;

/**
 * Created by nazar on 13.07.17.
 */

public class AUAllScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.au_schedule_recycler_view)
    RecyclerView auScheduleRecyclerView;
    @InjectView(R.id.au_schedule_swipe_refresh)
    SwipeRefreshLayout auScheduleSwipeRefreshLayout;
    @InjectView(R.id.goToPreviousFacultyHeader)
    TextView goBackToPreviousFacultyHeader;
    AUFacultyContentRequestService auFacultyContentRequestService;
    AUFacultyRecyclerViewAdapter auFacultyRecyclerViewAdapter;
    private Realm realm;
    private List<AUFacultyHeader> auFacultyHeaderList;
    private Disposable auScheduleDisposable;
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
        realm = Realm.getDefaultInstance();
        auScheduleSwipeRefreshLayout.setOnRefreshListener(this);
        auScheduleSwipeRefreshLayout.setRefreshing(true);
        auFacultyRecyclerViewAdapter = new AUFacultyRecyclerViewAdapter(getContext(), auFacultyHeaderList);
        auFacultyRecyclerViewAdapter.setOnItemClickListener(auItem -> {
            auScheduleSwipeRefreshLayout.setRefreshing(true);
            if (AUItem.EVENT.equals(auItem.getAutype())) {
                Intent intent = new Intent(getContext(), AUEventItemDetailsActivity.class);
                intent.putExtra(AUItem.AU_ITEM_URL_TAG, auItem.getUrl());
                intent.putExtra(AUItem.AU_ITEM_NAME_TAG, auItem.getTitle());
                startActivity(intent);
                stopRefreshing();
            } else {
                auFacultyContentRequestService
                        .requestContent(auItem.getUrl(), auItem.getHeaderLevel() + 1, new AUFacultyHeader(auItem))
                        .subscribe((items) -> {
                            auFacultyHeaderList = items;
                            updateCachedAUFacultyHeaderList();
                            updateAUFacultyRecyclerViewAdapter();
                            auFacultyContentRequestService.update(auItem);
                            stopRefreshing();
                        }, this::onError);
                goBackToPreviousFacultyHeader.setText(auItem.getTitle());
                readFromCacheBy(auItem.getTitle());
            }

        });
        auFacultyContentRequestService = AUFacultyContentRequestService.of(realm, getString(R.string.COURSES_URL));
        auFacultyContentRequestService
                .requestContent(getString(R.string.COURSES_URL), 1, null)
                .subscribe(this::onSuccess, this::onError);

        auFacultyContentRequestService.notifyContentOnCacheUpdate(content -> {
            auFacultyHeaderList = content;
            updateAUFacultyRecyclerViewAdapter();
            stopRefreshing();
        });
        auScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auScheduleRecyclerView.setAdapter(auFacultyRecyclerViewAdapter);
        goBackToPreviousFacultyHeader.setOnClickListener(v -> {
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
                    goBackToPreviousFacultyHeader.setText(topLevelHeader.getTitle());
                } else {
                    goBackToPreviousFacultyHeader.setText("");
                    auFacultyNotifyContentOnCacheUpdate();
                }
            }

        });
        return root;
    }

    private void updateCachedAUFacultyHeaderList() {
        if (!auFacultyHeaderList.isEmpty()) {
            cachedAUFAcultyHeaderList = auFacultyHeaderList;
        }
    }

    private void readFromCacheBy(String topLevelHeaderName) {
        auFacultyHeaderList = auFacultyContentRequestService.readFromCache("topLevelHeaderName", topLevelHeaderName);
        updateCachedAUFacultyHeaderList();
        updateAUFacultyRecyclerViewAdapter();
        stopRefreshing();
    }

    private void onError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<AUFacultyHeader> auFacultyHeaders) {
        auFacultyHeaderList = auFacultyHeaders;
        updateAUFacultyRecyclerViewAdapter();
        stopRefreshing();
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
        if (auScheduleSwipeRefreshLayout.isRefreshing()) {
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

        if (auScheduleDisposable != null) {
            auScheduleDisposable.dispose();
            auScheduleDisposable = null;
        }
    }

    @Override
    public void onRefresh() {
        auFacultyNotifyContentOnCacheUpdate();
    }
}
