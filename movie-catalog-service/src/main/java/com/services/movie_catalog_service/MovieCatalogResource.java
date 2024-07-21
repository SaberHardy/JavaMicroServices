package com.services.movie_catalog_service;

import com.services.movie_catalog_service.models.CatalogItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        // get all rated movies ids

        // for each movie id, get details from movie info service

        // put them all in a list together
        return Collections.singletonList(new CatalogItem("Transformers", "Action", 5));
    }
}
