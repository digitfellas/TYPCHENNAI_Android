package com.digitfellas.typchennai;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class RootFragment extends Fragment {

    protected static final String TAG = RootFragment.class.getSimpleName();

    protected ActionBar mActionBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mActionBar = ((RootActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("");

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
