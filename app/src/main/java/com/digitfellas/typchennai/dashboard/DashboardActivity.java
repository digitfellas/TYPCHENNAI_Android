package com.digitfellas.typchennai.dashboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.landing.LandingBaseActivity;
import com.digitfellas.typchennai.login.ForgotPasswordActivity;
import com.digitfellas.typchennai.login.RegistrationActivity;
import com.digitfellas.typchennai.navigation.directory.DirectoryActivity;
import com.digitfellas.typchennai.navigation.events.EventsActivity;
import com.digitfellas.typchennai.navigation.vibhag.VibhagData;
import com.digitfellas.typchennai.navigation.vibhag.VibhagDetailActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.RegistrationResponse;
import com.digitfellas.typchennai.network.response.SendDeviceTokenResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.DeviceUtil;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;
import com.digitfellas.typchennai.utils.swipeviewpager.ExtendedViewPager;
import com.digitfellas.typchennai.utils.swipeviewpager.TouchImageView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by administrator on 07/06/18.
 */

public class DashboardActivity extends LandingBaseActivity implements View.OnClickListener {

    private CirclePageIndicator indicator;
    private ExtendedViewPager mViewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private CardView mCvAtdc, mCvGyanshala, mCvEvents, mCvDirectory;
    private LatoRegularTextview mTvSendEmail, mTvVisitFb;
    private String mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_dashboard);

        mToolBarTitle.setText("TYP Chennai");

        mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new TouchImageAdapter(this));

        mCvAtdc = (CardView) findViewById(R.id.cv_atdc);
        mCvGyanshala = (CardView) findViewById(R.id.cv_gyanshala);
        mCvEvents = (CardView) findViewById(R.id.cv_events);
        mCvDirectory = (CardView) findViewById(R.id.cv_directory);

        mTvSendEmail = (LatoRegularTextview) findViewById(R.id.tv_send_email);
        mTvVisitFb = (LatoRegularTextview) findViewById(R.id.tv_visit_fb);

        if (Preferences.INSTANCE.isMember()) {
            mCvDirectory.setVisibility(View.VISIBLE);
        } else {
            mCvDirectory.setVisibility(View.INVISIBLE);
            hideMenuItem();
        }


        mCvAtdc.setOnClickListener(this);
        mCvGyanshala.setOnClickListener(this);
        mCvEvents.setOnClickListener(this);
        mCvDirectory.setOnClickListener(this);
        mTvSendEmail.setOnClickListener(this);
        mTvVisitFb.setOnClickListener(this);

        mActionBack.setImageDrawable(getResources().getDrawable(R.drawable.hamburger));

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        if (Preferences.INSTANCE.getFCMToken() != null) {
            if (NetworkUtils.isNetworkAvailable()) {
                showProgressDialog(getResources().getString(R.string.please_wait));
                sendRegistrationToServer();
            } else {
                showErrorDialog(getResources().getString(R.string.network_error));
            }
        }
    }

    private void hideMenuItem() {

        Menu nav_Menu = mNavigationView.getMenu();
        nav_Menu.findItem(R.id.draweritem_tn_directory).setVisible(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void sendRegistrationToServer() {

        if (Preferences.INSTANCE.isMember()) {
            mMember = "member";
        } else {
            mMember = "nonmember";
        }

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<SendDeviceTokenResponse> call = userService.sendDeviceToken(DeviceUtil.getSecureUDID(DashboardActivity.this),
                Preferences.INSTANCE.getFCMToken(), "Android", Preferences.INSTANCE.getUserId(), mMember, Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<SendDeviceTokenResponse>() {
            @Override
            public void onResponse(Call<SendDeviceTokenResponse> call, Response<SendDeviceTokenResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    SendDeviceTokenResponse sendDeviceTokenResponse = response.body();
                    if (sendDeviceTokenResponse != null && sendDeviceTokenResponse.getStatus().equals("success")) {

                    }
                } else {
                    APIError error = APIErrorUtil.parseError(response);
                    if (error != null) {
                        Logger.i("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<SendDeviceTokenResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cv_atdc:

                VibhagData vibhagData = new VibhagData();
                vibhagData.setName("ATDC");
                vibhagData.setUrlContent("atdc");
                vibhagData.setmGalleryName("atdc");
                vibhagData.setUrlContact("atdc-content");
                vibhagData.setImage(R.drawable.ic_calendar);
                Intent detailIntent = new Intent(DashboardActivity.this,VibhagDetailActivity.class);
                detailIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                detailIntent.putExtra("VIBHAG_DETAIL", (Serializable) vibhagData);
                startActivity(detailIntent);
                break;

            case R.id.cv_gyanshala:
                VibhagData vibhagData1 = new VibhagData();
                vibhagData1.setName("Gyanshala");
                vibhagData1.setUrlContent("gyanshala");
                vibhagData1.setmGalleryName("gyanshala");
                vibhagData1.setUrlContact("gyanshala-contact");
                vibhagData1.setImage(R.drawable.ic_calendar);
                Intent detailIntent1 = new Intent(DashboardActivity.this,VibhagDetailActivity.class);
                detailIntent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
                detailIntent1.putExtra("VIBHAG_DETAIL", (Serializable) vibhagData1);
                startActivity(detailIntent1);
                break;

            case R.id.cv_events:
                startActivity(new Intent(DashboardActivity.this, EventsActivity.class));
                break;

            case R.id.cv_directory:
                startActivity(new Intent(DashboardActivity.this, DirectoryActivity.class));
                break;

            case R.id.tv_send_email:
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","contact@typchennai.com", null));
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;

            case R.id.tv_visit_fb:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/typchennai"));
                startActivity(browserIntent);
                break;

            default:
                break;

        }
    }

    class TouchImageAdapter extends PagerAdapter {

        private String[] images = new String[]{"https://www.typchennai.com/images/banners/typ-banner",
                "https://www.typchennai.com/images/banners/typ-banner1.jpg"};
        private Context mContext;

        public TouchImageAdapter(Context context) {
            mContext = context;
        }

        //private int[] images = {R.drawable.house1, R.drawable.living_room, R.drawable.kitchen, R.drawable.hall};

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());

            Picasso.with(mContext)
                    .load(images[position])
                    .into(img);

            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
