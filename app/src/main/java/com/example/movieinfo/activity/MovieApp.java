package com.example.movieinfo.activity;

import android.app.Application;

import com.example.movieinfo.component.DaggerMoviesComponent;
import com.example.movieinfo.component.DaggerNetComponent;
import com.example.movieinfo.component.MoviesComponent;
import com.example.movieinfo.component.NetComponent;
import com.example.movieinfo.modules.AppModule;
import com.example.movieinfo.modules.MoviesModule;
import com.example.movieinfo.modules.NetModule;

public class MovieApp extends Application {

    private NetComponent mNetComponent;

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private MoviesComponent mMoviesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // specify the full namespace of the component
        // Dagger_xxxx (where xxxx = component name)
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .build();


        mMoviesComponent = DaggerMoviesComponent.builder()
                .netComponent(mNetComponent)
                .moviesModule(new MoviesModule())
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public MoviesComponent getMoviesComponent() {
        return mMoviesComponent;
    }

}