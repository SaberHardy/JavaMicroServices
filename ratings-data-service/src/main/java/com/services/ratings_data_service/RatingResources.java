package com.services.ratings_data_service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/ratingsData")
public class RatingResources {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1", 1),
                new Rating("2", 2),
                new Rating("3", 3)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);

        return userRating;
    }
}
