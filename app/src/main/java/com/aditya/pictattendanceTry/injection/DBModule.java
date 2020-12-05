package com.aditya.pictattendanceTry.injection;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.aditya.pictattendanceTry.data.local.AttendanceDB;
import com.aditya.pictattendanceTry.data.local.dao.AttendanceDao;
import com.aditya.pictattendanceTry.data.local.dao.PercentDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
 class DBModule {

    @Provides
    @Singleton
    AttendanceDB provideAttendanceDB(Context context)
    {
        return Room
                .databaseBuilder(context,AttendanceDB.class, "Attendance.db").fallbackToDestructiveMigration()
                .build();
    }


    @Provides
    AttendanceDao provideAttendanceDao(AttendanceDB attendanceDB)
    {
        return attendanceDB.attendanceDao();
    }

    @Provides
    PercentDao providePercentDao(AttendanceDB attendanceDB)
    {
        return attendanceDB.percentDao();
    }
}
