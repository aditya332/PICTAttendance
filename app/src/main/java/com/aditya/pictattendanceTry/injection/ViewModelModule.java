package com.aditya.pictattendanceTry.injection;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.aditya.pictattendanceTry.ui.activities.LoginActivity.LoginViewModel;
import com.aditya.pictattendanceTry.ui.activities.MainViewModel;
import com.aditya.pictattendanceTry.ui.activities.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel provideMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel provideLoginViewModel(LoginViewModel mainViewModel);


    @Binds
    abstract ViewModelProvider.Factory provideFactory(ViewModelFactory factory);

}
