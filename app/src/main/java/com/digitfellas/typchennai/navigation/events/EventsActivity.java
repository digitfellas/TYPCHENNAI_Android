package com.digitfellas.typchennai.navigation.events;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.navigation.about.Pager;

public class EventsActivity extends RootActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Bundle mBundle;
    private boolean mIsEvent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_events);

        mToolBarTitle.setText("Events & Announcements");

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mIsEvent = mBundle.getBoolean(IntentConstant.IS_EVENT);
        }
        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tab_events);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Announcements"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager_events);

        //Creating our pager adapter
        EventPager adapter = new EventPager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        setTabIcons();

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTabIcons() {

        View view = LayoutInflater.from(this).inflate(R.layout.custom_textview, null);
        TextView tvTabText = (TextView) view.findViewById(R.id.tab);
        tvTabText.setText("Events");
        ImageView ivTabIcon = (ImageView) view.findViewById(R.id.iv_tabicon);
        ivTabIcon.setImageResource(R.drawable.ic_events);
        tabLayout.getTabAt(0).setCustomView(view);

        view = LayoutInflater.from(this).inflate(R.layout.custom_textview, null);
        TextView tvTabText1 = (TextView) view.findViewById(R.id.tab);
        tvTabText1.setText("Announcements");
        ImageView ivTabIcon1 = (ImageView) view.findViewById(R.id.iv_tabicon);
        ivTabIcon1.setImageResource(R.drawable.ic_notifications);
        tabLayout.getTabAt(1).setCustomView(view);

        if (mIsEvent) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        /*switch (tab.getPosition()) {

            case 0:
                mToolBarTitle.setText("Events");
                break;

            case 1:
                mToolBarTitle.setText("Announcements");
                break;

            default:
                break;
        }*/
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
