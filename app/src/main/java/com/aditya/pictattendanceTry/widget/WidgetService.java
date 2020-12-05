package com.aditya.pictattendanceTry.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new com.aditya.pictattendanceTry.widget.RemoteViewsFactory(getApplicationContext());
    }
}
