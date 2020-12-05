package com.aditya.pictattendanceTry;

import android.app.Application;

import com.aditya.pictattendanceTry.injection.AppComponent;
import com.aditya.pictattendanceTry.injection.AppModule;
import com.aditya.pictattendanceTry.injection.DaggerAppComponent;
import com.aditya.pictattendanceTry.model.UserWrapper;

public class PICTAttendance extends Application{



    private AppComponent component;
    private UserWrapper subjectList;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

    }

    public void submitUser(UserWrapper userWrapper)
    {
        this.subjectList =userWrapper ;
    }

    public UserWrapper getUser()
    {
        return subjectList;
    }


    public AppComponent getComponent() {
        return component;
    }
}
