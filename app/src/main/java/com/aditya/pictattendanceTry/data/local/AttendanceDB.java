package com.aditya.pictattendanceTry.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.aditya.pictattendanceTry.data.local.dao.AttendanceDao;
import com.aditya.pictattendanceTry.data.local.dao.PercentDao;
import com.aditya.pictattendanceTry.model.Subject;
import com.aditya.pictattendanceTry.model.TotalAttendance;

@Database(entities = {Subject.class, TotalAttendance.class},
        version = 1,
        exportSchema = false)
public abstract class AttendanceDB extends RoomDatabase {
   public abstract AttendanceDao attendanceDao();

   public abstract PercentDao percentDao();
}
