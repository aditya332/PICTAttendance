package com.aditya.pictattendanceTry.data;

import android.arch.lifecycle.LiveData;

import com.aditya.pictattendanceTry.model.TotalAttendance;
import com.aditya.pictattendanceTry.model.UserWrapper;
import com.aditya.pictattendanceTry.utils.Resource;

import java.util.List;

public interface Repository {
     LiveData<List<TotalAttendance>> loadTotal();
     void loadAttendance(String username, String password);
     void cancel();
     void loadFromNetworkAndSave(String username, String password);
     void deleteAll();
    LiveData<Resource<UserWrapper>> getSubjects();

}
