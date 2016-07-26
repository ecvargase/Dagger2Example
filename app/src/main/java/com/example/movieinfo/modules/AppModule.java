package com.example.movieinfo.modules;

import android.app.Application;
import android.util.Log;

import com.example.movieinfo.Job.MovieJob;
import com.example.movieinfo.activity.MainActivity;
import com.example.movieinfo.activity.MovieApp;
import com.google.common.eventbus.EventBus;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.path.android.jobqueue.log.CustomLogger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    Application mApplication;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = true;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public EventBus eventBus() {
        return new EventBus();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public JobManager jobManager(){
        if(DEBUG) Log.i(TAG, "singleton jobManager");
        Configuration configuration = new Configuration.Builder(mApplication.getApplicationContext())
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        if(DEBUG) Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        if(DEBUG) Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        if(DEBUG) Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(0)        // kill all consumers when no job available
                .maxConsumerCount(3)        // no more than 3 consumers running at the same time
                .loadFactor(3)              // each consumer processes 3 jobs
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(Job job) {
                        if(job instanceof MovieJob){
                            ((MovieJob) job).inject(((MovieApp)mApplication).getMoviesComponent());
                        }
                    }
                })
                .consumerKeepAlive(60)
                .build();

        JobManager jobManager = new JobManager(mApplication.getApplicationContext(), configuration);

        return jobManager;
    }


}
