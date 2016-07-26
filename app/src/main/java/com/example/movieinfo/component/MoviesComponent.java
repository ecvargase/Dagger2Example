package com.example.movieinfo.component;


import com.example.movieinfo.Job.MovieJob;
import com.example.movieinfo.scopes.UserScope;
import com.example.movieinfo.activity.MainActivity;
import com.example.movieinfo.modules.MoviesModule;

import dagger.Component;


@UserScope
@Component(dependencies = NetComponent.class, modules = MoviesModule.class)
public interface MoviesComponent {
    void inject(MainActivity activity);
    void inject(MovieJob job);
}
