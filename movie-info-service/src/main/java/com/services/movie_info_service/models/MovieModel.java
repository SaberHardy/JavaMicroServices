package com.services.movie_info_service.models;

public class MovieModel {
    public MovieModel(String movieId, String name) {
        this.movieId = movieId;
        this.name = name;
    }

    private String movieId;
    private String name;

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
