package com.services.movie_info_service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @RequestMapping("/{movieId}")
    public MovieModel getMovieInfo(@PathVariable("movieId") String movieId) {
        return new MovieModel(movieId, "Avengers");
    }
}