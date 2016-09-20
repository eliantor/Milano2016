package com.aktor.training.course.gui;

import android.app.ActivityOptions;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.tool.DataBindingBuilder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.transition.Explode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.aktor.training.course.R;
import com.aktor.training.course.databinding.MainGuiActivityBinding;

import java.util.List;

/**
 * Created by aktor on 19/09/16.
 */

public class MainGuiActivity extends AppCompatActivity implements OnActionPerformed{

    private MainGuiActivityBinding mBindings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_gui_activity);
        mBindings = DataBindingUtil.setContentView(this, R.layout.main_gui_activity);
        mBindings.setCallbacks(this);
        setSupportActionBar(mBindings.pagerLayout.toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(new DrawerArrowDrawable(this));
        ab.setDisplayHomeAsUpEnabled(true);
//
        setupDrawerNavigation();
        setupPages();


    }

    public interface Call {
        //(a)->{}
        public void doSomething(String a);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case android.R.id.home:
                mBindings.drawer.openDrawer(GravityCompat.START);
                return true;

        }
        return true;
    }

    private void setupDrawerNavigation(){

        mBindings.navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mBindings.drawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void setupPages(){
        ViewPager pager = mBindings.pagerLayout.pager;
        PagesAdapter adapter = new PagesAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        TabLayout tabs = mBindings.pagerLayout.tabs;
        tabs.setupWithViewPager(pager);
    }

    @Override
    public void performFrequentAction() {
        Snackbar.make(mBindings.pagerLayout.fab,"WOW",Snackbar.LENGTH_LONG)
                .setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
        }).show();
    }

    static class PagesAdapter extends FragmentPagerAdapter{
        public PagesAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new FragmentList();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page "+position;
        }
    }

    
}
