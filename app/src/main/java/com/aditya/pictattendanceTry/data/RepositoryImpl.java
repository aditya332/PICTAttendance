package com.aditya.pictattendanceTry.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.aditya.pictattendanceTry.utils.PercentageHelper;
import com.aditya.pictattendanceTry.data.local.dao.AttendanceDao;
import com.aditya.pictattendanceTry.data.local.dao.PercentDao;
import com.aditya.pictattendanceTry.model.TotalAttendance;
import com.aditya.pictattendanceTry.model.UserWrapper;
import com.aditya.pictattendanceTry.utils.AppExecutors;
import com.aditya.pictattendanceTry.utils.Comparator;
import com.aditya.pictattendanceTry.utils.Constants;
import com.aditya.pictattendanceTry.utils.Resource;
import com.aditya.pictattendanceTry.data.remote.api.AttendanceApi;
import com.aditya.pictattendanceTry.data.remote.api.AuthenticateApi;
import com.aditya.pictattendanceTry.model.Subject;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.Lazy;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@Singleton
public class RepositoryImpl implements Repository{

    private final AuthenticateApi authenticateApi;
    private final AttendanceApi attendanceApi;
    private MediatorLiveData<Resource<UserWrapper>> _subjects;
    private PercentageHelper percentHelper;
    private PercentDao percentDao;
    private Lazy<AttendanceDao> attendanceDao;
    private AppExecutors executors;
    private Call<UserWrapper> attendanceCall;
    private Call<ResponseBody> authenticationCall;
int times = 0;
    public LiveData<Resource<UserWrapper>> getSubjects() {
        return _subjects;
    }



    @Inject
     public RepositoryImpl(
             PercentageHelper percentHelper,
                        PercentDao percentDao,
                        Lazy<AttendanceDao> attendanceDao,
                       AuthenticateApi authenticateApi,
                       AppExecutors executors,
                       AttendanceApi attendanceApi)
    {
        this.percentHelper = percentHelper;
        this.percentDao = percentDao;
        this.attendanceDao = attendanceDao;
        this.executors = executors;

        _subjects = new MediatorLiveData<>();
        this.authenticateApi = authenticateApi;
        this.attendanceApi = attendanceApi;
    }


    public LiveData<List<TotalAttendance>> loadTotal()
    {
        return percentDao.loadPercentages();
    }


    public void loadAttendance(String username, String password)
    {
        _subjects.setValue(Resource.loading(null));
        LiveData<List<Subject>> loadFromDb = attendanceDao.get().loadSubjects();

        _subjects.addSource(loadFromDb, list -> {

            _subjects.removeSource(loadFromDb);

            if(list==null || list.isEmpty())
            loadFromNetworkAndSave( username, password);

        else
            _subjects.setValue(Resource.loadedFromDb(new UserWrapper("", "", list, 0f, false)));


        });



    }

    public void cancel()
    {
        if(authenticationCall!=null)
            authenticationCall.cancel();
        if(attendanceCall!=null)
            attendanceCall.cancel();

    }

    public  void loadFromNetworkAndSave(
                                        String username,
                                        String password)
    {

        authenticationCall = authenticateApi.authenticate(username,
                password,
                Constants.dbConnVar,
                Constants.serviceId);

                authenticationCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call,
                                           @NonNull Response<ResponseBody> response) {

                        attendanceCall = attendanceApi.getAttendance(Constants.dashboard,
                                Constants.dbConnVar,
                                username,
                                password,
                                Constants.serviceId);
                        attendanceCall.enqueue(new Callback<UserWrapper>() {
                            @Override
                            public void onResponse(@NonNull Call<UserWrapper> call,
                                                   @NonNull Response<UserWrapper> response) {
                                if(response.body()==null)
                                    _subjects.setValue(Resource.error(Constants.ERROR,null));
                                else {
                                    executors.diskIO().execute(() -> {
                                        attendanceDao.get().deleteAll();
                                        Collections.sort(response.body().getSubjectList(), new Comparator());
                                        attendanceDao.get().insertAll(response.body().getSubjectList());
                                        TotalAttendance old = percentDao.getLastRecord();
                                        boolean hasChanged = false;
                                        if(old==null || old.getTotalAttendance()!=response.body().getPercent()) {
                                            percentDao.insert(percentHelper.getTotalAttendanceObject(response.body().getPercent()));
                                           hasChanged=true;
                                        }
                                        _subjects.postValue(Resource.success(new UserWrapper(
                                                response.body().getName(),
                                                response.body().getYear(),
                                                response.body().getSubjectList(),
                                                response.body().getPercent(), hasChanged)));
//                                        if(times==0)
//                                          _subjects.postValue(Resource.success(new UserWrapper(
//                                                response.body().getName(),
//                                                response.body().getYear(),
//                                                Constants.getFirst(),
//                                                response.body().getPercent(), hasChanged)));
//                                        else if(times==1)
//                                        {
//                                            _subjects.postValue(Resource.success(new UserWrapper(
//                                                    response.body().getName(),
//                                                    response.body().getYear(),
//                                                    Constants.getSecond(),
//                                                    response.body().getPercent(), hasChanged)));
//                                        }
//                                        else
//                                        {
//                                            _subjects.postValue(Resource.success(new UserWrapper(
//                                                    response.body().getName(),
//                                                    response.body().getYear(),
//                                                    response.body().getSubjectList(),
//                                                    response.body().getPercent(), hasChanged)));
//                                        }
//
//                                        times++;

                                    });

                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<UserWrapper> call,
                                                  @NonNull Throwable t) {

                                _subjects.setValue(Resource.error(Constants.NO_CONNECTION,null));
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call,
                                          @NonNull Throwable t) {

                        _subjects.setValue(Resource.error(Constants.NO_CONNECTION,null));
                    }
                });

    }


    public void deleteAll()
    {
        executors.diskIO().execute(() -> {attendanceDao.get().deleteAll();
        percentDao.deleteAll();
        });
    }

}
