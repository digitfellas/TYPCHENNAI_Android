package com.digitfellas.typchennai.navigation.events;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.SessionStorage;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.EventListResponse;
import com.digitfellas.typchennai.network.response.Events;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends RootActivity {

    private Bundle mBundle;
    private String mId;
    private int mPosition;
    private Events mEvents;
    private LatoBoldTextview mTvEventName;
    private LatoRegularTextview mTvDescription, mTvDate, mTvLocation, mTvShortDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_event_details);

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

        mTvEventName = (LatoBoldTextview) findViewById(R.id.tv_event_name);
        mTvDescription = (LatoRegularTextview) findViewById(R.id.tv_description);
        mTvShortDescription = (LatoRegularTextview) findViewById(R.id.tv_short_description);
        mTvDate = (LatoRegularTextview) findViewById(R.id.tv_date);
        mTvLocation = (LatoRegularTextview) findViewById(R.id.tv_location);

        callEventDetails();
    }

    private void callEventDetails() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<EventListResponse> call = userService.getEventDetail(mId, Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    EventListResponse eventResponse = response.body();
                    if (eventResponse != null && eventResponse.getStatus().equals("success")) {

                        if (eventResponse.getEvent() != null) {
                            mEvents = eventResponse.getEvent();
                            mTvEventName.setText(mEvents.getTitle());
                            mTvShortDescription.setText(mEvents.getShort_description());
                            mTvDescription.setText(Html.fromHtml(mEvents.getDescription()));
                            mTvDate.setText(mEvents.getEvent_date() + " at " + mEvents.getEvent_time());
                            mTvLocation.setText(mEvents.getLocation());
                            mToolBarTitle.setText(mEvents.getTitle());
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
            public void onFailure(Call<EventListResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

}
