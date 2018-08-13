package com.digitfellas.typchennai.navigation.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.navigation.vibhag.GalleryActivity;

/**
 * Created by administrator on 08/06/18.
 */

public class GalleryImageActivity extends RootActivity implements View.OnClickListener {

    private CardView mCvGallery, mCvVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_image_gallery);

        mCvGallery = (CardView) findViewById(R.id.cv_gallery);
        mCvVideo = (CardView) findViewById(R.id.cv_video);

        mToolBarTitle.setText("Gallery");
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCvGallery.setOnClickListener(this);
        mCvVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cv_gallery:
                startActivity(new Intent(GalleryImageActivity.this, GalleryListActivity.class));
                break;

            case R.id.cv_video:
                startActivity(new Intent(GalleryImageActivity.this, VideoActivity.class));
                break;

        }
    }
}
