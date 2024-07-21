package com.services.movie_catalog_service.models;

public class MovieModel {

    private String movieId;
    private String name;

    public MovieModel(String movieId, String name) {
        this.movieId = movieId;
        this.name = name;
    }

    public MovieModel() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }


}
