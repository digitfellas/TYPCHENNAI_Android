package com.digitfellas.typchennai.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularEditText;
import com.digitfellas.typchennai.dashboard.DashboardActivity;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.LoginResponse;
import com.digitfellas.typchennai.network.response.RegistrationResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by administrator on 14/04/18.
 */

public class LoginActivity extends RootActivity implements View.OnClickListener {

    private LatoRegularEditText mEtMobileNo, mEtPassword;
    private LatoBoldTextview mTvSignUp, mTvForgotPassword, mTvLogin;
    private String mPassword = null, mMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_login);

        mEtMobileNo = (LatoRegularEditText) findViewById(R.id.et_mobile_no);
        mEtPassword = (LatoRegularEditText) findViewById(R.id.et_password);

        mTvLogin = (LatoBoldTextview) findViewById(R.id.tv_login);
        mTvForgotPassword = (LatoBoldTextview) findViewById(R.id.tv_forgot_password);
        mTvSignUp = (LatoBoldTextview) findViewById(R.id.tv_sign_up);

        mTvLogin.setOnClickListener(this);
        mTvSignUp.setOnClickListener(this);
        mTvForgotPassword.setOnClickListener(this);

        mToolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_login:
                mMobileNumber = mEtMobileNo.getText().toString();
                mPassword = mEtPassword.getText().toString();
                validateFields();
                break;

            case R.id.tv_forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;

            case R.id.tv_sign_up:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;

        }

    }

    private void validateFields() {

        if (!(mMobileNumber != null && mMobileNumber.length() > 0)) {
            mEtMobileNo.setError(getResources().getString(R.string.enter_mobile_number));
            mEtMobileNo.requestFocus();
            return;
        }


        if (mMobileNumber.length() < 9) {
            mEtMobileNo.setError(getResources().getString(R.string.enter_valid_mobile_number));
            mEtMobileNo.requestFocus();
            return;
        }


        if (!(mPassword != null && mPassword.length() > 0)) {
            mEtPassword.setError(getResources().getString(R.string.enter_password));
            mEtPassword.requestFocus();
            return;
        }

        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            callLoginApi();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }


    }

    private void callLoginApi() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<LoginResponse> call = userService.login(mMobileNumber, mPassword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    LoginResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success") && workingCommitteeResponse.getMessage().contains("LOGIN_SUCCESS")) {

                        Preferences.INSTANCE.putUserId(workingCommitteeResponse.getMember_id());
                        Preferences.INSTANCE.putAccessToken(workingCommitteeResponse.getApp_key());
                        Preferences.INSTANCE.putUserLoggedInStatus(true);
                        if(workingCommitteeResponse.getMember_type().equalsIgnoreCase("non_member")) {
                            Preferences.INSTANCE.putMember(false);
                        } else {
                            Preferences.INSTANCE.putMember(true);
                        }
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, workingCommitteeResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    APIError error = APIErrorUtil.parseError(response);
                    if (error != null) {
                        Logger.i("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

}
