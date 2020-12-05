package com.aditya.pictattendanceTry.data.remote.api;

import com.aditya.pictattendanceTry.model.UserWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AttendanceApi {
    @GET("StudentsPersonalFolder_pict.jsp")
    Call<UserWrapper> getAttendance(@Query("dashboard") int dashboard,
                                    @Query("dbConnVar") String dbConnVar,
                                    @Query("loginid") String loginId,
                                    @Query("password") String password,
                                    @Query("service_id") String serviceId);

}
