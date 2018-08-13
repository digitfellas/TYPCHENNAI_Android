package com.digitfellas.typchennai.navigation.about;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootFragment;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.AboutUsResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by administrator on 07/06/18.
 */

public class AboutFragment extends RootFragment {

    private LatoBoldTextview mTvTitle;
    private LatoRegularTextview mTvContent;
    private AboutUsActivity mContext;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = (AboutUsActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_about, container, false);

        mTvTitle = (LatoBoldTextview) view.findViewById(R.id.tv_title);
        mTvContent = (LatoRegularTextview) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (NetworkUtils.isNetworkAvailable()) {
            mContext.showProgressDialog(getResources().getString(R.string.please_wait));
            callAboutAPI();
        } else {
            mContext.showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void callAboutAPI() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<AboutUsResponse> call = userService.aboutUs();
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                mContext.hideProgressDialog();

                if (response.isSuccessful()) {
                    AboutUsResponse aboutUsResponse = response.body();
                    if (aboutUsResponse != null && aboutUsResponse.getStatus().equals("success")) {

                        mTvTitle.setText(aboutUsResponse.getContent().getTitle());
                        mTvContent.setText(Html.fromHtml(aboutUsResponse.getContent().getContent()));
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
                mContext.hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }
}
