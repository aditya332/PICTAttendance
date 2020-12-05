package com.aditya.pictattendanceTry.data.local.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.aditya.pictattendanceTry.model.Subject;
import java.util.List;
@Dao
public interface AttendanceDao {

    @Query("SELECT * FROM Subjects")
    LiveData<List<Subject>> loadSubjects();

    @Query("SELECT * FROM Subjects")
    List<Subject> loadSubjectsSync();


    @Query("DELETE FROM Subjects")
    void deleteAll();

    @Insert
    void insertAll(List<Subject> list);

}
