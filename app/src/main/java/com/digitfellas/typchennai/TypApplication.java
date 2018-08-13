package com.digitfellas.typchennai;

import android.app.Application;
import android.graphics.Typeface;

import com.digitfellas.typchennai.preference.Preferences;
import com.google.firebase.FirebaseApp;

/**
 * Created by administrator on 14/04/18.
 */

public class TypApplication extends Application {

    private static TypApplication sThis;
    private static String TAG = TypApplication.class.getSimpleName();
    public static Typeface lato_Regular, lato_Bold;

    @Override
    public void onCreate() {
        super.onCreate();
        sThis = this;
        Preferences.INSTANCE.createPreferences(sThis);
        lato_Regular = Typeface.createFromAsset(getAssets(), "font/Lato-Regular.ttf");
        lato_Bold = Typeface.createFromAsset(getAssets(), "font/Lato-Bold.ttf");

    }

    public static TypApplication getThis() {
        return sThis;
    }
}
