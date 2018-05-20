package com.udacity.gradle.builditbigger.backend;

import com.udacity.gradle.jokegenerator.JokeGenerator;

/**
 * The object model for the data we are sending through endpoints
 */
public class myBean {

    private String myData;

    public String getData() {
        return JokeGenerator.getJoke();
    }

    public void setData(String data) {
        myData = data;
    }
}