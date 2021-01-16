package pl.bzpb.bookforum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bzpb.bookforum.dao.entity.Rating;
import pl.bzpb.bookforum.services.RatingService;

@RestController
@RequestMapping("/api/book/rating")
public class RatingServiceApi {

    RatingService ratingService;

    @Autowired
    public RatingServiceApi(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{id}") //zwroc wszystkie oceny dla tej ksiazki
    ResponseEntity<?> getRatings(@PathVariable Long id) {
        //try-catch resposnse
        ratingService.getRatings(id);

    }

    @PostMapping("/{id}") //
    ResponseEntity<?> addRating(@RequestBody Rating rating, @PathVariable Long id) {
        //try-catch resposnse

    }

    @DeleteMapping("/{id}") //usuwa ocene o danym id
    ResponseEntity<?> addRating(@RequestBody Rating rating, @PathVariable Long id) {
        //try-catch resposnse

    }
}
