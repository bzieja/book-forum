package pl.bzpb.bookforum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.dao.entity.Rating;
import pl.bzpb.bookforum.services.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/book/rating")
public class RatingServiceApi {

    RatingService ratingService;

    @Autowired
    public RatingServiceApi(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{id}") //zwroc wszystkie oceny dla tej ksiazki
    ResponseEntity<List<Rating>> getRatings(@PathVariable Long id) {
        try{
            List <Rating> ratings = ratingService.getRatings(id);
            return new ResponseEntity<>(ratings, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}") //dodaj ocene
    ResponseEntity<?> addRating(@RequestBody Rating rating, @PathVariable Long id) {
        try{
            //zakodowane na usera akowalski@gmail.com
            ratingService.addRating(rating, id, "akowalski@gmail.com");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}") //ADMIN ONLY!
    ResponseEntity<?> deleteRating(@PathVariable Long id) {
        try{
            ratingService.deleteRating(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
