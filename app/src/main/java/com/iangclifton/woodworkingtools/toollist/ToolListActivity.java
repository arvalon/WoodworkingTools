package com.iangclifton.woodworkingtools.toollist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.iangclifton.woodworkingtools.R;
import com.iangclifton.woodworkingtools.ToolType;

import java.util.List;
import java.util.Map;

// TODO: 14.02.2019 replace OnPreDrawListener with lamba
public class ToolListActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private static final String EXTRA_TOOL_TYPE = "com.iangclifton.woodworkingtools.TOOL_TYPE";

    //private ToolType mToolType;

    private ViewPager mViewPager;

    public static void startActivity(Activity activity, ToolType toolType, Bundle activityOptions) {
        final Intent intent = new Intent(activity, ToolListActivity.class);
        intent.putExtra(EXTRA_TOOL_TYPE, toolType);
        ActivityCompat.startActivity(activity, intent, activityOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_list);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ToolType mToolType = (ToolType) getIntent().getSerializableExtra(EXTRA_TOOL_TYPE);

        if (mToolType == null) {
            throw new IllegalStateException("ToolType not available as extra; use startActivity");
        }

        setTitle(mToolType.getToolNameResourceId());

        // Set up tabs
        mViewPager = findViewById(R.id.viewpager);

        final TabLayout tabLayout = findViewById(R.id.tabs);

        final ToolPagerAdapter toolPagerAdapter =
                new ToolPagerAdapter(getSupportFragmentManager(), getResources(), mToolType);

        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        mViewPager.setAdapter(toolPagerAdapter);

        tabLayout.addOnTabSelectedListener(this);

        // Handle animation from previous activity
        postponeEnterTransition();

        mViewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                mViewPager.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }

        });

        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (mViewPager.getCurrentItem() != 0) {
                    // Not displaying the about page, which has the hero image
                    names.clear();
                    sharedElements.clear();
                }
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }

    @Override
    public void onTabReselected(TabLayout.Tab tab) { }
}
