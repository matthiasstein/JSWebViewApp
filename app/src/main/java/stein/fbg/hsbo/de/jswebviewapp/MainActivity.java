package stein.fbg.hsbo.de.jswebviewapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Feature> featureList = new ArrayList<Feature>();
    private MenuItem previousSelectedBasemapMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        previousSelectedBasemapMenuItem = navigationView.getMenu().findItem(R.id.nav_streets);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.content_frame, new WebViewFragment(), "map_fragment");
            transaction.add(R.id.content_frame, new FeatureListFragment(), "feature_fragment");
            transaction.commit();
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

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        WebViewFragment webViewFragment = (WebViewFragment) fm.findFragmentByTag("map_fragment");
        FeatureListFragment featureListFragment = (FeatureListFragment) fm.findFragmentByTag("feature_fragment");

        if (id == R.id.nav_map) {
            hideAllFragments();
            transaction.show(webViewFragment);
        } else if (id == R.id.nav_features) {
            hideAllFragments();
            transaction.show(featureListFragment);
            refreshFeatureList();
        } else if (id == R.id.nav_streets) {
            previousSelectedBasemapMenuItem.setChecked(false);
            previousSelectedBasemapMenuItem = item;
            item.setChecked(true);
            webViewFragment.changeBasemap("streets");
        } else if (id == R.id.nav_topo) {
            previousSelectedBasemapMenuItem.setChecked(false);
            previousSelectedBasemapMenuItem = item;
            item.setChecked(true);
            webViewFragment.changeBasemap("topo");
        } else if (id == R.id.nav_satellite) {
            previousSelectedBasemapMenuItem.setChecked(false);
            previousSelectedBasemapMenuItem = item;
            item.setChecked(true);
            webViewFragment.changeBasemap("satellite");
        } else if (id == R.id.nav_light_gray) {
            previousSelectedBasemapMenuItem.setChecked(false);
            previousSelectedBasemapMenuItem = item;
            item.setChecked(true);
            webViewFragment.changeBasemap("gray");
        } else if (id == R.id.nav_dark_gray) {
            previousSelectedBasemapMenuItem.setChecked(false);
            previousSelectedBasemapMenuItem = item;
            item.setChecked(true);
            webViewFragment.changeBasemap("dark-gray");
        }

        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideAllFragments() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment fragment : fm.getFragments()) {
            if (fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    public void refreshFeatureList() {
        FragmentManager fm = getSupportFragmentManager();
        FeatureListFragment fragment = (FeatureListFragment) fm.findFragmentByTag("feature_fragment");
        if (fragment != null)
            fragment.refresh();
    }
}
