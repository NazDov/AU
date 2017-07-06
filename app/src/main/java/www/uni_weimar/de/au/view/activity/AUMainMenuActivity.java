package www.uni_weimar.de.au.view.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.service.impl.AUMainMenuContentRequestService;
import www.uni_weimar.de.au.view.adapters.AUMainMenuViewPagerAdapter;
import www.uni_weimar.de.au.view.fragments.tabs.AUEventsTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUMainMenuTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUNewsFeedTabFragment;

import static www.uni_weimar.de.au.application.AUApplicationConfiguration.*;

public class AUMainMenuActivity extends AppCompatActivity implements View.OnClickListener {

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
        setContext(this);
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
            noInternetConnexTextView.setVisibility(View.VISIBLE);
            noInternetConnexImageView.setVisibility(View.VISIBLE);
        }
        realmUI = Realm.getDefaultInstance();
        new AUMainMenuContentRequestService(realmUI)
                .requestContent(cacheContent -> {
                    this.mainMenuTabList = cacheContent;
                    setAuMainMenuTabFragments(cacheContent);
                    if (!cacheContent.isEmpty()) {
                        noInternetConnexTextView.setVisibility(View.GONE);
                        noInternetConnexImageView.setVisibility(View.GONE);
                    }
                });
        auMainMenuViewPagerAdapter = new AUMainMenuViewPagerAdapter(getSupportFragmentManager(),
                auMainMenuTabFragments);
        auMainMenuViewPager.setAdapter(auMainMenuViewPagerAdapter);
        auMainMenuViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        auMainMenuCategoryIcon.setBackgroundResource(R.drawable.news_icon);
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_bg));
                        break;
                    case 1:
                        auMainMenuCategoryIcon.setBackgroundResource(R.drawable.lecture_shedule_icon);
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lecture_shedule_bg));
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
        newsFeedMenuBtn.setOnClickListener(this);
        scheduleMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.schedule_menu_btn_img);
        scheduleMenuBtn.setOnClickListener(this);
        cafeteriaMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.cafeteria_menu_btn_img);
        cafeteriaMenuBtn.setOnClickListener(this);
        libraryMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.library_menu_btn_img);
        libraryMenuBtn.setOnClickListener(this);
        profileMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.profile_menu_btn_img);
        profileMenuBtn.setOnClickListener(this);
        settingsMenuBtn = (ImageButton) auMainMenuHeaderView.findViewById(R.id.settings_menu_btn_img);
        settingsMenuBtn.setOnClickListener(this);
        navMenuButtonIds.add(R.id.news_feed_menu_btn_img);
        navMenuButtonIds.add(R.id.schedule_menu_btn_img);
        navMenuButtonIds.add(R.id.cafeteria_menu_btn_img);
        navMenuButtonIds.add(R.id.library_menu_btn_img);
        navMenuButtonIds.add(R.id.profile_menu_btn_img);
        navMenuButtonIds.add(R.id.settings_menu_btn_img);
    }

    @Override
    public void onClick(View menuBtnView) {
        int currentItem = 0;
        int menuBtnId = menuBtnView.getId();
        deactivateAllNavMenuBtnExcept(menuBtnId);
        if (menuBtnId == R.id.news_feed_menu_btn_img) {
            newsFeedMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            newsFeedMenuBtn.setBackgroundResource(R.mipmap.news_rss_active);
        }

        if (menuBtnId == R.id.schedule_menu_btn_img) {
            scheduleMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            scheduleMenuBtn.setBackgroundResource(R.mipmap.schedule_active);
        }

        if (menuBtnId == R.id.cafeteria_menu_btn_img) {
            cafeteriaMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            cafeteriaMenuBtn.setBackgroundResource(R.mipmap.cafeteria_active);
        }

        if (menuBtnId == R.id.library_menu_btn_img) {
            libraryMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            libraryMenuBtn.setBackgroundResource(R.mipmap.library_active);
        }

        if (menuBtnId == R.id.profile_menu_btn_img) {
            profileMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            profileMenuBtn.setBackgroundResource(R.mipmap.profile_active);
        }

        if (menuBtnId == R.id.settings_menu_btn_img) {
            settingsMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_active);
            settingsMenuBtn.setBackgroundResource(R.mipmap.settings_active);
        }

        auMainMenuViewPager.setCurrentItem(currentItem);
        auMainMenuDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void deactivateAllNavMenuBtnExcept(int toBeActiveNavMenuBtnId) {
        for (int navMenuBtn : navMenuButtonIds) {
            if (navMenuBtn != toBeActiveNavMenuBtnId) {
                if (navMenuBtn == R.id.news_feed_menu_btn_img) {
                    newsFeedMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    newsFeedMenuBtn.setBackgroundResource(R.mipmap.news_rss_inactive);
                }

                if (navMenuBtn == R.id.schedule_menu_btn_img) {
                    scheduleMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    scheduleMenuBtn.setBackgroundResource(R.mipmap.schedule_inactive);
                }

                if (navMenuBtn == R.id.cafeteria_menu_btn_img) {
                    cafeteriaMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    cafeteriaMenuBtn.setBackgroundResource(R.mipmap.cafeteria_inactive);
                }

                if (navMenuBtn == R.id.library_menu_btn_img) {
                    libraryMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    libraryMenuBtn.setBackgroundResource(R.mipmap.library_inactive);
                }

                if (navMenuBtn == R.id.profile_menu_btn_img) {
                    profileMenuBtnWrapper.setBackgroundResource(R.drawable.circle_shape_inactive);
                    profileMenuBtn.setBackgroundResource(R.mipmap.profile_inactive);
                }

                if (navMenuBtn == R.id.settings_menu_btn_img) {
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
    public void onBackPressed() {
        if (auMainMenuDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            auMainMenuDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            AUMainMenuTabFragment auMainMenuTabFragment = null;
            String mainMenuTabTitle = auMainMenuTab.getTitle();
            if ("News".equalsIgnoreCase(mainMenuTabTitle)) {
                auMainMenuTabFragment = AUNewsFeedTabFragment.newInstance(auMainMenuTab);
                auMainMenuTabFragmentList.add(auMainMenuTabFragment);
            }
            if ("Veranstaltungen".equalsIgnoreCase(mainMenuTabTitle)) {
                auMainMenuTabFragment = AUEventsTabFragment.newInstance(mainMenuTabTitle);
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
                initSystemMainMenuComponents(getContext());
                recreate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        noInternetConnexImageView.startAnimation(rotateAnimation);

    }


}
