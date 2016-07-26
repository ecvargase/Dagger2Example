package com.example.movieinfo.component;


import android.content.SharedPreferences;

import com.example.movieinfo.modules.AppModule;
import com.example.movieinfo.modules.NetModule;
import com.google.common.eventbus.EventBus;
import com.path.android.jobqueue.JobManager;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
     //downstream components need these exposed
    Retrofit retrofit();
    EventBus eventBus();
    JobManager jobManager();
    OkHttpClient okHttpClient();
    SharedPreferences sharedPreferences();
}