package com.digitfellas.typchennai.navigation.downloads;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.dashboard.DashboardActivity;
import com.digitfellas.typchennai.navigation.gallery.GalleryAdapter;
import com.digitfellas.typchennai.navigation.gallery.GalleryListActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.DownloadResponse;
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

public class DownloadActivity extends RootActivity {

    private RecyclerView mRvDownloads;
    private DownloadAdapter mDownloadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_downloads);

        mRvDownloads = (RecyclerView) findViewById(R.id.rv_downloads);
        mRvDownloads.setLayoutManager(new LinearLayoutManager(this));

        mToolBarTitle.setText("E-Downloads");

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            callGetPDF();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    private void callGetPDF() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<DownloadResponse> call = userService.getPdf(Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    DownloadResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {
                        if (workingCommitteeResponse.getPdf_path() != null && workingCommitteeResponse.getPdfs() != null && workingCommitteeResponse.getPdfs().size() > 0) {
                            mDownloadAdapter = new DownloadAdapter(DownloadActivity.this, workingCommitteeResponse.getPdfs(), workingCommitteeResponse.getPdf_path());
                            mRvDownloads.setAdapter(mDownloadAdapter);
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
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }
}
