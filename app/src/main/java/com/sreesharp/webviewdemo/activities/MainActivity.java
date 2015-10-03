package com.sreesharp.webviewdemo.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sreesharp.webviewdemo.R;
import com.sreesharp.webviewdemo.adapters.DemoPagerAdapter;
import com.sreesharp.webviewdemo.fragments.ListviewFragment;
import com.sreesharp.webviewdemo.fragments.WebviewFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements WebviewFragment.OnDataChangedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentPagerAdapter adapterViewPager  = new DemoPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        // Connect the TabLayout with viewpager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onDataChanged() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for(Fragment fragment: fragments)
                    if(fragment instanceof ListviewFragment)
                        ((ListviewFragment) fragment).refresh();

            }
        });
    }
}
