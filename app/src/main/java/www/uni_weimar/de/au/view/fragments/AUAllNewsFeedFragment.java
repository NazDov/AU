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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.service.impl.AUNewsFeedContentRequestService;
import www.uni_weimar.de.au.view.adapters.AUNewsFeedRecyclerViewAdapter;

/**
 * Created by ndovhuy on 19.06.2017.
 */
public class AUAllNewsFeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.au_all_news_feed_recycler_view)
    RecyclerView auAllNewsFeedRecyclerView;
    @InjectView(R.id.news_feed_swipe_refresh)
    SwipeRefreshLayout newsFeedSwipeRefreshLayout;
    @InjectView(R.id.android_material_design_spinner)
    Spinner auSpinner;
    AUNewsFeedRecyclerViewAdapter auNewsFeedRecyclerViewAdapter;
    AUNewsFeedContentRequestService auNewsFeedContentRequestService;
    Observable<List<AUNewsFeed>> auNewsFeedObservable;
    List<AUNewsFeed> auNewsFeedList;
    Disposable disposable;
    Realm realm;

    public static AUAllNewsFeedFragment newInstance() {
        Bundle args = new Bundle();
        AUAllNewsFeedFragment fragment = new AUAllNewsFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_all_news_feed_layout, container, false);
        ButterKnife.inject(this, rootView);
        newsFeedSwipeRefreshLayout.setOnRefreshListener(this);
        realm = Realm.getDefaultInstance();
        auAllNewsFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auNewsFeedContentRequestService = new AUNewsFeedContentRequestService(realm, null);
        auNewsFeedObservable = auNewsFeedContentRequestService.requestContent(content -> auNewsFeedList = content);
        auNewsFeedRecyclerViewAdapter = new AUNewsFeedRecyclerViewAdapter(getContext(), auNewsFeedList);
        auAllNewsFeedRecyclerView.setAdapter(auNewsFeedRecyclerViewAdapter);
        disposable = auNewsFeedObservable.subscribe(this::onSuccess, this::onError);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.news_feed_categories, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auSpinner.setAdapter(arrayAdapter);
        auSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = parent.getSelectedItem().toString();

                if ("All".equalsIgnoreCase(categoryName)) {
                    auNewsFeedContentRequestService.requestContent(content -> auNewsFeedList = content);
                } else {
                    auNewsFeedList = auNewsFeedContentRequestService
                            .getAuBaseORM()
                            .findAllBy("category", categoryName);
                    if (auNewsFeedList.isEmpty()) {
                        Toast.makeText(getContext(), "no items found for: "+categoryName, Toast.LENGTH_SHORT).show();
                    }
                }
                auNewsFeedRecyclerViewAdapter.setAuNewsFeedList(auNewsFeedList);
                auNewsFeedRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }


    private void onSuccess(final List<AUNewsFeed> auNewsFeeds) {
        auNewsFeedList = auNewsFeeds;
        auNewsFeedRecyclerViewAdapter.notifyDataSetChanged();
        if (newsFeedSwipeRefreshLayout.isRefreshing()) {
            newsFeedSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void onError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        if (newsFeedSwipeRefreshLayout.isRefreshing()) {
            newsFeedSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        Toast.makeText(getContext(), "onDestroy()", Toast.LENGTH_LONG).show();
        super.onDestroyView();
        ButterKnife.reset(this);
        if (realm != null) {
            realm.close();
        }
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onRefresh() {
        auNewsFeedObservable.subscribe(this::onSuccess, this::onError);
    }

}
