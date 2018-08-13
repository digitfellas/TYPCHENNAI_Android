package com.digitfellas.typchennai.navigation.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootFragment;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.AnnouncementResponse;
import com.digitfellas.typchennai.network.response.EventListResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncementFragment extends RootFragment {

    private EventsActivity mContext;
    private LatoRegularTextview mTvNoNotifications;
    private RecyclerView mRvNotifications;
    private AnnouncementAdapter mAnnouncementAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = (EventsActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        mTvNoNotifications = (LatoRegularTextview) view.findViewById(R.id.tv_no_notification);
        mRvNotifications = (RecyclerView) view.findViewById(R.id.rv_notification_list);
        mRvNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (NetworkUtils.isNetworkAvailable()) {
            mContext.showProgressDialog(getResources().getString(R.string.please_wait));
            callNoitificationAPI();
        } else {
            mContext.showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void callNoitificationAPI() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<AnnouncementResponse> call = userService.getAnnouncements(Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<AnnouncementResponse>() {
            @Override
            public void onResponse(Call<AnnouncementResponse> call, Response<AnnouncementResponse> response) {
                mContext.hideProgressDialog();

                if (response.isSuccessful()) {
                    AnnouncementResponse announcementResponse = response.body();
                    if (announcementResponse != null && announcementResponse.getStatus().equals("success")) {

                        if (announcementResponse.getAnnouncements() != null && announcementResponse.getAnnouncements().size() > 0) {
                            mAnnouncementAdapter = new AnnouncementAdapter(mContext, announcementResponse.getAnnouncements());
                            mRvNotifications.setAdapter(mAnnouncementAdapter);
                            mTvNoNotifications.setVisibility(View.GONE);
                            mRvNotifications.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoNotifications.setVisibility(View.VISIBLE);
                            mRvNotifications.setVisibility(View.GONE);
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
            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
                mContext.hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

}
