package com.services.movie_info_service;

import com.services.movie_info_service.models.MovieModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    private static final Logger logger = LoggerFactory.getLogger(MovieResource.class);

    @RequestMapping("/{movieId}")
    public MovieModel getMovieInfo(@PathVariable("movieId") String movieId) {
        logger.info("========== Incoming request to getMovieInfo ==========");
        logger.info("Request received for movieId: {}", movieId);
        
        try {
            MovieModel movie = new MovieModel(movieId, "Avengers");
            logger.info("Successfully retrieved movie info - movieId: {}, movieName: {}", movieId, movie.getName());
            logger.info("========== Request completed successfully ==========");
            return movie;
        } catch (Exception e) {
            logger.error("========== ERROR: Failed to get movie info for movieId: {} - Exception: {} ==========", movieId, e.getMessage(), e);
            throw e;
        }
    }
}