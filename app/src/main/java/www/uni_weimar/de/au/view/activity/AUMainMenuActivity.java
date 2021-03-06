package www.uni_weimar.de.au.view.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.application.AUApplicationConfiguration;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.service.impl.AUMainMenuContentRequestService;
import www.uni_weimar.de.au.utils.AUActivityFragmentStateStorage;
import www.uni_weimar.de.au.view.adapters.AUMainMenuViewPagerAdapter;
import www.uni_weimar.de.au.view.fragments.AUCafeteriaMenuProgramFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUCafeteriaTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AULibraryTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUScheduleTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUMainMenuTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUNewsFeedTabFragment;

import static www.uni_weimar.de.au.application.AUApplicationConfiguration.*;

public class AUMainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NEWS_FEED_MENUTAB_ITEM_ID = 0;
    private static final int COURSES_MENUTAB_ITEM_ID = 1;
    private static final int CAFETERIA_MENUTAB_ITEM_ID = 2;
    private static final int LIBRARY_MENUTAB_ITEM_ID = 3;
    public static final String NEWS_MENUTAB_TITLE = "News";
    public static final String COURSES_MENUTAB_TITLE = "Courses";
    public static final String CAFETERIA_MENUTAB_TITLE = "Cafeteria";
    public static final String LIBRARY_MENUTAB_TITLE = "Library";
    @InjectView(R.id.au_main_menu_nav_view)
    NavigationView auMainMenuTabNavigationView;
    @InjectView(R.id.au_main_menu_drawer_layout)
    DrawerLayout auMainMenuDrawerLayout;
    @InjectView(R.id.auMainMenuViewPager)
    ViewPager auMainMenuViewPager;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.au_main_menu_image_header)
    ImageView auMainMenuImageHeader;
    @InjectView(R.id.au_main_menu_category_icon)
    ImageView auMainMenuCategoryIcon;
    @InjectView(R.id.noIntentetConnexTextView)
    TextView noInternetConnexTextView;
    @InjectView(R.id.noIntentetConnexImageView)
    ImageButton noInternetConnexImageView;
    AUMainMenuViewPagerAdapter auMainMenuViewPagerAdapter;
    Realm realmUI;
    Disposable disposable;
    List<AUMainMenuTabFragment> auMainMenuTabFragments;
    List<AUMainMenuTab> mainMenuTabList;
    RelativeLayout newsFeedMenuBtnWrapper;
    RelativeLayout scheduleMenuBtnWrapper;
    RelativeLayout cafeteriaMenuBtnWrapper;
    RelativeLayout libraryMenuBtnWrapper;
    RelativeLayout profileMenuBtnWrapper;
    RelativeLayout settingsMenuBtnWrapper;
    ImageButton newsFeedMenuBtn;
    ImageButton scheduleMenuBtn;
    ImageButton cafeteriaMenuBtn;
    ImageButton libraryMenuBtn;
    ImageButton profileMenuBtn;
    ImageButton settingsMenuBtn;
    List<Integer> navMenuButtonIds = new ArrayList<>(6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.au_main_menu_activity);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fixMinDrawerMargin(auMainMenuDrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, auMainMenuDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        auMainMenuDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        if (!hasInternetConnection(this)) {
            showNoConnectionViewAs(View.VISIBLE);
        }
        realmUI = Realm.getDefaultInstance();
        AUMainMenuContentRequestService
                .of(realmUI)
                .notifyContentOnCacheUpdate(cacheContent -> {
                    this.mainMenuTabList = cacheContent;
                    setAuMainMenuTabFragments(cacheContent);
                    if (!cacheContent.isEmpty()) {
                        showNoConnectionViewAs(View.GONE);
                    }
                });
        auMainMenuViewPagerAdapter = new AUMainMenuViewPagerAdapter(getSupportFragmentManager(),
                auMainMenuTabFragments);
        auMainMenuViewPager.setAdapter(auMainMenuViewPagerAdapter);
        auMainMenuViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case NEWS_FEED_MENUTAB_ITEM_ID:
                        auMainMenuCategoryIcon.setBackgroundResource(R.drawable.news_icon);
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_bg));
                        break;
                    case COURSES_MENUTAB_ITEM_ID:
                        auMainMenuCategoryIcon.setBackgroundResource(R.drawable.lecture_shedule_icon);
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lecture_shedule_bg));
                        break;
                    case CAFETERIA_MENUTAB_ITEM_ID:
                        auMainMenuCategoryIcon.setBackgroundResource(R.drawable.cafeteria_icon);
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cafeteria_bg));
                        break;
                    case LIBRARY_MENUTAB_ITEM_ID:
                        auMainMenuCategoryIcon.setBackgroundResource(R.drawable.library_icon);
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.library_bg));
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        auMainMenuTabNavigationView.setVerticalScrollBarEnabled(false);
        auMainMenuTabNavigationView.refreshDrawableState();
        View auMainMenuHeaderView = auMainMenuTabNavigationView.getHeaderView(0);
        newsFeedMenuBtnWrapper = (RelativeLayout) auMainMenuHeaderView.findViewById(R.id.news_feed_menu_btn_wrapper);
        scheduleMenuBtnWrapper = (RelativeLayout) auMainMenuHeaderView.findViewById(R.id.schedule_menu_btn_wrapper);
        cafeteriaMenuBtnWrapper = (RelativeLayout) auMainMenuHeaderView.findViewById(R.id.cafeteria_menu_btn_wrapper);
        libraryMenuBtnWrapper = (RelativeLayout) auMainMenuHeaderView.findViewById(R.id.library_menu_btn_wrapper);
        profileMenuBtnWrapper = (RelativeLayout) auMainMenuHeaderView.findViewById(R.id.profile_menu_btn_wrapper);
        settingsMenuBtnWrapper = (RelativeLayout) auMainMenuHeaderView.findViewById(R.id.settings_menu_btn_wrapper);
        newsFeedMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.news_feed_menu_btn_img);
        newsFeedMenuBtn.setOnClickListener(v -> AUMainMenuActivity.this.onClick(newsFeedMenuBtnWrapper));
        newsFeedMenuBtnWrapper.setOnClickListener(this);
        scheduleMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.schedule_menu_btn_img);
        scheduleMenuBtn.setOnClickListener(v -> AUMainMenuActivity.this.onClick(scheduleMenuBtnWrapper));
        scheduleMenuBtnWrapper.setOnClickListener(this);
        cafeteriaMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.cafeteria_menu_btn_img);
        cafeteriaMenuBtnWrapper.setOnClickListener(this);
        libraryMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.library_menu_btn_img);
        libraryMenuBtnWrapper.setOnClickListener(this);
        profileMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.profile_menu_btn_img);
        profileMenuBtnWrapper.setOnClickListener(this);
        settingsMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.settings_menu_btn_img);
        settingsMenuBtnWrapper.setOnClickListener(this);
        navMenuButtonIds.add(R.id.news_feed_menu_btn_wrapper);
        navMenuButtonIds.add(R.id.schedule_menu_btn_wrapper);
        navMenuButtonIds.add(R.id.cafeteria_menu_btn_wrapper);
        navMenuButtonIds.add(R.id.library_menu_btn_wrapper);
        navMenuButtonIds.add(R.id.profile_menu_btn_wrapper);
        navMenuButtonIds.add(R.id.settings_menu_btn_wrapper);
    }

    private void showNoConnectionViewAs(int visibility) {
        noInternetConnexTextView.setVisibility(visibility);
        noInternetConnexImageView.setVisibility(visibility);
    }

    @Override
    public void onClick(View menuBtnView) {
        int currentMenuItemID = NEWS_FEED_MENUTAB_ITEM_ID;
        int menuBtnId = menuBtnView.getId();
        deactivateAllNavMenuBtnExcept(menuBtnId);
        if (menuBtnId == R.id.news_feed_menu_btn_wrapper) {
            newsFeedMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            newsFeedMenuBtn.setBackgroundResource(R.mipmap.news_rss_active);
        }

        if (menuBtnId == R.id.schedule_menu_btn_wrapper) {
            currentMenuItemID = COURSES_MENUTAB_ITEM_ID;
            scheduleMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            scheduleMenuBtn.setBackgroundResource(R.mipmap.schedule_active);
        }

        if (menuBtnId == R.id.cafeteria_menu_btn_wrapper) {
            currentMenuItemID = CAFETERIA_MENUTAB_ITEM_ID;
            cafeteriaMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            cafeteriaMenuBtn.setBackgroundResource(R.mipmap.cafeteria_active);
        }

        if (menuBtnId == R.id.library_menu_btn_wrapper) {
            currentMenuItemID = LIBRARY_MENUTAB_ITEM_ID;
            libraryMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            libraryMenuBtn.setBackgroundResource(R.mipmap.library_active);
        }

        if (menuBtnId == R.id.profile_menu_btn_wrapper) {
            profileMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            profileMenuBtn.setBackgroundResource(R.mipmap.profile_active);
        }

        if (menuBtnId == R.id.settings_menu_btn_wrapper) {
            settingsMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            settingsMenuBtn.setBackgroundResource(R.mipmap.settings_active);
        }

        auMainMenuViewPager.setCurrentItem(currentMenuItemID);
        auMainMenuDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void deactivateAllNavMenuBtnExcept(int toBeActiveNavMenuBtnId) {
        for (int navMenuBtn : navMenuButtonIds) {
            if (navMenuBtn != toBeActiveNavMenuBtnId) {
                if (navMenuBtn == R.id.news_feed_menu_btn_wrapper) {
                    newsFeedMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    newsFeedMenuBtn.setBackgroundResource(R.mipmap.news_rss_inactive);
                }

                if (navMenuBtn == R.id.schedule_menu_btn_wrapper) {
                    scheduleMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    scheduleMenuBtn.setBackgroundResource(R.mipmap.schedule_inactive);
                }

                if (navMenuBtn == R.id.cafeteria_menu_btn_wrapper) {
                    cafeteriaMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    cafeteriaMenuBtn.setBackgroundResource(R.mipmap.cafeteria_inactive);
                }

                if (navMenuBtn == R.id.library_menu_btn_wrapper) {
                    libraryMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    libraryMenuBtn.setBackgroundResource(R.mipmap.library_inactive);
                }

                if (navMenuBtn == R.id.profile_menu_btn_wrapper) {
                    profileMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    profileMenuBtn.setBackgroundResource(R.mipmap.profile_inactive);
                }

                if (navMenuBtn == R.id.settings_menu_btn_wrapper) {
                    settingsMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    settingsMenuBtn.setBackgroundResource(R.mipmap.settings_inactive);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmUI.close();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onBackPressed() {
        if (auMainMenuDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            auMainMenuDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment activeFragment = AUActivityFragmentStateStorage.active();
            if (activeFragment != null &&
                    activeFragment.getClass() == AUCafeteriaMenuProgramFragment.class){
                ((AUCafeteriaMenuProgramFragment)activeFragment).onBackPressed();
                Toast.makeText(getBaseContext(), "Tap back button again to exit application", Toast.LENGTH_SHORT).show();
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setAuMainMenuTabFragments(List<AUMainMenuTab> auMainMenuTabsList) {
        List<AUMainMenuTabFragment> auMainMenuTabFragmentList = new ArrayList<>();
        for (AUMainMenuTab auMainMenuTab : auMainMenuTabsList) {
            AUMainMenuTabFragment auMainMenuTabFragment;
            String mainMenuTabTitle = auMainMenuTab.getTitle();
            if (NEWS_MENUTAB_TITLE.equalsIgnoreCase(mainMenuTabTitle)) {
                auMainMenuTabFragment = AUNewsFeedTabFragment.newInstance(auMainMenuTab);
                auMainMenuTabFragmentList.add(auMainMenuTabFragment);
            }
            if (COURSES_MENUTAB_TITLE.equalsIgnoreCase(mainMenuTabTitle)) {
                auMainMenuTabFragment = AUScheduleTabFragment.newInstance(auMainMenuTab);
                auMainMenuTabFragmentList.add(auMainMenuTabFragment);
            }

            if (CAFETERIA_MENUTAB_TITLE.equalsIgnoreCase(mainMenuTabTitle)) {
                auMainMenuTabFragment = AUCafeteriaTabFragment.newInstance(auMainMenuTab);
                auMainMenuTabFragmentList.add(auMainMenuTabFragment);
            }

            if (LIBRARY_MENUTAB_TITLE.equalsIgnoreCase(mainMenuTabTitle)) {
                auMainMenuTabFragment = AULibraryTabFragment.newInstance(auMainMenuTab);
                auMainMenuTabFragmentList.add(auMainMenuTabFragment);
            }
        }
        this.auMainMenuTabFragments = auMainMenuTabFragmentList;
    }


    public void fixMinDrawerMargin(DrawerLayout drawerLayout) {
        try {
            Field f = DrawerLayout.class.getDeclaredField("mMinDrawerMargin");
            f.setAccessible(true);
            f.set(drawerLayout, 0);

            drawerLayout.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshOnNoConnection(View view) {
        Animation rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Realm realm = Realm.getDefaultInstance();
                if (!hasInternetConnection(AUMainMenuActivity.this)) {
                    Toast.makeText(AUMainMenuActivity.this, "no internet connection", Toast.LENGTH_SHORT).show();
                }
                AUMainMenuContentRequestService
                        .of(realm, getResources().getString(R.string.MAIN_MENU))
                        .requestNewContent()
                        .subscribe(this::onMainMenuLoaded, this::onError);
            }

            private void onError(Throwable throwable) {
                Toast.makeText(AUMainMenuActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            private void onMainMenuLoaded(List<AUMainMenuTab> auMainMenuTabs) {
                recreate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        noInternetConnexImageView.startAnimation(rotateAnimation);

    }


}
