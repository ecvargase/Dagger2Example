package com.example.movieinfo.Job;

import android.util.Log;

import com.example.movieinfo.component.MoviesComponent;
import com.example.movieinfo.events.FailEvent;
import com.example.movieinfo.events.MovieEvent;
import com.example.movieinfo.model.MovieResponse;
import com.example.movieinfo.network.interfaces.MoviesInterface;
import com.google.common.eventbus.EventBus;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.io.IOException;
import java.util.Arrays;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by camilovargas on 25/07/16.
 */
public class MovieJob extends Job {
    private final static String API_KEY = "b2aa362e6e9162456b3f51cb7921e93f";
    private final String TAG= "Inicio";

    @Inject
    EventBus bus;

    @Inject
    MoviesInterface moviesInterface;

    public MovieJob(Params params) {
        super(new Params(100));
    }

    @Override
    public void onAdded() {
        Log.d(TAG, "OnAdded");
    }

    @Override
    public void onRun()  {

        Call<MovieResponse> call = moviesInterface.getTopRatedMovies(API_KEY);
        Response<MovieResponse> response = null;
        try {
            response = call.execute();
            if(response.isSuccessful()){
                bus.post(new MovieEvent(response.body().getResults()));
            }else{

            }
        } catch (IOException e) {
            bus.post(new FailEvent("Error"));
            Log.e(TAG, e.getMessage()+ "\n"+ Arrays.toString(e.getStackTrace()));
        }



    }

    @Override
    protected void onCancel() {
        Log.d(TAG, "OnCancel");

    }

    public void inject(MoviesComponent moviesComponent) {
        moviesComponent.inject(this);

    }
}
