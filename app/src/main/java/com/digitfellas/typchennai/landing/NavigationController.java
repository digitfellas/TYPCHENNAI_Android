package com.digitfellas.typchennai.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.dashboard.DashboardActivity;
import com.digitfellas.typchennai.login.LoginActivity;
import com.digitfellas.typchennai.navigation.about.AboutUsActivity;
import com.digitfellas.typchennai.navigation.directory.DirectoryActivity;
import com.digitfellas.typchennai.navigation.downloads.DownloadActivity;
import com.digitfellas.typchennai.navigation.events.EventsActivity;
import com.digitfellas.typchennai.navigation.gallery.GalleryImageActivity;
import com.digitfellas.typchennai.navigation.vibhag.VibhagActivity;
import com.digitfellas.typchennai.navigation.working.WorkingCommitteeActivity;
import com.digitfellas.typchennai.preference.Preferences;


public class NavigationController implements NavigationView.OnNavigationItemSelectedListener {

    private final static int DRAWER_CLOSE_DELAY_MS = 350; // in milliseconds.

    private static final String NAV_ITEM_ID = "navItemId";

    private int mNavItemId;

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    private Context mContext;

    protected NavigationController(Context context, DrawerLayout drawerLayout) {
        mContext = context;
        mDrawerLayout = drawerLayout;
    }

    protected void init(Bundle savedInstanceState, NavigationView navigationView) {
        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.draweritem_home;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        mNavigationView = navigationView;

        mNavigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
    }

    protected void loadUserInformation() {
        /*ImageView ivProfilePic = (ImageView) mNavigationView.findViewById(R.id.drawer_IvProfile);
        LatoBoldTextview tvUsername = (LatoBoldTextview) mNavigationView.findViewById(R.id.drawer_TvUsername);
        LatoRegularTextview tvEmail = (LatoRegularTextview) mNavigationView.findViewById(R.id.drawer_TvEmail);

        tvUsername.setText("Dany");
        tvEmail.setText("mobileuser@gmail.com");

        ImageUtil.loadImageOrSmallText(mContext, null,
                tvUsername.getText().toString(), ivProfilePic,
                mContext.getResources().getColor(R.color.app_color_primary_dark));*/
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);

        // TODO: Handle item select.
        switch (mNavItemId) {

            case R.id.draweritem_home: {

                Intent intent = new Intent(mContext, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
                break;
            }

            case R.id.draweritem_about_typ: {
                mContext.startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            }

            case R.id.draweritem_typ_committee: {
                mContext.startActivity(new Intent(mContext, WorkingCommitteeActivity.class));
                break;
            }

            case R.id.draweritem_tn_directory: {

                if (Preferences.INSTANCE.isMember()) {
                    mContext.startActivity(new Intent(mContext, DirectoryActivity.class));
                }
                break;
            }

            case R.id.draweritem_vibhag: {
                mContext.startActivity(new Intent(mContext, VibhagActivity.class));
                break;
            }

            case R.id.draweritem_events: {
                mContext.startActivity(new Intent(mContext, EventsActivity.class));
                break;
            }

            case R.id.draweritem_gallery: {
                mContext.startActivity(new Intent(mContext, GalleryImageActivity.class));
                break;
            }

            case R.id.draweritem_edownloads: {
                mContext.startActivity(new Intent(mContext, DownloadActivity.class));
                break;
            }

            case R.id.draweritem_logout: {
                logout();
                break;
            }

            default:
                break;
        }

        return true;
    }

    public void logout() {
        Preferences.INSTANCE.putUserLoggedInStatus(false);
        Intent homeIntent = new Intent(mContext, LoginActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(homeIntent);
        ((RootActivity) mContext).finish();
    }
}


// Use this in onNavigationItemSelected if an animation is required for the user
// while clicking a navigation menu item and some activity is performed.
/*mHandler.postDelayed(new Runnable() {
@Override
public void run() {

        }
        }, DRAWER_CLOSE_DELAY_MS);*/
