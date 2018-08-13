package com.digitfellas.typchennai.landing;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;


public class LandingBaseActivity extends RootActivity {

    protected NavigationController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavController = new NavigationController(this, mDrawerLayout);
        mNavController.init(savedInstanceState, mNavigationView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavController.loadUserInformation();
    }

    @Override
    protected void customizeActionBar(ActionBar actionBar, Toolbar toolbar) {
        super.customizeActionBar(actionBar, toolbar);
        actionBar.setHomeAsUpIndicator(R.drawable.hamburger);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavController.onSaveInstanceState(outState);
    }
}
