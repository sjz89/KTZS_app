package me.daylight.ktzs.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.daylight.ktzs.entity.AttendanceRecord;
import me.daylight.ktzs.entity.Course;
import me.daylight.ktzs.entity.Homework;
import me.daylight.ktzs.entity.Leave;
import me.daylight.ktzs.entity.Notice;
import me.daylight.ktzs.entity.SignInState;
import me.daylight.ktzs.entity.UploadFile;
import me.daylight.ktzs.entity.User;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HttpContract {
    @POST("/authority/login")
    Observable<RetResult<String>> login(@Header("imei")String imei,@Query("idNumber")String account, @Query("password")String password);

    @GET("/authority/logout")
    Observable<RetResult> logout();

    @GET("/user/getSelfInfo")
    Observable<RetResult<User>> getSelfInfo();

    @POST("/user/changeInfo")
    Observable<RetResult> changeInfo(@Body User user);

    @POST("/attendance/signIn")
    Observable<RetResult<AttendanceRecord>> signIn(@Query("uniqueId")String uniqueId,@Query("x")double x,@Query("y")double y);

    @GET("/attendance/getMyAttendance")
    Observable<RetResult<List<AttendanceRecord>>> getMyAttendance(@Query("limit")Integer limit);

    @GET("/course/getMyCourses")
    Observable<RetResult<List<Course>>> getMyCourses(@Query("semester")String semester);

    @GET("/attendance/getNow")
    Observable<RetResult<SignInState>> getAttendanceNow();

    @GET("/attendance/checkHasSignInProgress")
    Observable<RetResult> checkProgress();

    @POST("/attendance/start")
    Observable<RetResult<String>> startSignIn(@Query("id")Long courseId,@Query("limitMin")int limitMin,@Query("x")Double x,@Query("y")Double y);

    @GET("/attendance/stop")
    Observable<RetResult> stopSignIn(@Query("uniqueId")String uniqueId);

    @GET("/notice/getNoticesForMe")
    Observable<RetResult<List<Notice>>> getAllNotices();

    @POST("/user/changePwd")
    Observable<RetResult> changePwd(@Query("oldPwd")String oldPwd,@Query("password")String password);

    @GET("/attendance/getLatestRecord")
    Observable<RetResult<Map<String,String>>> getLatestRecord(@Query("courseId")Long courseId);

    @GET("/notice/getLatestNotice")
    Observable<RetResult<Notice>> getLatestNotice(@Query("courseId") Long courseId);

    @GET("/course/getStudentsByCourse")
    Observable<RetResult<List<User>>> getStudentsByCourse(@Query("courseId")Long courseId);

    @POST("/notice/push")
    Observable<RetResult> push(@Body Map<String,Object> notice);

    @GET("/user/getUserInfo")
    Observable<RetResult<User>> getUserInfo(@Query("idNumber")String idNumber);

    @GET("/user/getTeacherInfo")
    Observable<RetResult<User>> getTeacherInfo(@Query("courseId")Long courseId);

    @FormUrlEncoded
    @POST("/leave/save")
    Observable<RetResult> submitLeaveNote(@Field("startDate")Long startDate,@Field("endDate")Long endDate,@Field("reason")String reason);

    @GET("/leave/getMyLeaveRecord")
    Observable<RetResult<List<Leave>>> getMyLeaveRecord();

    @GET("/attendance/getSignInDetail")
    Observable<RetResult<List<Map<String,Object>>>> getSignInDetail();

    @GET("/attendance/getRecordOfTeacher")
    Observable<RetResult<List<Map<String,Object>>>> getRecordOfTeacher();

    @GET("/attendance/getRecordByUniqueId")
    Observable<RetResult<List<AttendanceRecord>>> getRecordByUniqueId(@Query("uniqueId")String uniqueId);

    @GET("/notice/getNoticesByCourse")
    Observable<RetResult<List<Notice>>> getNoticesByCourse(@Query("courseId")Long courseId);

    @GET("/homework/findAllOfMe")
    Observable<RetResult<List<Homework>>> findHomeworkOfMe();

    @FormUrlEncoded
    @POST("/homework/publish")
    Observable<RetResult> publishHomework(@Field("name")String name,@Field("content")String content,@Field("courseId")Long courseId,@Field("endTime")Long endTime);

    @GET("/homework/getDetail")
    Observable<RetResult<Map<String,Object>>> getHomeworkDetail(@Query("id")Long id);

    @GET("/homework/getFileList")
    Observable<RetResult<List<UploadFile>>> getHomeworkFileList(@Query("id")Long id);

    @Multipart
    @POST("/file/upload")
    Observable<RetResult> upload(@Query("homeworkId")Long homeworkId,@Part MultipartBody.Part file);

    @GET("/homework/setRandomCode")
    Observable<RetResult> setRandomCode(@Query("randomCode")String code,@Query("homeworkId")Long id);

    @FormUrlEncoded
    @POST("/authority/deviceReplace")
    Observable<RetResult> deviceReplace(@Field("imei")String imei,@Field("idNumber")String idNumber);
}
