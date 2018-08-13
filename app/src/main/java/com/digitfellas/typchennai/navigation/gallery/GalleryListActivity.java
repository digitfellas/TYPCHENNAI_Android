package com.digitfellas.typchennai.navigation.gallery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.navigation.working.WorkingAdapter;
import com.digitfellas.typchennai.navigation.working.WorkingCommitteeActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.GetGalleryResponse;
import com.digitfellas.typchennai.network.response.PhotoAlbum;
import com.digitfellas.typchennai.network.response.WorkingCommitteeResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by administrator on 08/06/18.
 */

public class GalleryListActivity extends RootActivity {

    private RecyclerView mRvGallery;
    private GalleryAdapter mGalleryAdapter;
    private Bundle mBundle;
    private boolean mIsFromVibhag = false;
    private String mVibhagName;

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

        mToolBarTitle.setText("Photo Album");

        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            mIsFromVibhag = mBundle.getBoolean(IntentConstant.ISFROMVIBHAG);
            mVibhagName = mBundle.getString(IntentConstant.VIBHAG_NAME);
        }

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
        Call<GetGalleryResponse> call = userService.getGallery(Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<GetGalleryResponse>() {
            @Override
            public void onResponse(Call<GetGalleryResponse> call, Response<GetGalleryResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    GetGalleryResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {
                        if (workingCommitteeResponse.getImage_path() != null && workingCommitteeResponse.getPhotoalbum() != null && workingCommitteeResponse.getPhotoalbum().size() > 0) {

                            if (mIsFromVibhag) {
                                List<PhotoAlbum> photoalbum = getVibhagAlbum(workingCommitteeResponse.getPhotoalbum());
                                mGalleryAdapter = new GalleryAdapter(GalleryListActivity.this, photoalbum, workingCommitteeResponse.getImage_path());
                                mRvGallery.setAdapter(mGalleryAdapter);
                            } else {
                                mGalleryAdapter = new GalleryAdapter(GalleryListActivity.this, workingCommitteeResponse.getPhotoalbum(), workingCommitteeResponse.getImage_path());
                                mRvGallery.setAdapter(mGalleryAdapter);
                            }
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
            public void onFailure(Call<GetGalleryResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

    private List<PhotoAlbum> getVibhagAlbum(List<PhotoAlbum> album) {

        List<PhotoAlbum> selectedAlbum = new ArrayList<>();

        for (int i=0; i<album.size(); i++){

            if(album.get(i).getAlbumtype().equalsIgnoreCase(mVibhagName)){
                selectedAlbum.add(album.get(i));
            }
        }
        return selectedAlbum;
    }

}
