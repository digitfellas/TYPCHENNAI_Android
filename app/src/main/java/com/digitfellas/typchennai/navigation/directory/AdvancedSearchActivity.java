package com.digitfellas.typchennai.navigation.directory;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularEditText;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.GetDirectoryResponse;
import com.digitfellas.typchennai.network.response.Heads;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * Created by administrator on 07/06/18.
 */

public class AdvancedSearchActivity extends RootActivity implements View.OnClickListener {

    private EditText mEtName, mEtSurname, mEtMobileNo, mEtCity, mEtPin, mEtNative;
    private String name, surname, mobileno, city, pin, nativeloc;
    private String state = "All", gender = "All", maritalstatus = "All";
    private LatoBoldTextview mTvSearch;
    private DirectoryAdapter mDirectoryAdapter;
    private List<Heads> mHeadList, mSortedList;
    private RecyclerView mRvDirectory;
    private LinearLayout mLlSearch;
    private Spinner mSpGender, mSpMaritalStatus;
    private String[] genderArray = {"Select Gender", "Male", "Female"};
    private String[] maritalStatusArray = {"Select Marital Status", "Single", "Married"};
    private ArrayAdapter genderAdapter, maritalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_advanced_search);

        mRvDirectory = (RecyclerView) findViewById(R.id.rv_directory_list_search);

        mLlSearch = (LinearLayout) findViewById(R.id.ll_search);
        mEtName = (LatoRegularEditText) findViewById(R.id.et_name);
        mEtSurname = (LatoRegularEditText) findViewById(R.id.et_surname);
        mEtMobileNo = (LatoRegularEditText) findViewById(R.id.et_pincode);
        mEtCity = (LatoRegularEditText) findViewById(R.id.et_city);
        mEtPin = (LatoRegularEditText) findViewById(R.id.et_mobile_no);
        mEtNative = (LatoRegularEditText) findViewById(R.id.et_native_loc);

        mRvDirectory.setLayoutManager(new LinearLayoutManager(this));

        mTvSearch = (LatoBoldTextview) findViewById(R.id.tv_search);

        mTvSearch.setOnClickListener(this);


        mToolBarTitle.setText("Search Members");
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSpGender = (Spinner) findViewById(R.id.sp_gender);
        mSpMaritalStatus = (Spinner) findViewById(R.id.sp_marital_status);

        mSpGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = genderArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maritalstatus = maritalStatusArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpGender.setAdapter(genderAdapter);

        maritalAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalStatusArray);
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpMaritalStatus.setAdapter(maritalAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_search:

                if (mEtName.getText().toString() != null) {
                    name = mEtName.getText().toString();
                } else {
                    name = "";
                }

                if (mEtSurname.getText().toString() != null) {
                    surname = mEtSurname.getText().toString();
                } else {
                    surname = "";
                }

                if (mEtMobileNo.getText().toString() != null) {
                    mobileno = mEtMobileNo.getText().toString();
                } else {
                    mobileno = "";
                }

                if (mEtCity.getText().toString() != null) {
                    city = mEtCity.getText().toString();
                } else {
                    city = "";
                }

                if (mEtNative.getText().toString() != null) {
                    nativeloc = mEtNative.getText().toString();
                } else {
                    nativeloc = "";
                }

                if (mEtPin.getText().toString() != null) {
                    pin = mEtPin.getText().toString();
                } else {
                    pin = "";
                }


                //callSearchApi();
                callAdvancedSearchApi();
                break;

            default:
                break;
        }
    }

    private void callSearchApi() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<GetDirectoryResponse> call = userService.getSearchName(name, surname, nativeloc, state, city, pin, mobileno, gender, maritalstatus, Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<GetDirectoryResponse>() {
            @Override
            public void onResponse(Call<GetDirectoryResponse> call, Response<GetDirectoryResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    GetDirectoryResponse getDirectoryResponse = response.body();
                    if (getDirectoryResponse != null && getDirectoryResponse.getStatus().equals("success")) {

                        if (getDirectoryResponse.getHeads() != null && getDirectoryResponse.getHeads().size() > 0) {
                            mHeadList = new ArrayList<>(getDirectoryResponse.getHeads());
                            mDirectoryAdapter = new DirectoryAdapter(AdvancedSearchActivity.this, getDirectoryResponse.getHeads());
                            mRvDirectory.setAdapter(mDirectoryAdapter);
                            mLlSearch.setVisibility(View.GONE);
                            mRvDirectory.setVisibility(View.VISIBLE);
                        } /*else {
                            mTvNoEvents.setVisibility(View.VISIBLE);
                            mRvEvents.setVisibility(View.GONE);
                        }*/
                    }
                } else {
                    APIError error = APIErrorUtil.parseError(response);
                    if (error != null) {
                        Logger.i("Error");
                    }
                    mLlSearch.setVisibility(View.GONE);
                    mRvDirectory.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetDirectoryResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }

    private void callAdvancedSearchApi() {


        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        if (name != null && name.length() > 0) {
            data.put("name", name);
        }
        if (surname != null && surname.length() > 0) {
            data.put("surname", surname);
        }
        if (nativeloc != null && nativeloc.length() > 0) {
            data.put("native", nativeloc);
        }
        if (state != null && state.length() > 0 && !state.equalsIgnoreCase("All")) {
            data.put("state", state);
        }
        if (city != null && city.length() > 0) {
            data.put("city", city);
        }
        if (pin != null && pin.length() > 0) {
            data.put("pincode", pin);
        }
        if (mobileno != null && mobileno.length() > 0) {
            data.put("mobile_no", mobileno);
        }
        if (gender != null && gender.length() > 0 && !gender.equalsIgnoreCase("Select Gender")) {
            data.put("gender", gender);
        }
        if (maritalstatus != null && maritalstatus.length() > 0 && !maritalstatus.equalsIgnoreCase("Select Marital Status")) {
            data.put("marital_status", maritalstatus);
        }

        data.put("app_key", Preferences.INSTANCE.getAccessToken());


        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<GetDirectoryResponse> call = userService.advancedSearch(data);
        call.enqueue(new Callback<GetDirectoryResponse>() {
            @Override
            public void onResponse(Call<GetDirectoryResponse> call, Response<GetDirectoryResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    GetDirectoryResponse getDirectoryResponse = response.body();
                    if (getDirectoryResponse != null && getDirectoryResponse.getStatus().equals("success")) {

                        if (getDirectoryResponse.getMembers() != null && getDirectoryResponse.getMembers().size() > 0) {
                            mHeadList = new ArrayList<>(getDirectoryResponse.getMembers());
                            mDirectoryAdapter = new DirectoryAdapter(AdvancedSearchActivity.this, getDirectoryResponse.getMembers());
                            mRvDirectory.setAdapter(mDirectoryAdapter);
                            mLlSearch.setVisibility(View.GONE);
                            mRvDirectory.setVisibility(View.VISIBLE);
                        } else {
                            mLlSearch.setVisibility(View.VISIBLE);
                            mRvDirectory.setVisibility(View.GONE);
                        }
                    }
                } else {
                    APIError error = APIErrorUtil.parseError(response);
                    if (error != null) {
                        Logger.i("Error");
                    }
                    mLlSearch.setVisibility(View.GONE);
                    mRvDirectory.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetDirectoryResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });
    }
}
