package pl.bzpb.bookforum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.RatingRepo;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.dao.entity.Rating;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RatingService {

    BookRepo bookRepo;
    RatingRepo ratingRepo;

    @Autowired
    public RatingService(BookRepo bookRepo, RatingRepo ratingRepo) {
        this.bookRepo = bookRepo;
        this.ratingRepo = ratingRepo;
    }

    public List<Rating> getRatings(Long id) throws NoSuchElementException {
        Iterable<Book> books = bookRepo.findAllById(Collections.singleton(id));
        List<Rating> ratings = null;

        for (Book book : books) {
            ratings.add((Rating) book.getRatings());
        }

        if (ratings == null) {
            throw new NoSuchElementException();
        }

        return ratings;
    }
}
