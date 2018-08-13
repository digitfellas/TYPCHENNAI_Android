package com.digitfellas.typchennai.common.customtextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.digitfellas.typchennai.TypApplication;

public class LatoRegularTextview extends AppCompatTextView {

    public LatoRegularTextview(Context context) {
        super(context);
        init();
    }

    public LatoRegularTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public LatoRegularTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
       setTypeface(new TypApplication().lato_Regular);//Set Typeface from MyApplication
    }
}
