package com.digitfellas.typchennai.navigation.events;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;
import com.digitfellas.typchennai.SessionStorage;
import com.digitfellas.typchennai.common.customtextview.LatoBoldTextview;
import com.digitfellas.typchennai.common.customtextview.LatoRegularTextview;
import com.digitfellas.typchennai.network.response.Announcements;

public class AnnouncementDetailActivity extends RootActivity {

    private LatoBoldTextview mTvEventName;
    private LatoRegularTextview mTvDescription;
    private Announcements announcements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_announcement_detail);

        mTvEventName = (LatoBoldTextview) findViewById(R.id.tv_announcement_name);
        mTvDescription = (LatoRegularTextview) findViewById(R.id.tv_description);

        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        announcements = SessionStorage.getInstance().getAnnouncements();

        mTvEventName.setText(announcements.getTitle());
        mTvDescription.setText(Html.fromHtml(announcements.getDescription()));
        mToolBarTitle.setText(announcements.getTitle());

    }
}
