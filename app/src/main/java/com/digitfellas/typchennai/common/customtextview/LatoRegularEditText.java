package com.digitfellas.typchennai.common.customtextview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.digitfellas.typchennai.TypApplication;


public class LatoRegularEditText extends AppCompatEditText {

    public LatoRegularEditText(Context context) {
        super(context);
        init();
    }


    public LatoRegularEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public LatoRegularEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setTypeface(new TypApplication().lato_Regular);//Set Typeface from MyApplication
    }
}
