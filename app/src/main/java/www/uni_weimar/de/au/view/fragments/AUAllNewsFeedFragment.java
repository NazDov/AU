package www.uni_weimar.de.au.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.models.AUNewsFeedDefaultCategory;
import www.uni_weimar.de.au.models.AUNewsFeedFavourite;
import www.uni_weimar.de.au.models.AURssChannel;
import www.uni_weimar.de.au.orm.AUNewsFeedFavouriteORM;
import www.uni_weimar.de.au.orm.AURssORM;
import www.uni_weimar.de.au.service.impl.AUNewsFeedContentRequestService;
import www.uni_weimar.de.au.service.impl.AUNewsFeedFavouriteContentRequestService;
import www.uni_weimar.de.au.utils.AUNewsFeedStaticCategory;
import www.uni_weimar.de.au.view.adapters.AUNewsFeedRecyclerViewAdapter;

/**
 * Created by ndovhuy on 19.06.2017.
 */
public class AUAllNewsFeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.au_all_news_feed_recycler_view)
    RecyclerView newsFeedRecyclerView;
    @InjectView(R.id.news_feed_swipe_refresh)
    SwipeRefreshLayout newsFeedSwipeRefreshLayout;
    @InjectView(R.id.au_category_menu_tab_layout)
    TabLayout newsFeedCategoryTabLayout;
    AUNewsFeedRecyclerViewAdapter newsFeedRecyclerViewAdapter;
    AUNewsFeedContentRequestService newsFeedContentRequestService;
    AUNewsFeedFavouriteContentRequestService newsFeedFavouriteContentRequestService;
    Observable<List<AUNewsFeed>> newsFeedObservable;
    List<AUNewsFeed> newsFeeds;
    Realm realm;
    AUNewsFeedFavouriteORM newsFeedFavouriteORM;
    View rootView;
    AURssORM rssOrm;
    List<AURssChannel> rssChannels;
    private AURssChannel auRssChannel;

    public static AUAllNewsFeedFragment newInstance() {
        return new AUAllNewsFeedFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        rssOrm = new AURssORM(realm);
        rssChannels = rssOrm.findAll();
        auRssChannel = rssChannels.get(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.au_all_news_feed_layout, container, false);
        ButterKnife.inject(this, rootView);
        newsFeedSwipeRefreshLayout.setOnRefreshListener(this);
        newsFeedFavouriteORM = new AUNewsFeedFavouriteORM(realm);
        newsFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsFeedContentRequestService = new AUNewsFeedContentRequestService(realm, new AURssChannel(auRssChannel.getUrl(), auRssChannel.getTitle()));
        newsFeedFavouriteContentRequestService = new AUNewsFeedFavouriteContentRequestService(realm);
        newsFeedContentRequestService.notifyContentOnCacheUpdate(content -> newsFeeds = content);
        newsFeedObservable = newsFeedContentRequestService.requestNewContent();
        newsFeedRecyclerViewAdapter = new AUNewsFeedRecyclerViewAdapter(getContext(), newsFeeds);
        newsFeedRecyclerViewAdapter.setAuNewsFeedLikedItemListener(auItem -> {
            Toast.makeText(getContext(), "news item " + auItem.getTitle() + " was added to favourites", Toast.LENGTH_SHORT).show();
            AUNewsFeedFavourite auNewsFeedFavourite = new AUNewsFeedFavourite();
            auNewsFeedFavourite.setLink(auItem.getLink());
            newsFeedFavouriteORM.add(auNewsFeedFavourite);
        });
        newsFeedRecyclerView.setAdapter(newsFeedRecyclerViewAdapter);
        newsFeedObservable.subscribe(this::onSuccess, this::onError);
        initNewsFeedCategories();
        return rootView;
    }

    private void initNewsFeedCategories() {
        initNewsFeedCategoryTabs();
        initNewsFeedCategoryMargins();
        initOnCategoryTabSelectListener();
    }

    protected void initOnCategoryTabSelectListener() {
        newsFeedCategoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    protected void switchTab(TabLayout.Tab tab) {
        String categoryName = (String) tab.getText();
        int categoryPosition = tab.getPosition() != 0 ? tab.getPosition() - 1 : tab.getPosition();
        switchTabByCategoryNameAndPosition(categoryName, categoryPosition);
    }

    void switchTabByCategoryNameAndPosition(String categoryName, int categoryPosition) {
        if (AUNewsFeedStaticCategory.ALL.toString().equalsIgnoreCase(categoryName)) {
            newsFeedContentRequestService.notifyContentOnCacheUpdate(content -> newsFeeds = content);
        } else if (AUNewsFeedStaticCategory.FAVOURITE.toString().equalsIgnoreCase(categoryName)) {
            newsFeedFavouriteContentRequestService.notifyContentOnCacheUpdate(content -> {
                newsFeeds = content;
            });
        } else {
            switchRssChannel(categoryName, categoryPosition);
        }
        newsFeedRecyclerViewAdapter.setAuNewsFeedList(newsFeeds);
        newsFeedRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void switchRssChannel(String categoryName, int categoryPosition) {
        String categoryURL = rssChannels.get(categoryPosition).getUrl();
        newsFeeds = newsFeedContentRequestService
                .findAllBy(AUNewsFeedContentRequestService.AUNewsFeedSearchKey.CATEGORY_URL, categoryURL);
        newsFeedContentRequestService
                .requestNewContent(new AURssChannel(categoryURL, categoryName))
                .subscribe(AUAllNewsFeedFragment.this::onSuccess,
                        AUAllNewsFeedFragment.this::onError);
    }

    protected void initNewsFeedCategoryTabs() {
        for (String rssChannelTabName : getRssChannelsTabNames()) {
            TabLayout.Tab auMenuTab = newsFeedCategoryTabLayout.newTab();
            newsFeedCategoryTabLayout.addTab(auMenuTab.setText(rssChannelTabName));
        }

        // specify 'favourite' news category menu
        newsFeedCategoryTabLayout.addTab(newsFeedCategoryTabLayout.newTab().setText(AUNewsFeedStaticCategory.FAVOURITE.name()), 1);

    }

    private void initNewsFeedCategoryMargins() {
        for (int i = 0; i < newsFeedCategoryTabLayout.getTabCount(); i++) {
            View tabView = ((ViewGroup) newsFeedCategoryTabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams tabMargin = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
            tabMargin.setMargins(20, 0, 0, 0);
            tabView.requestLayout();
        }
    }

    @NonNull
    List<String> getRssChannelsTabNames() {
        List<String> rssChannelsTabNames = new ArrayList<>();
        for (AURssChannel rssChannel : rssChannels) {
            rssChannelsTabNames.add(rssChannel.getTitle());
        }
        return rssChannelsTabNames;
    }


    private void onSuccess(final List<AUNewsFeed> auNewsFeeds) {
        notifyViewIfNotEmpty(auNewsFeeds);
    }

    private void notifyViewIfNotEmpty(List<AUNewsFeed> auNewsFeeds) {
        if (!auNewsFeeds.isEmpty()) {
            notifyView(auNewsFeeds);
        }
    }

    private void notifyView(List<AUNewsFeed> auNewsFeeds) {
        this.newsFeeds = auNewsFeeds;
        newsFeedRecyclerViewAdapter.notifyDataSetChanged();
        stopRefreshing();
    }

    private void onError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        stopRefreshing();
    }

    private void stopRefreshing() {
        if (newsFeedSwipeRefreshLayout != null && newsFeedSwipeRefreshLayout.isRefreshing()) {
            newsFeedSwipeRefreshLayout.setRefreshing(false);
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
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRefresh() {
        newsFeedContentRequestService
                .requestNewContent()
                .subscribe(this::onSuccess, this::onError);
    }

}
