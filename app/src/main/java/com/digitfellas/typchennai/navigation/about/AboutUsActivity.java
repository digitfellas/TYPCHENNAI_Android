package com.digitfellas.typchennai.navigation.about;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;

/**
 * Created by administrator on 07/06/18.
 */

public class AboutUsActivity extends RootActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_about_us);

        mToolBarTitle.setText("About TYP Chennai");

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("About TYP Chennai"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

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
        tvTabText.setText("About TYP Chennai");
        ImageView ivTabIcon = (ImageView) view.findViewById(R.id.iv_tabicon);
        ivTabIcon.setImageResource(R.drawable.ic_info);
        tabLayout.getTabAt(0).setCustomView(view);

        view = LayoutInflater.from(this).inflate(R.layout.custom_textview, null);
        TextView tvTabText1 = (TextView) view.findViewById(R.id.tab);
        tvTabText1.setText("History");
        ImageView ivTabIcon1 = (ImageView) view.findViewById(R.id.iv_tabicon);
        ivTabIcon1.setImageResource(R.drawable.ic_history);
        tabLayout.getTabAt(1).setCustomView(view);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
