package com.digitfellas.typchennai.navigation.gallery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.GetGalleryResponse;
import com.digitfellas.typchennai.network.response.VideoResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by administrator on 09/06/18.
 */

public class VideoActivity extends RootActivity {

    private RecyclerView mRvGallery;
    private VideoAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_gallery_list);

        mRvGallery = (RecyclerView) findViewById(R.id.rv_gallery_list);
        mRvGallery.setLayoutManager(new LinearLayoutManager(this));

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mToolBarTitle.setText("Video");

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
        Call<VideoResponse> call = userService.getVideoResponse(Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    VideoResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {
                        if (workingCommitteeResponse.getVideos() != null && workingCommitteeResponse.getVideos() != null && workingCommitteeResponse.getVideos().size() > 0) {
                            mVideoAdapter = new VideoAdapter(VideoActivity.this, workingCommitteeResponse.getVideos());
                            mRvGallery.setAdapter(mVideoAdapter);
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
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

}
