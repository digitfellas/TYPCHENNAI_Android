package com.digitfellas.typchennai.navigation.vibhag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.digitfellas.typchennai.R;
import com.digitfellas.typchennai.RootActivity;

import java.util.ArrayList;

public class VibhagActivity extends RootActivity {

    /*
    * if changing names in below array change in vibhag detail activity switch case also
    * */
    private final String vibhagNames[] = {
            "ATDC",
            "Gyanshala",
            "Shiksha",
            "Kishore Mandal",
            "Library",
            "Mobile App & Website",
            "Sangathan",
            "Vigyapan",
            "Prakashan",
            "Consumables",
            "Media",
            "Bulletin"
    };

    private final String galleryNames[] = {
            "atdc",
            "gyanshala",
            "shiksha",
            "kishore-mandal",
            "library",
            "mobile-app-website",
            "sangathan",
            "vigyapan",
            "prakashan",
            "Consumables",
            "media",
            "bulletin"
    };

    private final String vibhagUrls[] = {
            "atdc",
            "gyanshala",
            "shiksha",
            "kishore-mandal",
            "library",
            "mobile-app-website",
            "sangathan",
            "advertisement-vigyapan",
            "prakashan",
            "consumables-distribution",
            "media",
            "jainsanskar",
    };

    private final String vibhagContactUrls[] = {
            "atdc-content",
            "gyanshala-contact",
            "shiksha-contact",
            "kishore-mandal-contact",
            "library-contact",
            "mobile-app-website-contact",
            "sanghatan-contact",
            "advertisement-vigyapan-contact",
            "prakashan-contact",
            "consumables-distribution-contact",
            "media-contact",
            "jainsanskar-contact",
    };

    private final int vibhagImages[] = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.ten,
            R.drawable.eleven,
            R.drawable.eight,
            R.drawable.nine,
            R.drawable.twelve,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.thtee,
            R.drawable.four,
            R.drawable.five,
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_vibhag);
        mToolBarTitle.setText("Vibhag");
        initViews();
        mActionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_vibhag_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<VibhagData> vibhagData = prepareData();
        VibhagListAdapter adapter = new VibhagListAdapter(getApplicationContext(),vibhagData);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<VibhagData> prepareData() {
        ArrayList<VibhagData> android_version = new ArrayList<>();
        for(int i=0;i<vibhagNames.length;i++){
            VibhagData vibhagData = new VibhagData();
            vibhagData.setName(vibhagNames[i]);
            vibhagData.setUrlContent(vibhagUrls[i]);
            vibhagData.setmGalleryName(galleryNames[i]);
            vibhagData.setUrlContact(vibhagContactUrls[i]);
            vibhagData.setImage(vibhagImages[i]);
            android_version.add(vibhagData);
        }
        return android_version;

    }
}
