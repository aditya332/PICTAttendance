package com.aditya.pictattendanceTry.injection;

import com.aditya.pictattendanceTry.service.NotificationJobService;
import com.aditya.pictattendanceTry.ui.activities.LoginActivity.LoginActivity;
import com.aditya.pictattendanceTry.ui.activities.MainActivity;
import com.aditya.pictattendanceTry.widget.RemoteViewsFactory;
import com.aditya.pictattendanceTry.widget.WidgetListProvider;
import com.aditya.pictattendanceTry.widget.WidgetPercentProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        ViewModelModule.class,
        DBModule.class,
        NetworkModule.class,
        RepoModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
    void inject(LoginActivity loginActivity);
    void inject(NotificationJobService notificationJobService);
    void inject(RemoteViewsFactory remoteViewsFactory);
    void inject(WidgetListProvider widgetListProvider);
    void inject(WidgetPercentProvider widgetPercentProvider);
}
