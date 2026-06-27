package com.services.ratings_data_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/ratingsData")
public class RatingResources {

    private static final Logger logger = LoggerFactory.getLogger(RatingResources.class);

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        logger.info("========== Incoming request to getRating ==========");
        logger.info("Request received for movieId: {}", movieId);
        
        try {
            Rating rating = new Rating(movieId, 4);
            logger.info("Successfully retrieved rating for movieId: {} - rating: {}", movieId, rating.getRating());
            logger.info("========== Request completed successfully ==========");
            return rating;
        } catch (Exception e) {
            logger.error("========== ERROR: Failed to get rating for movieId: {} - Exception: {} ==========", movieId, e.getMessage(), e);
            throw e;
        }
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        logger.info("========== Incoming request to getUserRatings ==========");
        logger.info("Request received for userId: {}", userId);
        
        try {
            List<Rating> ratings = Arrays.asList(
                    new Rating("1", 1),
                    new Rating("2", 2),
                    new Rating("3", 3)
            );
            
            logger.info("Created ratings list with {} items for userId: {}", ratings.size(), userId);
            logger.debug("Ratings list: {}", ratings);

            UserRating userRating = new UserRating();
            userRating.setUserRating(ratings);
            
            logger.info("Successfully retrieved {} user ratings for userId: {}", userRating.getUserRating().size(), userId);
            logger.info("========== Request completed successfully ==========");
            
            return userRating;
        } catch (Exception e) {
            logger.error("========== ERROR: Failed to get user ratings for userId: {} - Exception: {} ==========", userId, e.getMessage(), e);
            throw e;
        }
    }
}
