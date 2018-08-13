package com.digitfellas.typchennai.navigation.vibhag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.navigation.events.EventListAdapter;
import com.digitfellas.typchennai.navigation.events.EventsActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.GyanshalasResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GyanshalaCentersActivity extends RootActivity {

    private LatoRegularTextview mTvNoEvents;
    private RecyclerView mRvEvents;
    private GyanshalasAdapter mEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_gyanshala_centers);
        mToolBarTitle.setText("Gyanshala Centres");
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRvEvents = (RecyclerView)findViewById(R.id.rv_centers);
        mTvNoEvents = (LatoRegularTextview) findViewById(R.id.tv_no_event);
        mRvEvents.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            getGyanshalas();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    private void getGyanshalas() {
        showProgressDialog("Please wait..");
        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<GyanshalasResponse> call = userService.getGyanshalas();
        call.enqueue(new Callback<GyanshalasResponse>() {
            @Override
            public void onResponse(Call<GyanshalasResponse> call, Response<GyanshalasResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    GyanshalasResponse gyanshalasResponse = response.body();
                    if (gyanshalasResponse != null && gyanshalasResponse.getStatus().equals("success")) {
                        if(gyanshalasResponse.getGyanshalas()!=null && gyanshalasResponse.getGyanshalas().size()>0){
                                mEventAdapter = new GyanshalasAdapter(GyanshalaCentersActivity.this, gyanshalasResponse.getGyanshalas());
                                mRvEvents.setAdapter(mEventAdapter);
                                mTvNoEvents.setVisibility(View.GONE);
                                mRvEvents.setVisibility(View.VISIBLE);
                            } else {
                                mTvNoEvents.setVisibility(View.VISIBLE);
                                mRvEvents.setVisibility(View.GONE);
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
            public void onFailure(Call<GyanshalasResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }
}
