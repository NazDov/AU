package www.uni_weimar.de.au.view.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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
import www.uni_weimar.de.au.view.adapters.AUMainMenuViewPagerAdapter;
import www.uni_weimar.de.au.view.fragments.tabs.AUEventsTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUMainMenuTabFragment;
import www.uni_weimar.de.au.view.fragments.tabs.AUNewsFeedTabFragment;

import static www.uni_weimar.de.au.application.AUApplicationConfiguration.*;

public class AUMainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContext(this);
        setContentView(R.layout.au_main_menu_activity);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, auMainMenuDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        auMainMenuDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        auMainMenuTabNavigationView.setNavigationItemSelectedListener(this);
        if (!hasInternetConnex(this)) {
            noInternetConnexTextView.setVisibility(View.VISIBLE);
            noInternetConnexImageView.setVisibility(View.VISIBLE);
        }
        realmUI = Realm.getDefaultInstance();
        new AUMainMenuContentRequestService(realmUI, this.getString(R.string.MAIN_MENU))
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
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.news_bg_compressed));
                        break;
                    case 1:
                        auMainMenuCategoryIcon.setBackgroundResource(R.drawable.lecture_shedule_icon);
                        auMainMenuImageHeader.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lecture_schedule_bg_compressed));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        auMainMenuDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

    public void refreshOnNoConnection(View view) {
        Animation rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (hasInternetConnex(getApplicationContext())) {
                    initSystemMainMenuComponents(getContext());
                    recreate();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        noInternetConnexImageView.startAnimation(rotateAnimation);

    }
}
