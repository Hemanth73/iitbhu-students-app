package com.example.iitbhustudentsapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.iitbhustudentsapp.R;
import com.example.iitbhustudentsapp.fragments.DashboardFragment;
import com.example.iitbhustudentsapp.fragments.EventsFragment;
import com.example.iitbhustudentsapp.fragments.NewsFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    boolean backPressedOnce = false;
    private FrameLayout mContentView;
    private View homeView, messView, complaintView, contactsView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subscriptions:
                //add the function to perform here
                Intent intent = new Intent(MainActivity.this, SubscriptionActivity.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mContentView = findViewById(R.id.content_view);
        LayoutInflater inflater = getLayoutInflater();
        homeView = inflater.inflate(R.layout.layout_home, null);
        messView = inflater.inflate(R.layout.layout_mess, null);
        complaintView = inflater.inflate(R.layout.layout_complaints, null);
        contactsView = inflater.inflate(R.layout.layout_contacts, null);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        mNavigationView.setCheckedItem(menuItem.getItemId());
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                loadHomeView();
                                break;
                            case R.id.nav_mess_menu:
                                loadMessView();
                                break;
                            case R.id.nav_complaint_box:
                                loadComplaintView();
                                break;
                            case R.id.nav_important_contacts:
                                loadContactsView();
                                break;
                        }
                        return true;
                    }
                });

        mNavigationView.getMenu().getItem(0).setChecked(true);
        initHomeView();
        loadHomeView();

    }

    private void initHomeView() {
        BottomNavigationView.OnNavigationItemSelectedListener mOnBottomNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_news:
                        fragment = new NewsFragment();
                        break;
                    case R.id.navigation_events:
                        fragment = new EventsFragment();
                        break;
                    case R.id.navigation_me:
                        fragment = new DashboardFragment();
                        break;
                }
                return loadFragment(fragment);
            }

            private boolean loadFragment(Fragment fragment) {
                // switching fragment
                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                    return true;
                }
                return false;
            }
        };

        BottomNavigationView navigation = (BottomNavigationView) homeView.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnBottomNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragment_container, new NewsFragment())
                .commit();

    }

    private void initComplaintView() {

    }

    private void loadHomeView() {
        mContentView.removeAllViews();
        mContentView.addView(homeView);
    }

    private void loadMessView() {
        mContentView.removeAllViews();
        mContentView.addView(messView);
    }

    private void loadComplaintView() {
        mContentView.removeAllViews();
        mContentView.addView(complaintView);
    }

    private void loadContactsView() {
        mContentView.removeAllViews();
        mContentView.addView(contactsView);
    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.backPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressedOnce = false;
            }
        }, 2000);
    }
}
