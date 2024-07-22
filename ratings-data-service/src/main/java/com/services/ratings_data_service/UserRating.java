package com.services.ratings_data_service;

import java.util.List;

public class UserRating {
    public List<Rating> userRating;

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
