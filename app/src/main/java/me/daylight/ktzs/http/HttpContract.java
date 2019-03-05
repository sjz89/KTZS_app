package me.daylight.ktzs.http;

import io.reactivex.Observable;
import me.daylight.ktzs.model.entity.User;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpContract {
    @POST("/authority/login")
    Observable<RetResult> login(@Query("idNumber")String account,@Query("password")String password);

    @GET("/user/getSelfInfo")
    Observable<RetResult<User>> getSelfInfo();
}
