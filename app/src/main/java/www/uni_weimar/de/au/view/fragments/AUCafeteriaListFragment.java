package www.uni_weimar.de.au.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import www.uni_weimar.de.au.models.AUCafeteria;
import www.uni_weimar.de.au.service.impl.AUCafeteriaListContentRequestService;
import www.uni_weimar.de.au.utils.AUInstanceFactory;
import www.uni_weimar.de.au.utils.AUUtilityDefaultLinksFactory;
import www.uni_weimar.de.au.view.adapters.AUCafeteriaListRecyclerViewAdapter;
import www.uni_weimar.de.au.view.components.AUSpinner;
import www.uni_weimar.de.au.view.fragments.tabs.AUCafeteriaTabFragment;

/**
 * Created by ndovhuy on 26.08.2017.
 */

public class AUCafeteriaListFragment extends Fragment {
    @InjectView(R.id.auCafeteriaListRecyclerView)
    RecyclerView auCafeteriaRecyclerView;
    Realm realmUI;
    List<AUCafeteria> auCafeterias;
    AUCafeteriaListRecyclerViewAdapter adapter;
    AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher mSwitchFragmentStateListener;
    AUSpinner auSpinner;

    public static AUCafeteriaListFragment newInstance(AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher mListener) {
        AUCafeteriaListFragment fragment = new AUCafeteriaListFragment();
        fragment.mSwitchFragmentStateListener = mListener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object instance = AUInstanceFactory.getInstance(AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher.class);
        if (instance != null) {
            mSwitchFragmentStateListener = (AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher) instance;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_cafeteria_list, container, false);
        ButterKnife.inject(this, rootView);
        realmUI = Realm.getDefaultInstance();
        auSpinner = new AUSpinner(getActivity(), R.mipmap.cafeteria_spinner);
        auSpinner.show();
        adapter = new AUCafeteriaListRecyclerViewAdapter(auCafeterias);
        adapter.setOnItemChangeListener(auItem -> {
            Toast.makeText(getContext(), "opening menu for " + auItem.getName(), Toast.LENGTH_SHORT).show();
            if (mSwitchFragmentStateListener != null)
                mSwitchFragmentStateListener.switchToCafeteriaMenuProgramFragment(auItem);
        });
        auCafeteriaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auCafeteriaRecyclerView.setAdapter(adapter);
        AUCafeteriaListContentRequestService contentRequestService = AUCafeteriaListContentRequestService
                .of(realmUI, AUUtilityDefaultLinksFactory.getDefaultLink(R.string.DEFAULT_CAFETERIA_URL));
        contentRequestService.notifyContentOnCacheUpdate(this::onSuccess);
        contentRequestService.requestNewContent().subscribe(this::onSuccess, this::onError);
        return rootView;
    }

    private void onError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<AUCafeteria> auCafeterias) {
        this.auCafeterias = auCafeterias;
        adapter.setCafeterias(auCafeterias);
        if (auSpinner.isShowing()) {
            auSpinner.dismiss();
        }
    }
}
