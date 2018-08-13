package com.digitfellas.typchennai.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularEditText;
import com.digitfellas.typchennai.navigation.gallery.GalleryDetailActivity;
import com.digitfellas.typchennai.navigation.gallery.GalleryDetailAdapter;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.GetGalleryDetailResponse;
import com.digitfellas.typchennai.network.response.RegistrationResponse;
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

public class RegistrationActivity extends RootActivity implements View.OnClickListener {

    private LatoBoldTextview mTvRegister, mTvLogin;
    private String mMobileNo, mPassword, mCnfPassword;
    private LatoRegularEditText mEtMobileNumber, mEtPassword, mEtConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_registration);

        mTvLogin = (LatoBoldTextview) findViewById(R.id.tv_login);

        mEtMobileNumber = (LatoRegularEditText) findViewById(R.id.et_mobile_no);
        mEtPassword = (LatoRegularEditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (LatoRegularEditText) findViewById(R.id.et_cnf_password);

        mTvRegister = (LatoBoldTextview) findViewById(R.id.tv_register);
        mTvRegister.setOnClickListener(this);

        mToolBarTitle.setText("Sign Up");

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_register:
                mMobileNo = mEtMobileNumber.getText().toString();
                mPassword = mEtPassword.getText().toString();
                mCnfPassword = mEtConfirmPassword.getText().toString();
                validateFields();
                break;

            default:
                break;
        }
    }

    private void validateFields() {

        if (!(mMobileNo != null && mMobileNo.length() > 0)) {
            mEtMobileNumber.setError(getResources().getString(R.string.enter_mobile_number));
            mEtMobileNumber.requestFocus();
            return;
        }

        if (!(mMobileNo != null && mMobileNo.length() == 10)) {
            mEtMobileNumber.setError(getResources().getString(R.string.enter_valid_mobile_number));
            mEtMobileNumber.requestFocus();
            return;
        }

        if (!(mPassword != null && mPassword.length() > 0)) {
            mEtPassword.setError(getResources().getString(R.string.enter_password));
            mEtPassword.requestFocus();
            return;
        }

        if (!(mCnfPassword != null && mCnfPassword.length() > 0)) {
            mEtConfirmPassword.setError(getResources().getString(R.string.enter_confirm_password));
            mEtConfirmPassword.requestFocus();
            return;
        }

        if (!mPassword.equals(mCnfPassword)) {
            mEtConfirmPassword.setError(getResources().getString(R.string.password_not_match));
            mEtConfirmPassword.requestFocus();
            return;
        }


        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            callRegisterAPI();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }

    }

    private void callRegisterAPI() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<RegistrationResponse> call = userService.signup(mMobileNo, mPassword, mCnfPassword);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    RegistrationResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {

                        Preferences.INSTANCE.putUserId(workingCommitteeResponse.getMember_id());
                        Intent intent = new Intent(RegistrationActivity.this, ForgotPasswordActivity.class);
                        intent.putExtra(IntentConstant.OTP_ONLY, true);
                        startActivity(intent);
                    }
                } else {
                    APIError error = APIErrorUtil.parseError(response);
                    if (error != null) {
                        Logger.i("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });


    }
}
