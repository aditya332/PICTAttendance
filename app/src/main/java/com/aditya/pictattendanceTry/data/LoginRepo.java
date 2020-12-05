package com.aditya.pictattendanceTry.data;

import android.arch.lifecycle.LiveData;

import com.aditya.pictattendanceTry.utils.Status;

public interface LoginRepo {
     LiveData<Status> provideStatus();
     void login(String username, String password);
}
