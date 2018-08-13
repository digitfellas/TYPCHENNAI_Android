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
 * Created by administrator on 09/06/18.
 */

public class ChangePasswordActivity extends RootActivity implements View.OnClickListener {

    private LatoBoldTextview mTvChangePass;
    private String mPassword, mCnfPassword;
    private LatoRegularEditText mEtPassword, mEtConfirmPassword;
    private String mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_change_password);

        mEtPassword = (LatoRegularEditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (LatoRegularEditText) findViewById(R.id.et_cnf_password);

        mTvChangePass = (LatoBoldTextview) findViewById(R.id.tv_change_pass);
        mTvChangePass.setOnClickListener(this);

        mToolBarTitle.setText("Change Password");

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

            case R.id.tv_change_pass:
                mPassword = mEtPassword.getText().toString();
                mCnfPassword = mEtConfirmPassword.getText().toString();
                validateFields();
                break;
        }
    }

    private void validateFields() {

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
            callChangePassword();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }

    }

    private void callChangePassword() {

        if (Preferences.INSTANCE.isMember()) {
            mMember = "member";
        } else {
            mMember = "nonmember";
        }

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<ForgotPasswordResponse> call = userService.changePassword(mPassword, mCnfPassword, Preferences.INSTANCE.getUserId(), mMember);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    ForgotPasswordResponse workingCommitteeResponse = response.body();
                    if (workingCommitteeResponse != null && workingCommitteeResponse.getStatus().equals("success") && workingCommitteeResponse.getMessage().equalsIgnoreCase("success")) {
                        Toast.makeText(ChangePasswordActivity.this, "Your Password has been changed successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, workingCommitteeResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
