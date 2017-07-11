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
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.models.AUNewsFeedFavourite;
import www.uni_weimar.de.au.service.impl.AUNewsFeedFavouriteContentRequestService;
import www.uni_weimar.de.au.service.inter.AUContentChangeListener;
import www.uni_weimar.de.au.view.adapters.AUNewsFeedRecyclerViewAdapter;

/**
 * Created by ndovhuy on 19.06.2017.
 */
public class AUMyNewsFeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.au_my_news_feed_recycler_view)
    RecyclerView auMyNewsFeedRecyclerView;
    @InjectView(R.id.au_my_news_feed_swipe_refresh)
    SwipeRefreshLayout auMyNewsFeedSwipeRefreshLayout;
    AUNewsFeedRecyclerViewAdapter auNewsFeedRecyclerViewAdapter;
    AUNewsFeedFavouriteContentRequestService auNewsFeedFavouriteContentRequestService;
    Realm realmUi;
    List<AUNewsFeed> auNewsFeedFavourites;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_my_news_feed_layout, container, false);
        ButterKnife.inject(this, rootView);
        realmUi = Realm.getDefaultInstance();
        auMyNewsFeedSwipeRefreshLayout.setOnRefreshListener(this);
        auNewsFeedFavouriteContentRequestService = new AUNewsFeedFavouriteContentRequestService(realmUi);
        requestAuNewsFeedContent();
        auMyNewsFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auNewsFeedRecyclerViewAdapter = new AUNewsFeedRecyclerViewAdapter(getContext(), auNewsFeedFavourites);
        auMyNewsFeedRecyclerView.setAdapter(auNewsFeedRecyclerViewAdapter);
        return rootView;
    }

    private void requestAuNewsFeedContent() {
        auNewsFeedFavouriteContentRequestService.notifyContentOnCacheUpdate(content -> {
            if (content == null || content.isEmpty()) {
                Toast.makeText(getContext(), "No favourite items found", Toast.LENGTH_SHORT).show();
            }
            auNewsFeedFavourites = content;
            auMyNewsFeedSwipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        if (realmUi != null) realmUi.close();
        realmUi = null;
    }

    @Override
    public void onRefresh() {
        requestAuNewsFeedContent();
        auNewsFeedRecyclerViewAdapter.setAuNewsFeedList(auNewsFeedFavourites);
        auNewsFeedRecyclerViewAdapter.notifyDataSetChanged();
    }
}
