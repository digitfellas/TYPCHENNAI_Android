package com.digitfellas.typchennai.navigation.vibhag;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.dashboard.DashboardActivity;
import com.digitfellas.typchennai.navigation.gallery.GalleryListActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.AboutUsResponse;
import com.digitfellas.typchennai.network.service.UserService;
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

public class VibhagDetailActivity extends RootActivity implements View.OnClickListener {

    private VibhagData vibhagData;
    private CirclePageIndicator indicator;
    private ExtendedViewPager mViewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private LatoRegularTextview mTvContact, mTvAbout;
    private CardView mCvGallery, mCvAbout, mCvCenteres;
    private ImageView mIvGallery, mIvAbout;
    private String[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_vibhag_detail);

        mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        mTvContact = (LatoRegularTextview) findViewById(R.id.tv_conatct);
        mTvAbout = (LatoRegularTextview) findViewById(R.id.tv_about);
        mCvGallery = (CardView) findViewById(R.id.cv_gallery);
        mCvAbout = (CardView) findViewById(R.id.cv_about);
        mCvCenteres = (CardView) findViewById(R.id.cv_centers);
        mIvAbout = (ImageView) findViewById(R.id.iv_about);
        mIvGallery = (ImageView) findViewById(R.id.iv_gallery);

        Intent intent = getIntent();
        if (intent != null) {
            vibhagData = (VibhagData) intent.getExtras().getSerializable("VIBHAG_DETAIL");
            if (vibhagData != null) {
                mToolBarTitle.setText(vibhagData.getName());
                mTvAbout.setText("About " + vibhagData.getName());
                //mIvAbout.setImageResource(vibhagData.getImage());
                getImageList();
                mViewPager.setAdapter(new TouchImageAdapter(this));
                if (NetworkUtils.isNetworkAvailable()) {
                    showProgressDialog(getResources().getString(R.string.please_wait));
                    getVibhagContact();
                } else {
                    showErrorDialog(getResources().getString(R.string.network_error));
                }
            }
        }
        mActionBack.setOnClickListener(this);
        mCvGallery.setOnClickListener(this);
        mCvAbout.setOnClickListener(this);
        mCvCenteres.setOnClickListener(this);

        if (vibhagData.getName().equalsIgnoreCase("Gyanshala")) {
            mCvCenteres.setVisibility(View.VISIBLE);
        } else {
            mCvCenteres.setVisibility(View.GONE);
        }


    }

    private void getVibhagContact() {
        showProgressDialog("Please wait..");
        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<AboutUsResponse> call = userService.getVibhagContactResponse(vibhagData.getUrlContact());
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    AboutUsResponse aboutUsResponse = response.body();
                    if (aboutUsResponse != null && aboutUsResponse.getStatus().equals("success")) {

                        if (aboutUsResponse.getContent() != null) {
                            mTvContact.setText(Html.fromHtml(aboutUsResponse.getContent().getContent()));
                        }
                    }
                } else {
                    APIError error = APIErrorUtil.parseError(response);
                    if (error != null) {
                        Logger.i("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.action_back:
                onBackPressed();
                break;

            case R.id.cv_about:
                Intent detailIntent = new Intent(VibhagDetailActivity.this, AboutVibhagDetailsActivity.class);
                detailIntent.putExtra("VIBHAG_DETAIL", (Serializable) vibhagData);
                startActivity(detailIntent);
                break;

            case R.id.cv_gallery:
                Intent intent = new Intent(VibhagDetailActivity.this, GalleryListActivity.class);
                intent.putExtra(IntentConstant.ISFROMVIBHAG, true);
                intent.putExtra(IntentConstant.VIBHAG_NAME, vibhagData.getmGalleryName());
                startActivity(intent);
                break;

            case R.id.cv_centers:
                startActivity(new Intent(VibhagDetailActivity.this, GyanshalaCentersActivity.class));
                break;

            default:
                break;
        }
    }


    class TouchImageAdapter extends PagerAdapter {

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

    private void getImageList() {
        switch (vibhagData.getName()) {
            case "ATDC":
                images = new String[]{"https://www.typchennai.com/images/banners/atdc-banner.jpg",
                        "https://www.typchennai.com/images/banners/atdc-banner1.jpg"};
                break;

            case "Gyanshala":
                images = new String[]{"https://www.typchennai.com/images/banners/gyanshala-banner.jpg",
                        "https://www.typchennai.com/images/banners/gyanshala-banner1.jpg"};
                break;

            case "Shiksha":
                images = new String[]{"https://www.typchennai.com/images/banners/shiksha-banner.jpg",
                        "https://www.typchennai.com/images/banners/shiksha-banner1.jpg"};
                break;

            case "Kishore Mandal":
                images = new String[]{"https://www.typchennai.com/images/banners/kishoremandal-banner.jpg",
                        "https://www.typchennai.com/images/banners/kishoremandal-banner1.jpg"};
                break;

            case "Library":
                images = new String[]{"https://www.typchennai.com/images/banners/library-banner.jpg",
                        "https://www.typchennai.com/images/banners/library-banner1.jpg"};
                break;

            case "Mobile App & Website":
                images = new String[]{"https://www.typchennai.com/images/banners/mobileapp-banner.jpg",
                        "https://www.typchennai.com/images/banners/mobileapp-banner1.jpg"};
                break;

            case "Sangathan":
                images = new String[]{"https://www.typchennai.com/images/banners/sanghatan-banner.jpg",
                        "https://www.typchennai.com/images/banners/sanghatan-banner1.jpg"};
                break;

            case "Vigyapan":
                images = new String[]{"https://www.typchennai.com/images/banners/vigyapan-banner.jpg",
                        "https://www.typchennai.com/images/banners/vigyapan-banner.jpg"};
                break;

            case "Prakashan":
                images = new String[]{"https://www.typchennai.com/images/banners/prakhashan-banner.jpg",
                        "https://www.typchennai.com/images/banners/prakhashan-banner1.jpg"};
                break;

            case "Consumables":
                images = new String[]{"https://www.typchennai.com/images/banners/consumable-banner.jpg",
                        "https://www.typchennai.com/images/banners/consumable-banner1.jpg"};
                break;

            case "Media":
                images = new String[]{"https://www.typchennai.com/images/banners/media-banner.jpg",
                        "https://www.typchennai.com/images/banners/media-banner.jpg"};
                break;

            case "bulletin":
                images = new String[]{"https://www.typchennai.com/images/uploads/vigyapan-banner.jpg"};
                break;

            default:
                break;


        }
    }


}
