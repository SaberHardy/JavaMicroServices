package com.services.movie_catalog_service;

import com.services.movie_catalog_service.models.CatalogItem;
import com.services.movie_catalog_service.models.MovieModel;
import com.services.movie_catalog_service.models.Rating;
import com.services.movie_catalog_service.models.UserRating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private static final Logger logger = LoggerFactory.getLogger(MovieCatalogResource.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        long startTime = System.currentTimeMillis();
        logger.info("========== START: Incoming request to getCatalog ==========");
        logger.info("Request received for userId: {}", userId);

        try {
            // Step 1: Fetch ratings from Ratings Data Service
            logger.info("Fetching ratings from Ratings Data Service for userId: {}", userId);
            UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsData/users/" + userId, UserRating.class);
            
            if (userRating == null || userRating.getUserRating() == null) {
                logger.warn("No ratings found for userId: {}", userId);
                logger.info("========== END: Request completed (no ratings) ==========");
                return Collections.emptyList();
            }
            
            logger.info("Successfully fetched {} ratings from Ratings Data Service for userId: {}", userRating.getUserRating().size(), userId);
            logger.debug("Ratings data: {}", userRating.getUserRating());

            // Step 2: Fetch movie info for each rated movie
            logger.info("Starting to fetch movie info for {} rated movies", userRating.getUserRating().size());
            
            List<CatalogItem> catalogItems = userRating.getUserRating().stream().map(rating -> {
                try {
                    logger.info("Fetching movie info from Movie Info Service for movieId: {}", rating.getMovieId());
                    MovieModel movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), MovieModel.class);
                    
                    if (movie == null) {
                        logger.warn("Movie not found for movieId: {}", rating.getMovieId());
                        return null;
                    }
                    
                    logger.info("Successfully fetched movie info for movieId: {} - movieName: {}", rating.getMovieId(), movie.getName());
                    return new CatalogItem(movie.getName(), "Small description", rating.getRating());
                } catch (Exception e) {
                    logger.error("ERROR: Failed to fetch movie info for movieId: {} - Exception: {}", rating.getMovieId(), e.getMessage(), e);
                    return null;
                }
            }).filter(item -> item != null).collect(Collectors.toList());

            long endTime = System.currentTimeMillis();
            logger.info("Successfully aggregated catalog with {} items for userId: {} in {}ms", catalogItems.size(), userId, (endTime - startTime));
            logger.info("========== END: Request completed successfully ==========");
            
            return catalogItems;
            
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("========== ERROR: Failed to get catalog for userId: {} - Exception: {} ==========", userId, e.getMessage(), e);
            logger.error("Request failed after {}ms", (endTime - startTime));
            throw e;
        }
    }
}
