package com.example.movieinfo.model;

/**
 * Created by camilovargas on 26/07/16.
 */
public class FailNet {

private String Error;

    public FailNet(String Error){
        this.Error =Error;
    }

    public String getFail() {
        return Error;
    }
}
