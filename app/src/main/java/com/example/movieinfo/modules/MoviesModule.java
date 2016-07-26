package com.example.movieinfo.modules;


import com.example.movieinfo.scopes.UserScope;
import com.example.movieinfo.network.interfaces.MoviesInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MoviesModule {

    @Provides
    @UserScope
    public MoviesInterface providesMoviesInterface(Retrofit retrofit) {
        return retrofit.create(MoviesInterface.class);
    }
}
