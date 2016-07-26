package com.example.movieinfo.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.movieinfo.R;
import com.example.movieinfo.Job.MovieJob;
import com.example.movieinfo.adapter.MoviesAdapter;
import com.example.movieinfo.events.FailEvent;
import com.example.movieinfo.events.MovieEvent;
import com.example.movieinfo.model.Movie;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.path.android.jobqueue.JobManager;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = true;


    // TODO - insert your themoviedb.org API KEY here

    private RecyclerView recyclerView;


    @Inject
    JobManager jobManager;

    @Inject
    EventBus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MovieApp) getApplication()).getMoviesComponent().inject(this);
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bus.register(this);
        jobManager.addJobInBackground(new MovieJob(null));

    }

    @SuppressWarnings("UnusedDeclaration")
    @Subscribe
    public void onMovieEvent(MovieEvent event) {
        final List<Movie> movies =event.movies;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

            }
        });

    }

    @SuppressWarnings("UnusedDeclaration")
    @Subscribe
    public void onFailEvent(FailEvent event) {
        final String eError =event.eError;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Llórela :( ", Toast.LENGTH_LONG);

                toast1.show();


            }
        });

    }
}