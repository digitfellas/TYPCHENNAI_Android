package com.digitfellas.typchennai.navigation.directory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularEditText;
import com.digitfellas.typchennai.navigation.events.EventListAdapter;
import com.digitfellas.typchennai.network.APIError;
import com.digitfellas.typchennai.network.APIErrorUtil;
import com.digitfellas.typchennai.network.RetrofitAdapter;
import com.digitfellas.typchennai.network.response.EventListResponse;
import com.digitfellas.typchennai.network.response.GetDirectoryResponse;
import com.digitfellas.typchennai.network.response.Heads;
import com.digitfellas.typchennai.network.service.UserService;
import com.digitfellas.typchennai.preference.Preferences;
import com.digitfellas.typchennai.utils.Logger;
import com.digitfellas.typchennai.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectoryActivity extends RootActivity implements View.OnClickListener {

    private RadioButton mRbName, mRbSurName, mRbNative, mRbPin;
    private RecyclerView mRvDirectory;
    private RadioGroup mRadioGroup;
    private boolean isName = true, isSurName = false, isNative = false, isPin = false;
    private DirectoryAdapter mDirectoryAdapter;
    private List<Heads> mHeadList, mSortedList;
    private LatoRegularEditText mEtSearch;
    private LatoBoldTextview mTvAdvancedSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_directory);

        mRvDirectory = (RecyclerView) findViewById(R.id.rv_directory_list);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg);

        mTvAdvancedSearch = (LatoBoldTextview) findViewById(R.id.tv_advanced_search);
        mEtSearch = (LatoRegularEditText) findViewById(R.id.et_search);
        mRbName = (RadioButton) findViewById(R.id.rb_name);
        mRbSurName = (RadioButton) findViewById(R.id.rb_surname);
        mRbNative = (RadioButton) findViewById(R.id.rb_native);
        mRbPin = (RadioButton) findViewById(R.id.rb_pin);

        mRvDirectory.setLayoutManager(new LinearLayoutManager(this));

        mRbName.setOnClickListener(this);
        mRbSurName.setOnClickListener(this);
        mRbNative.setOnClickListener(this);
        mRbPin.setOnClickListener(this);
        mTvAdvancedSearch.setOnClickListener(this);

        mToolBarTitle.setText("Tamilnadu Directory");
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mRbName.isChecked()) {

                }
                if (mRbSurName.isChecked()){

                }

                if (mRbNative.isChecked()){

                }

                if (mRbPin.isChecked()){
                }
            }
        });*/

        if (Preferences.INSTANCE.isMember()) {
            if (NetworkUtils.isNetworkAvailable()) {
                showProgressDialog(getResources().getString(R.string.please_wait));
                callGetDirectoryApi();
            } else {
                showErrorDialog(getResources().getString(R.string.network_error));
            }
        }

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(mEtSearch.getText().toString().toUpperCase());
            }
        });
    }

    private void filter(String searchText) {

        List<Heads> filterList = new ArrayList<>();

        if (mHeadList != null && mHeadList.size() > 0) {

            for (int i = 0; i < mHeadList.size(); i++) {

                if (mHeadList.get(i).getName().contains(searchText) || mHeadList.get(i).getSurname().contains(searchText) ||
                        mHeadList.get(i).getmNative().contains(searchText) || mHeadList.get(i).getCity_name().contains(searchText) ||
                        mHeadList.get(i).getRes_pin_code().contains(searchText.toLowerCase())) {

                    filterList.add(mHeadList.get(i));
                }

            }
        }
        mDirectoryAdapter.filterList(filterList);
    }

    private void callGetDirectoryApi() {

        UserService userService = RetrofitAdapter.getRetrofit().create(UserService.class);
        Call<GetDirectoryResponse> call = userService.getDirectoryList(Preferences.INSTANCE.getAccessToken());
        call.enqueue(new Callback<GetDirectoryResponse>() {
            @Override
            public void onResponse(Call<GetDirectoryResponse> call, Response<GetDirectoryResponse> response) {
                hideProgressDialog();

                if (response.isSuccessful()) {
                    GetDirectoryResponse getDirectoryResponse = response.body();
                    if (getDirectoryResponse != null && getDirectoryResponse.getStatus().equals("success")) {

                        if (getDirectoryResponse.getHeads() != null && getDirectoryResponse.getHeads().size() > 0) {
                            mHeadList = new ArrayList<>(getDirectoryResponse.getHeads());
                            mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, getDirectoryResponse.getHeads());
                            mRvDirectory.setAdapter(mDirectoryAdapter);
                            //mTvNoEvents.setVisibility(View.GONE);
                            //mRvEvents.setVisibility(View.VISIBLE);
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
                }
            }

            @Override
            public void onFailure(Call<GetDirectoryResponse> call, Throwable t) {
                hideProgressDialog();
                APIErrorUtil.getDefaultError(null);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rb_name:
                mRbName.setBackgroundColor(getResources().getColor(R.color.red));
                mRbSurName.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbNative.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbPin.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                // True - Ascending order, False - Descending order
                if (isName) {
                    isName = false;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortNameAscending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                } else {
                    isName = true;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortNameDescending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                }
                break;


            case R.id.rb_surname:
                mRbSurName.setBackgroundColor(getResources().getColor(R.color.red));
                mRbName.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbNative.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbPin.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                if (isSurName) {
                    isSurName = false;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortSurNameAscending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                } else {
                    isSurName = true;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortSurNameDescending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                }
                break;


            case R.id.rb_native:
                mRbNative.setBackgroundColor(getResources().getColor(R.color.red));
                mRbSurName.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbName.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbPin.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                if (isNative) {
                    isNative = false;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortNativeAscending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                } else {
                    isNative = true;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortNativeDescending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                }
                break;

            case R.id.rb_pin:
                mRbPin.setBackgroundColor(getResources().getColor(R.color.red));
                mRbNative.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbSurName.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                mRbName.setBackgroundColor(getResources().getColor(R.color.app_red_color));
                if (isPin) {
                    isPin = false;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortPinAscending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                } else {
                    isPin = true;
                    mDirectoryAdapter = new DirectoryAdapter(DirectoryActivity.this, sortPinDescending(mHeadList));
                    mRvDirectory.setAdapter(mDirectoryAdapter);
                    mDirectoryAdapter.notifyDataSetChanged();
                }
                break;


            case R.id.tv_advanced_search:
                startActivity(new Intent(DirectoryActivity.this, AdvancedSearchActivity.class));
                break;
        }

    }

    private List<Heads> sortNameAscending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return headsList;
    }

    private List<Heads> sortNameDescending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });

        return headsList;
    }

    private List<Heads> sortSurNameAscending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o1.getSurname().compareTo(o2.getSurname());
            }
        });

        return headsList;
    }

    private List<Heads> sortSurNameDescending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o2.getSurname().compareTo(o1.getSurname());
            }
        });

        return headsList;
    }

    private List<Heads> sortNativeAscending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o1.getmNative().compareTo(o2.getmNative());
            }
        });

        return headsList;
    }

    private List<Heads> sortNativeDescending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o2.getmNative().compareTo(o1.getmNative());
            }
        });

        return headsList;
    }

    private List<Heads> sortPinAscending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o1.getRes_pin_code().compareTo(o2.getRes_pin_code());
            }
        });

        return headsList;
    }

    private List<Heads> sortPinDescending(List<Heads> headsList) {

        Collections.sort(headsList, new Comparator<Heads>() {
            @Override
            public int compare(Heads o1, Heads o2) {
                return o2.getRes_pin_code().compareTo(o1.getRes_pin_code());
            }
        });

        return headsList;
    }
}
