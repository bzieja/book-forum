package pl.bzpb.bookforum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.RatingRepo;
import pl.bzpb.bookforum.dao.UserRepo;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.dao.entity.Rating;
import pl.bzpb.bookforum.dao.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RatingService {

    BookRepo bookRepo;
    RatingRepo ratingRepo;
    UserRepo userRepo;

    @Autowired
    public RatingService(BookRepo bookRepo, RatingRepo ratingRepo, UserRepo userRepo) {
        this.bookRepo = bookRepo;
        this.ratingRepo = ratingRepo;
        this.userRepo = userRepo;
    }

    public List<Rating> getRatings(Long id) throws NoSuchElementException {
        List<Rating> ratings = null;

        Optional<Book> optionalBook = bookRepo.findById(id);

        if(optionalBook.isEmpty()){
            throw new NoSuchElementException();
        }

        Book book = optionalBook.get();
        ratings = book.getRatings();
        return ratings;
    }

    public void addRating(Rating rating, Long bookId, String userId) throws Exception {
        try {
            Book book = bookRepo.findById(bookId).get();
            User user = userRepo.findById(userId).get();

            book.addRating(rating);
            user.setUserOnRating(rating);

            bookRepo.save(book);
            userRepo.save(user);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void deleteRating(Long id) throws Exception {
        try{
            Rating rating = ratingRepo.findById(id).get();

            rating.getBook().getRatings().remove(rating);
            rating.getUser().getRatings().remove(rating);

            ratingRepo.delete(rating);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
