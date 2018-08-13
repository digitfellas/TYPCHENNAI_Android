package com.digitfellas.typchennai.common.customtextview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.digitfellas.typchennai.TypApplication;


public class LatoBoldTextview extends AppCompatTextView {


    public LatoBoldTextview(Context context) {
        super(context);
        init();

    }


    public LatoBoldTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoBoldTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(new TypApplication().lato_Bold);//Set Typeface from MyApplication
    }

}
