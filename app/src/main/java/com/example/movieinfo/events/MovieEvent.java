package com.example.movieinfo.events;

import com.example.movieinfo.model.Movie;

import java.util.List;

/**
 * Created by camilovargas on 25/07/16.
 */
public class MovieEvent {

    public List<Movie> movies;

    public MovieEvent(List<Movie> movies){
        this.movies=movies;
    }

}
