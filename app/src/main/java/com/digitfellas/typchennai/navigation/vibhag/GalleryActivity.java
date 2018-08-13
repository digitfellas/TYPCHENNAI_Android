package com.digitfellas.typchennai.navigation.vibhag;

import android.os.Bundle;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;

public class GalleryActivity extends RootActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_gallery);
        mToolBarTitle.setText("Gallery");

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
