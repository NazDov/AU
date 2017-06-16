package www.uni_weimar.de.au.activity;

import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.application.AUApplicationConfiguration;
import www.uni_weimar.de.au.models.AUMainMenuItem;
import www.uni_weimar.de.au.models.AUMainMenuTab;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.service.impl.AUMainMenuContentRequestService;
import www.uni_weimar.de.au.service.impl.AUNewsFeedContentRequestService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Realm realmUI;
    private Disposable disposable;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AUApplicationConfiguration.setContext(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        realmUI = Realm.getDefaultInstance();
//        AUMainMenuContentRequestService auMainMenuContentProviderService = new AUMainMenuContentRequestService(realmUI, this.getString(R.string.MAIN_MENU));
//        disposable = auMainMenuContentProviderService
//                .requestContent(content -> {
//                    for (AUMainMenuTab auMainMenuTab : content) {
//                        Log.v("CACHED TAB ITEM", auMainMenuTab.getTitle());
//                        for (AUMainMenuItem auMainMenuItem : auMainMenuTab.getAUMainMenuItemList()) {
//                            Log.v("CACHED MENU ITEM", auMainMenuItem.getTitle());
//                        }
//                    }
//                })
//                .subscribe(this::updateUI, this::onError);



        AUNewsFeedContentRequestService auNewsFeedContentProviderService = new AUNewsFeedContentRequestService(realmUI, null);
        auNewsFeedContentProviderService
                .requestContent(content -> {
                    for (AUNewsFeed auNewsFeed : content) {
                        Log.v("CACHED NewsFeed: ", auNewsFeed.toString());
                    }
                }).subscribe(content -> {
                    for (AUNewsFeed auNewsFeed : content) {
                        Log.v("NewsFeed: ", auNewsFeed.toString());
                    }
        }, this::onError);

    }

    private void onError(Throwable e) {
        Log.e("exception: ", e.getMessage());
    }

    private void updateUI(List<AUMainMenuTab> auMainMenuTabs) {
        for (AUMainMenuTab auMainMenuTab : auMainMenuTabs) {
            Log.v("TAB ITEM", auMainMenuTab.getTitle());
            for (AUMainMenuItem auMainMenuItem : auMainMenuTab.getAUMainMenuItemList()) {
                Log.v("MENU ITEM", auMainMenuItem.getTitle());
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

        if (id == R.id.news) {
            // Handle the camera action
        } else if (id == R.id.courses) {

        } else if (id == R.id.library) {

        } else if (id == R.id.cafeteria) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
