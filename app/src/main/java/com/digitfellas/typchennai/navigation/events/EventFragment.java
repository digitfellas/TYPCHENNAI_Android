package com.digitfellas.typchennai.navigation.events;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootFragment;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.navigation.about.AboutUsActivity;
import com.digitfellas.typchennai.navigation.working.WorkingAdapter;
import com.digitfellas.typchennai.navigation.working.WorkingCommitteeActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.AboutUsResponse;
import com.digitfellas.typchennai.network.response.EventListResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends RootFragment {

    private EventsActivity mContext;
    private LatoRegularTextview mTvNoEvents;
    private RecyclerView mRvEvents;
    private EventListAdapter mEventAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = (EventsActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_events, container, false);

        mTvNoEvents = (LatoRegularTextview) view.findViewById(R.id.tv_no_event);
        mRvEvents = (RecyclerView) view.findViewById(R.id.rv_event_list);
        mRvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (NetworkUtils.isNetworkAvailable()) {
            mContext.showProgressDialog(getResources().getString(R.string.please_wait));
            callEventAPI();
        } else {
            mContext.showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void callEventAPI() {
        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<EventListResponse> call = userService.getEvents(Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<EventListResponse>() {
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
                mContext.hideProgressDialog();

                if (response.isSuccessful()) {
                    EventListResponse eventResponse = response.body();
                    if (eventResponse != null && eventResponse.getStatus().equals("success")) {

                        if (eventResponse.getEvents() != null && eventResponse.getEvents().size() > 0) {
                            mEventAdapter = new EventListAdapter(mContext, eventResponse.getEvents());
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
            public void onFailure(Call<EventListResponse> call, Throwable t) {
                mContext.hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }
}