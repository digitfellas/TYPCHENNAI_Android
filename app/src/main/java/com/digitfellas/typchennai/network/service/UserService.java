package com.digitfellas.typchennai.network.service;

import android.support.annotation.CallSuper;

import com.digitfellas.typchennai.network.response.AboutUsResponse;
import com.digitfellas.typchennai.network.response.AnnouncementResponse;
import com.digitfellas.typchennai.network.response.DownloadResponse;
import com.digitfellas.typchennai.network.response.EventListResponse;
import com.digitfellas.typchennai.network.response.FamilyDetailResponse;
import com.digitfellas.typchennai.network.response.ForgotPasswordResponse;
import com.digitfellas.typchennai.network.response.GetDirectoryResponse;
import com.digitfellas.typchennai.network.response.GetGalleryDetailResponse;
import com.digitfellas.typchennai.network.response.GetGalleryResponse;
import com.digitfellas.typchennai.network.response.GyanshalasResponse;
import com.digitfellas.typchennai.network.response.LoginResponse;
import com.digitfellas.typchennai.network.response.RegistrationResponse;
import com.digitfellas.typchennai.network.response.SendDeviceTokenResponse;
import com.digitfellas.typchennai.network.response.VideoResponse;
import com.digitfellas.typchennai.network.response.WorkingCommitteeResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface UserService {

    @GET("page/about")
    Call<AboutUsResponse> aboutUs();

    @GET("page/history")
    Call<AboutUsResponse> history();

    @GET("committees")
    Call<WorkingCommitteeResponse> getWorkingCommittee(@Query("app_key") String key);

    @GET("events/upcoming")
    Call<EventListResponse> getEvents(@Query("app_key") String key);

    @GET("announcements")
    Call<AnnouncementResponse> getAnnouncements(@Query("app_key") String key);

    @GET("event/{id}")
    Call<EventListResponse> getEventDetail(@Path("id") String id, @Query("app_key") String key);

    @GET("family")
    Call<GetDirectoryResponse> getDirectoryList(@Query("app_key") String key);


    @GET("family/{id}")
    Call<FamilyDetailResponse> getFamilyDetails(@Path("id") String id, @Query("app_key") String key);

    @GET("members/search")
    Call<GetDirectoryResponse> getSearchName(@Query("name") String name, @Query("surname") String surname, @Query("native") String native_loc, @Query("state") String state,
                                             @Query("city") String city, @Query("pincode") String pincode, @Query("mobile_no") String mobile_no, @Query("gender") String gender,
                                             @Query("marital_status") String maritalStatus, @Query("app_key") String appkey);

    @GET("members/search")
    Call<GetDirectoryResponse> advancedSearch(@QueryMap Map<String, String> options);

    @GET("gallery")
    Call<GetGalleryResponse> getGallery(@Query("app_key") String key);

    @GET("gallery/{id}")
    Call<GetGalleryDetailResponse> getAlbum(@Path("id") String id, @Query("app_key") String key);

    @GET("downloads/pdf")
    Call<DownloadResponse> getPdf(@Query("app_key") String key);

    @GET("signup")
    Call<RegistrationResponse> signup(@Query("mobile") String mobile, @Query("pass") String password, @Query("conpass") String confirmPass);

    @GET("otp/auth/clone")
    Call<RegistrationResponse> verifyOTP(@Query("member_id") String member_id, @Query("otp") String otp);

    @GET("loginauth")
    Call<LoginResponse> login(@Query("mobile") String mobile, @Query("pass") String password);

    @GET("page/{type}")
    Call<AboutUsResponse> getVibhagContactResponse(@Path("type") String type);

    @GET("gyanshalas")
    Call<GyanshalasResponse> getGyanshalas();

    @GET("fcminfo_insert")
    Call<SendDeviceTokenResponse> sendDeviceToken(@Query("device_id") String deviceId, @Query("push_token_id") String pushToken, @Query("device_model") String device_model, @Query("user_id") String userId, @Query("member_type") String member_type, @Query("app_key") String key);

    @GET("videos")
    Call<VideoResponse> getVideoResponse(@Query("app_key") String key);

    @GET("forgot/mobile")
    Call<ForgotPasswordResponse> forgotPassword(@Query("mobile") String mobile);

    @GET("forgot/otp")
    Call<ForgotPasswordResponse> OTPverify(@Query("otp") String mobile, @Query("member_id") String memberid, @Query("member_non") String member);

    @GET("forgot/pass")
    Call<ForgotPasswordResponse> changePassword(@Query("pass") String password, @Query("conpass") String cnfpassword, @Query("member_id") String memberid, @Query("member_non") String member);

}

