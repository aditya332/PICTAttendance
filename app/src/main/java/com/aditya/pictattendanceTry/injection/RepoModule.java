package com.aditya.pictattendanceTry.injection;


import com.aditya.pictattendanceTry.data.LoginRepo;
import com.aditya.pictattendanceTry.data.LoginRepoImpl;
import com.aditya.pictattendanceTry.data.Repository;
import com.aditya.pictattendanceTry.data.RepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepoModule {
    @Binds
    abstract Repository provideRepository(RepositoryImpl repository);

    @Binds
    abstract LoginRepo provideLoginRepo(LoginRepoImpl loginRepo);
}
