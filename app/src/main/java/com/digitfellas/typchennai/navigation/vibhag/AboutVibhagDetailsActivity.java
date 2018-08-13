package com.digitfellas.typchennai.navigation.vibhag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
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

public class AboutVibhagDetailsActivity extends RootActivity {

    private VibhagData vibhagData;
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_about_vibhag_details);
        mWebview = (WebView) findViewById(R.id.wv_about);
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WebSettings settings = mWebview.getSettings();
        settings.setMinimumFontSize(18);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        mWebview.setWebChromeClient(new WebChromeClient());
        //mWebview.setInitialScale(2);
        //mWebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        //mWebview.getSettings().setLoadWithOverviewMode(true);
        //mWebview.getSettings().setUseWideViewPort(true);
        Intent intent = getIntent();
        if(intent!=null) {
            vibhagData = (VibhagData) intent.getExtras().getSerializable("VIBHAG_DETAIL");
            if (vibhagData != null) {
                mToolBarTitle.setText(vibhagData.getName());
                getVibhagContact();
            }
        }
        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            getVibhagContact();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }

    }

    private void getVibhagContact() {
        showProgressDialog("Please wait..");
        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<AboutUsResponse> call = userService.getVibhagContactResponse(vibhagData.getUrlContent());
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    AboutUsResponse aboutUsResponse = response.body();
                    if (aboutUsResponse != null && aboutUsResponse.getStatus().equals("success")) {

                        if (aboutUsResponse.getContent() != null) {

                            String head = "<head><meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" /></head>";
                            String closedTag = "</body></html>";
                            String changeFontHtml = head + aboutUsResponse.getContent().getContent() + closedTag;

                            mWebview.loadDataWithBaseURL(null, changeFontHtml,
                                    "text/html", "UTF-8", null);
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
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }
}
