package com.digitfellas.typchennai.navigation.working;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.AboutUsResponse;
import com.digitfellas.typchennai.network.response.WorkingCommitteeResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by administrator on 07/06/18.
 */

public class WorkingCommitteeActivity extends RootActivity {

    private RecyclerView mRvWorkingCommittee;
    private WorkingAdapter mWorkingAdapter;
    private LatoRegularTextview mTvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.actvity_working_committee);

        mTvNoData = (LatoRegularTextview) findViewById(R.id.tv_no_data);
        mRvWorkingCommittee = (RecyclerView) findViewById(R.id.rv_working_committee);
        mRvWorkingCommittee.setLayoutManager(new LinearLayoutManager(this));

        mToolBarTitle.setText("Working Committee");
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
            callGetWorkingCommitteeApi();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    private void callGetWorkingCommitteeApi() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<WorkingCommitteeResponse> call = userService.getWorkingCommittee(Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<WorkingCommitteeResponse>() {
            @Override
            public void onResponse(Call<WorkingCommitteeResponse> call, Response<WorkingCommitteeResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    WorkingCommitteeResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {
                        if (workingCommitteeResponse.getWorking_committee() != null && workingCommitteeResponse.getWorking_committee().size() > 0) {
                            mWorkingAdapter = new WorkingAdapter(WorkingCommitteeActivity.this, workingCommitteeResponse.getWorking_committee());
                            mRvWorkingCommittee.setAdapter(mWorkingAdapter);
                            mTvNoData.setVisibility(View.GONE);
                            mRvWorkingCommittee.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.VISIBLE);
                            mRvWorkingCommittee.setVisibility(View.GONE);
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
            public void onFailure(Call<WorkingCommitteeResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }
}
