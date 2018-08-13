package com.digitfellas.typchennai.navigation.gallery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.GetGalleryDetailResponse;
import com.digitfellas.typchennai.network.response.GetGalleryResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by administrator on 08/06/18.
 */

public class GalleryDetailActivity extends RootActivity {

    private RecyclerView mRvGallery;
    private GalleryDetailAdapter mGalleryAdapter;
    private Bundle mBundle;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_gallery_detail);

        mRvGallery = (RecyclerView) findViewById(R.id.rv_gallery_list);
        mRvGallery.setLayoutManager(new LinearLayoutManager(this));

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            mId = mBundle.getString(IntentConstant.ID);
        }

        mToolBarTitle.setText("Gallery");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            callGetGalleryList();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    private void callGetGalleryList() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<GetGalleryDetailResponse> call = userService.getAlbum(mId, Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<GetGalleryDetailResponse>() {
            @Override
            public void onResponse(Call<GetGalleryDetailResponse> call, Response<GetGalleryDetailResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    GetGalleryDetailResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {
                        if (workingCommitteeResponse.getImage_path() != null && workingCommitteeResponse.getPhotos() != null && workingCommitteeResponse.getPhotos().size() > 0) {
                            mGalleryAdapter = new GalleryDetailAdapter(GalleryDetailActivity.this, workingCommitteeResponse.getPhotos(), workingCommitteeResponse.getImage_path());
                            mRvGallery.setAdapter(mGalleryAdapter);
                            mToolBarTitle.setText(workingCommitteeResponse.getPhotoalbums().getTitle());
                         /*   mTvNoData.setVisibility(View.GONE);
                            mRvWorkingCommittee.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.VISIBLE);
                            mRvWorkingCommittee.setVisibility(View.GONE);*/
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
            public void onFailure(Call<GetGalleryDetailResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }
}
