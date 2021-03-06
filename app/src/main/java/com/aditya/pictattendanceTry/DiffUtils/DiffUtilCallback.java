package com.aditya.pictattendanceTry.DiffUtils;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.aditya.pictattendanceTry.model.Subject;

public class DiffUtilCallback extends DiffUtil.ItemCallback<Subject> {

    @Override
    public boolean areItemsTheSame(@NonNull Subject subject, @NonNull Subject t1) {
        return   subject.getAttendance()==subject.getAttendance() &&
                subject.getSubjectname().equals(t1.getSubjectname());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Subject subject, @NonNull Subject t1) {
        return subject==t1;
    }
}
