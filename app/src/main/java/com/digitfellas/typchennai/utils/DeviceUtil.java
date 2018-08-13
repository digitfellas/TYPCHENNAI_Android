package com.digitfellas.typchennai.utils;

import android.content.Context;
import android.provider.Settings;

import java.util.UUID;

public class DeviceUtil {

    public static String getDeviceUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getDeviceModel() {

        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;
        return (deviceMan + " " + deviceName);
    }

    public static String getSecureUDID(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

}
