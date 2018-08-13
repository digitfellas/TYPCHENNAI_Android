package com.digitfellas.typchennai.preference;


import android.content.Context;


/**
 * Preferences to store/retrieve values/model as string/JsonParcels
 */
public enum Preferences {
    INSTANCE;

    // Preference Name
    public static final String PREF_NAME = "bird_pref";

    // Preference keys]
    private static final String IS_USER_LOGGED_IN = "is_user_logged_in";
    private static final String IS_MEMBER = "is_member";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String USER_MOBILE_NUMBER = "user_mobile_number";
    private static final String USER_ID = "user_id";
    private static final String HOST_ID = "host_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_ROOM_NUMBER = "user_room_number";
    private static final String USER_AUTH_TOKEN = "user_token";
    private static final String VERIFY_PROFILE_POPUP = "verify_profile_popup";

    private static final String TWILIO_ACCESS_TOKEN = "twilio_access_token";
    private static final String TWILIO_SID = "twilio_sid";
    private static final String USER_ROOM_NAME = "user_room_name";
    private static final String FCM_TOKEN = "fcm_token";


    private UserPreferences mPreferenceHandle;

    public void createPreferences(Context context) {
        mPreferenceHandle = new UserPreferences(context, PREF_NAME);
    }

    public void clearPreference() {
        if (mPreferenceHandle != null) {
            mPreferenceHandle.clearPreference();
        }
    }

    public void putUserLoggedInStatus(boolean loggedIn) {
        mPreferenceHandle.setBoolean(IS_USER_LOGGED_IN, loggedIn);
    }

    public boolean isUserLoggedIn() {
        return mPreferenceHandle.getBoolean(IS_USER_LOGGED_IN, false);
    }

    public void putMember(boolean loggedIn) {
        mPreferenceHandle.setBoolean(IS_MEMBER, loggedIn);
    }

    public boolean isMember() {
        return mPreferenceHandle.getBoolean(IS_MEMBER, false);
    }


    //---------------------------------------

    public void putAccessToken(String accesstoken) {
        mPreferenceHandle.setString(ACCESS_TOKEN, accesstoken);
    }

    public String getAccessToken() {
        return mPreferenceHandle.getString(ACCESS_TOKEN, null);
    }

    public void putUserMobileNumber(String mobileNumber) {
        mPreferenceHandle.setString(USER_MOBILE_NUMBER, mobileNumber);
    }

    public String getUserMobileNumber() {
        return mPreferenceHandle.getString(USER_MOBILE_NUMBER, null);
    }

    public void putUserId(String userId) {
        mPreferenceHandle.setString(USER_ID, userId);
    }

    public String getUserId() {
        return mPreferenceHandle.getString(USER_ID, null);
    }

    public void putHostId(String hostId) {
        mPreferenceHandle.setString(HOST_ID, hostId);
    }

    public String getHostId() {
        return mPreferenceHandle.getString(HOST_ID, null);
    }

    public String getUserName() {
        return mPreferenceHandle.getString(USER_NAME, null);
    }

    public void putUserName(String userName) {
        mPreferenceHandle.setString(USER_NAME, userName);
    }

    public String getUserRoomNumber() {
        return mPreferenceHandle.getString(USER_ROOM_NUMBER, null);
    }

    public void putUserRoomNUmber(String userRoomNumber) {
        mPreferenceHandle.setString(USER_ROOM_NUMBER, userRoomNumber);
    }

    public String getUserAuthToken() {
        return mPreferenceHandle.getString(USER_AUTH_TOKEN, null);
    }

    public void putUserAuthToken(String authToken) {
        mPreferenceHandle.setString(USER_AUTH_TOKEN, authToken);
    }

    public void putPopupDontshow(boolean dontShow) {
        mPreferenceHandle.setBoolean(VERIFY_PROFILE_POPUP, dontShow);
    }

    public boolean isDontShow() {
        return mPreferenceHandle.getBoolean(VERIFY_PROFILE_POPUP, false);
    }

    public String getUserRoomName() {
        return mPreferenceHandle.getString(USER_ROOM_NAME, null);
    }

    public void putUserRoomName(String roomName) {
        mPreferenceHandle.setString(USER_ROOM_NAME, roomName);
    }

    public String getTwilioAccessToken() {
        return mPreferenceHandle.getString(TWILIO_ACCESS_TOKEN, null);
    }

    public void putTwilioAccessToken(String accessToken) {
        mPreferenceHandle.setString(TWILIO_ACCESS_TOKEN, accessToken);
    }

    public String getTwilioSid() {
        return mPreferenceHandle.getString(TWILIO_SID, null);
    }

    public void putTwilioSid(String sid) {
        mPreferenceHandle.setString(TWILIO_SID, sid);
    }


    public String getFCMToken() {
        return mPreferenceHandle.getString(FCM_TOKEN, null);
    }

    public void putFCMToken(String fcmToken) {
        mPreferenceHandle.setString(FCM_TOKEN, fcmToken);
    }
}
