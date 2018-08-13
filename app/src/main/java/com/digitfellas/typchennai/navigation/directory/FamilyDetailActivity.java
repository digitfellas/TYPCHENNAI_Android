package com.digitfellas.typchennai.navigation.directory;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.IntentConstant;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.FamilyDetailResponse;
import com.digitfellas.typchennai.network.response.GetDirectoryResponse;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyDetailActivity extends RootActivity {

    private LatoBoldTextview mTvName;
    private LatoRegularTextview mTvResAddress, mTvOfficeAddress;
    private String mId, mResDetail, mOffDetail;
    private Bundle mBundle;
    private RecyclerView mRvFamilyMembers;
    private FamilyDetailAdapter familyDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_family_detail);
        mTvName = (LatoBoldTextview) findViewById(R.id.tv_name);
        mTvResAddress = (LatoRegularTextview) findViewById(R.id.tv_res_address_value);
        mTvOfficeAddress = (LatoRegularTextview) findViewById(R.id.tv_off_address_value);
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mId = mBundle.getString(IntentConstant.FAMILY_ID);
        }

        mToolBarTitle.setText("Family Detail");
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRvFamilyMembers = (RecyclerView) findViewById(R.id.rv_family_members);
        mRvFamilyMembers.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.isNetworkAvailable()) {
            showProgressDialog(getResources().getString(R.string.please_wait));
            getFamilyDetails();
        } else {
            showErrorDialog(getResources().getString(R.string.network_error));
        }
    }

    private void getFamilyDetails() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<FamilyDetailResponse> call = userService.getFamilyDetails(mId, Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<FamilyDetailResponse>() {
            @Override
            public void onResponse(Call<FamilyDetailResponse> call, Response<FamilyDetailResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    FamilyDetailResponse familyDetailResponse = response.body();

                    if (familyDetailResponse != null && familyDetailResponse.getStatus().equals("success")) {

                        if (familyDetailResponse.getHead() != null) {
                            mTvName.setText(familyDetailResponse.getHead().getName() + " " + familyDetailResponse.getHead().getSurname());
                        }

                        if (familyDetailResponse.getRes_address() != null) {

                            if (familyDetailResponse.getRes_address().getRes_address_1() != null && familyDetailResponse.getRes_address().getRes_address_1().length() > 0) {
                                mResDetail = familyDetailResponse.getRes_address().getRes_address_1();
                            }
                            if (familyDetailResponse.getRes_address().getRes_address_2() != null && familyDetailResponse.getRes_address().getRes_address_2().length() > 0) {
                                mResDetail = mResDetail + ", " + familyDetailResponse.getRes_address().getRes_address_2();
                            }
                            if (familyDetailResponse.getRes_address().getRes_address_3() != null && familyDetailResponse.getRes_address().getRes_address_3().length() > 0) {
                                mResDetail = mResDetail + ", " + familyDetailResponse.getRes_address().getRes_address_3();
                            }

                            if (familyDetailResponse.getRes_city() != null) {
                                mResDetail = mResDetail + ", " + familyDetailResponse.getRes_city();
                            }

                            if (familyDetailResponse.getRes_district() != null) {
                                mResDetail = mResDetail + ", " + familyDetailResponse.getRes_district();
                            }

                            if (familyDetailResponse.getRes_state() != null) {
                                mResDetail = mResDetail + ", " + familyDetailResponse.getRes_state();
                            }

                            if (familyDetailResponse.getRes_address().getRes_pin_code() != null) {
                                mResDetail = mResDetail + " - " + familyDetailResponse.getRes_address().getRes_pin_code();
                            }

                            if (familyDetailResponse.getRes_address().getRes_std_code() != null && familyDetailResponse.getRes_address().getRes_phone() != null) {
                                mResDetail = mResDetail + "\n" + familyDetailResponse.getRes_address().getRes_std_code() + "-" + familyDetailResponse.getRes_address().getRes_phone();
                            }

                            mTvResAddress.setText(mResDetail);
                        }

                        if (familyDetailResponse.getOff_address() != null) {

                            if (familyDetailResponse.getOff_address().getOff_address_1() != null && familyDetailResponse.getOff_address().getOff_address_1().length() > 0) {
                                mOffDetail = familyDetailResponse.getOff_address().getOff_address_1();
                            }
                            if (familyDetailResponse.getOff_address().getOff_address_2() != null && familyDetailResponse.getOff_address().getOff_address_2().length() > 0) {
                                mOffDetail = mOffDetail + ", " + familyDetailResponse.getOff_address().getOff_address_2();
                            }
                            if (familyDetailResponse.getOff_address().getOff_address_3() != null && familyDetailResponse.getOff_address().getOff_address_3().length() > 0) {
                                mOffDetail = mOffDetail + ", " + familyDetailResponse.getOff_address().getOff_address_3();
                            }

                            if (familyDetailResponse.getOff_city() != null) {
                                mOffDetail = mOffDetail + ", " + familyDetailResponse.getOff_city();
                            }

                            if (familyDetailResponse.getOff_district() != null) {
                                mOffDetail = mOffDetail + ", " + familyDetailResponse.getOff_district();
                            }

                            if (familyDetailResponse.getOff_state() != null) {
                                mOffDetail = mOffDetail + ", " + familyDetailResponse.getOff_state();
                            }

                            if (familyDetailResponse.getOff_address().getOff_pin_code() != null) {
                                mOffDetail = mOffDetail + " - " + familyDetailResponse.getOff_address().getOff_pin_code();
                            }

                            if (familyDetailResponse.getOff_address().getOff_std_code() != null && familyDetailResponse.getOff_address().getOff_phone() != null) {
                                mOffDetail = mOffDetail + "\n" + familyDetailResponse.getOff_address().getOff_std_code() + "-" + familyDetailResponse.getOff_address().getOff_phone();
                            }

                            mTvOfficeAddress.setText(mOffDetail);
                        }

                        if (familyDetailResponse.getMembers() != null && familyDetailResponse.getMembers().size() > 0) {
                            mRvFamilyMembers.setVisibility(View.VISIBLE);
                            familyDetailAdapter = new FamilyDetailAdapter(FamilyDetailActivity.this, familyDetailResponse.getMembers());
                            mRvFamilyMembers.setAdapter(familyDetailAdapter);
                        } else {
                            mRvFamilyMembers.setVisibility(View.GONE);
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
            public void onFailure(Call<FamilyDetailResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }
}
