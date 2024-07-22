package com.services.movie_catalog_service;

import com.services.movie_catalog_service.models.CatalogItem;
import com.services.movie_catalog_service.models.MovieModel;
import com.services.movie_catalog_service.models.Rating;
import com.services.movie_catalog_service.models.UserRating;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        // RestTemplate restTemplate = new RestTemplate();
        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);


        // use web client
        // get all rated movies ids
//        List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new Rating("5678", 3));

        return userRating.getUserRating().stream().map(rating -> {
            // for each movie id, get details from movie info service
             MovieModel movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), MovieModel.class);

            // This will give use an instance of MovieModel
            // MovieModel movie = webClientBuilder.build()
            // .get()
            //        .uri("http://localhost:8082/movies/" + rating.getMovieId())
            //.retrieve()
            //// This line means whatever the response is, it will be converted to MovieModel class,
            // And mono is a type of response, so it will be converted to Mono<MovieModel>
            //.bodyToMono(MovieModel.class)
            // This will wait for the response to be received, and this block() will wait until it is received
            // .block();

            // put them all in a list together
            return new CatalogItem(movie.getName(), "Small description", rating.getRating());
        }).collect(Collectors.toList());



    }
}
