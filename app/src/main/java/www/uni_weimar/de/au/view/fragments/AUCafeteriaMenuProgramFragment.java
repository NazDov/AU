package www.uni_weimar.de.au.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUCafeteria;
import www.uni_weimar.de.au.models.AUCafeteriaMenu;
import www.uni_weimar.de.au.service.impl.AUCafeteriaMenuContentRequestService;
import www.uni_weimar.de.au.utils.AUInstanceFactory;
import www.uni_weimar.de.au.view.adapters.AUCafeteriaMainMenuPagerAdapter;
import www.uni_weimar.de.au.view.components.AUSpinnerImageView;
import www.uni_weimar.de.au.view.fragments.tabs.AUCafeteriaTabFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ndovhuy on 04.08.2017.
 */
public class AUCafeteriaMenuProgramFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private static final String DEFAULT_SEARCH_KEY = "cafeteriaUrl";
    private static final String AU_CAFETERIA_TAG = "CAFETERIA";
    private static final String AU_FRAGMENT_SWITCHER_TAG = "SWITCHER";
    @InjectView(R.id.auCafeteriaProgramViewPager)
    ViewPager auCafeteriaViewPager;
    @InjectView(R.id.au_cafeteria_switch_fragment_btn)
    Button auCafeteriaSwitchFragmentBtn;
    @InjectView(R.id.cafeteriaSpinnerImageID)
    ImageView spinner;
    Realm realmUI;
    private List<AUCafeteriaMenu> auCafeteriaMenus;
    private AUCafeteria mAUCafeteria;
    private AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher mFragmentSwitcher;
    private List<AUCafeteriaMenuItemFragment> cafeteriaMenuItemFragments;
    private AUCafeteriaMainMenuPagerAdapter auCafeteriaMenuPagerAdapter;


    public static AUCafeteriaMenuProgramFragment newInstance(AUCafeteria auCafeteria, AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher fragmentSwitcher) {
        checkNotNull(auCafeteria);
        checkNotNull(fragmentSwitcher);
        AUCafeteriaMenuProgramFragment fragment = new AUCafeteriaMenuProgramFragment();
        fragment.mAUCafeteria = auCafeteria;
        fragment.mFragmentSwitcher = fragmentSwitcher;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object instance = AUInstanceFactory.getInstance(AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher.class);
        if (instance != null) {
            mFragmentSwitcher = (AUCafeteriaTabFragment.AUCafeteriaTabFragmentSwitcher) instance;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.au_cafeteria_menu_program_layout, container, false);
        ButterKnife.inject(this, rootView);
        spinner.setVisibility(View.VISIBLE);
        startLoadingAnim();
        realmUI = Realm.getDefaultInstance();
        auCafeteriaMenuPagerAdapter = new AUCafeteriaMainMenuPagerAdapter(getChildFragmentManager(), cafeteriaMenuItemFragments);
        auCafeteriaViewPager.setAdapter(auCafeteriaMenuPagerAdapter);
        auCafeteriaViewPager.addOnPageChangeListener(this);
        String defCafeteriaMenuProgramURL = getString(R.string.DEFAULT_CAFETERIA_URL);
        if (mAUCafeteria != null) {
            defCafeteriaMenuProgramURL = getString(R.string.DEFAULT_CAFETERIA_LINK_MASK) + mAUCafeteria.getUrl();
        }
        AUCafeteriaMenuContentRequestService
                .of(realmUI, defCafeteriaMenuProgramURL)
                .notifyContentOnCacheUpdate(this::onSuccess, DEFAULT_SEARCH_KEY, defCafeteriaMenuProgramURL)
                .requestNewContent()
                .subscribe(this::onSuccess, this::onError);
        auCafeteriaSwitchFragmentBtn.setOnClickListener(v -> {
            if (mFragmentSwitcher != null) {
                mFragmentSwitcher.switchToCafeteriaListFragment();
            }
        });
        return rootView;
    }

    private void startLoadingAnim() {
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setDuration(1000);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatMode(Animation.INFINITE);
        spinner.setAnimation(anim);
        spinner.startAnimation(anim);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realmUI != null) {
            realmUI.close();
            realmUI = null;
        }
        mAUCafeteria = null;
    }


    private void onError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void onSuccess(List<AUCafeteriaMenu> auCafeteriaMenus) {
        updateViewPager(auCafeteriaMenus);
        new Handler().postDelayed(()->{
            spinner.setVisibility(View.GONE);
        },500);
    }

    private void updateViewPager(List<AUCafeteriaMenu> content) {
        auCafeteriaMenus = content;
        cafeteriaMenuItemFragments = initAUCafeteriaMenuItemFragments();
        auCafeteriaMenuPagerAdapter.setAuCafeteriaMenuItemFragments(cafeteriaMenuItemFragments);
        auCafeteriaViewPager.setCurrentItem(getTodayCafeteriaMenuItemPosition());
    }

    private int getTodayCafeteriaMenuItemPosition() {
        int position = 0;
        int currentDayMenuPos = 0;
        for (; position < auCafeteriaMenus.size(); position++) {
            AUCafeteriaMenu auCafeteriaMenu = auCafeteriaMenus.get(position);
            String currentDayMenu = auCafeteriaMenu.getAuCafeteriaMenuDayTitle().split(",")[1].trim();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String currentDay = simpleDateFormat.format(new Date());
            if (currentDay.equalsIgnoreCase(currentDayMenu)) {
                currentDayMenuPos = position;
                break;
            }
        }
        return currentDayMenuPos;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        auCafeteriaViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public List<AUCafeteriaMenuItemFragment> initAUCafeteriaMenuItemFragments() {
        List<AUCafeteriaMenuItemFragment> fragments = new ArrayList<>();
        for (AUCafeteriaMenu auCafeteriaMenu : auCafeteriaMenus) {
            AUCafeteriaMenuItemFragment cafeteriaMenuItemFragment = AUCafeteriaMenuItemFragment.newInstance(auCafeteriaMenu);
            fragments.add(cafeteriaMenuItemFragment);
        }
        return fragments;
    }
}
