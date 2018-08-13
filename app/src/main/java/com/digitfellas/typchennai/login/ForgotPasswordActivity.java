package com.digitfellas.typchennai.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularEditText;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.ForgotPasswordResponse;
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

public class ForgotPasswordActivity extends RootActivity implements View.OnClickListener {

    private LatoRegularEditText mEtMobileNo, mEtPin;
    private LatoBoldTextview mTvSubmit, mTvVerify;
    private String mMobileNumber, mPin, mMember;
    private LinearLayout mLlMobile, mLlPin;
    private LatoRegularTextview mTvErrorText, mTvResendCode;
    private Bundle bundle;
    private boolean isOTP = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_forgot_password);

        mToolBarTitle.setText("Forgot Password");
        bundle = getIntent().getExtras();

        if (bundle != null) {
            isOTP = bundle.getBoolean(IntentConstant.OTP_ONLY);
        }
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mEtMobileNo = (LatoRegularEditText) findViewById(R.id.et_mobile_no);
        mEtPin = (LatoRegularEditText) findViewById(R.id.et_pin);
        mTvSubmit = (LatoBoldTextview) findViewById(R.id.tv_submit);
        mTvVerify = (LatoBoldTextview) findViewById(R.id.tv_verify);
        mLlPin = (LinearLayout) findViewById(R.id.ll_pin);
        mLlMobile = (LinearLayout) findViewById(R.id.ll_mobile_no);


        mTvSubmit.setOnClickListener(this);
        mTvVerify.setOnClickListener(this);


        if (isOTP) {
            mLlMobile.setVisibility(View.GONE);
            mLlPin.setVisibility(View.VISIBLE);
        } else {
            mLlPin.setVisibility(View.GONE);
            mLlMobile.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_submit:
                mMobileNumber = mEtMobileNo.getText().toString();
                validateFields();
                break;

            case R.id.tv_resend_code:
                validateFields();
                break;

            case R.id.tv_verify:
                mPin = mEtPin.getText().toString();
                if (mPin != null && mPin.length() > 0) {

                    if (NetworkUtils.isNetworkAvailable()) {
                        showProgressDialog(getResources().getString(R.string.please_wait));
                        if (isOTP) {
                            verifyCode();
                        } else {
                            verifyOTP();
                        }
                        hideSoftKeyBoard();
                    } else {
                        showErrorDialog(getResources().getString(R.string.network_error));
                    }

                } else {
                    mEtPin.setError("Please enter your OTP received on your mobile");
                    mEtPin.requestFocus();
                }
                break;

            default:
                break;
        }
    }

    private void verifyOTP() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);

        if (Preferences.INSTANCE.isMember()) {
            mMember = "member";
        } else {
            mMember = "nonmember";
        }
        Call<ForgotPasswordResponse> call = userService.OTPverify(mPin, Preferences.INSTANCE.getUserId(), mMember);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    ForgotPasswordResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {

                        if (workingCommitteeResponse.getMessage().contains("success")) {
                            Intent homeIntent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, workingCommitteeResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, workingCommitteeResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    APIError error = APIErrorUtil.parseError(response);
                    if (error != null) {
                        Logger.i("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

    private void verifyCode() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<RegistrationResponse> call = userService.verifyOTP(Preferences.INSTANCE.getUserId(), mPin);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    RegistrationResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success")) {

                        if (workingCommitteeResponse.getMessage().contains("success")) {

                            Toast.makeText(ForgotPasswordActivity.this, "Registration completed, Please login to continue the app", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeIntent);
                            finish();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, workingCommitteeResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

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


        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            callForgotPasswordApi();
            hideSoftKeyBoard();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }

    }

    private void callForgotPasswordApi() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<ForgotPasswordResponse> call = userService.forgotPassword(mMobileNumber);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    ForgotPasswordResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equalsIgnoreCase("success")) {

                        if (workingCommitteeResponse.getMessage().contains("OTP sent successfully!")) {
                            Preferences.INSTANCE.putUserId(workingCommitteeResponse.getMember_id());
                            mLlMobile.setVisibility(View.GONE);
                            mLlPin.setVisibility(View.VISIBLE);
                            if (workingCommitteeResponse.getMember_type().equalsIgnoreCase("nonmember"))
                                Preferences.INSTANCE.putMember(false);
                            else
                                Preferences.INSTANCE.putMember(true);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, workingCommitteeResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }
}
