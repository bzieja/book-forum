package pl.bzpb.bookforum.services;

import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.RatingRepo;
import pl.bzpb.bookforum.dao.UserRepo;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.dao.entity.Rating;
import pl.bzpb.bookforum.dao.entity.User;
import pl.bzpb.bookforum.services.exceptions.UserAlreadyRegistered;
import pl.bzpb.bookforum.util.JwtUtil;

import javax.servlet.http.Cookie;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class RatingService {

    BookRepo bookRepo;
    RatingRepo ratingRepo;
    UserRepo userRepo;
    JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(RatingService.class);

    @Autowired
    public RatingService(BookRepo bookRepo, RatingRepo ratingRepo, UserRepo userRepo, JwtUtil jwtUtil) {
        this.bookRepo = bookRepo;
        this.ratingRepo = ratingRepo;
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    public List<Rating> getRatings(Long id) throws NoSuchElementException {
        List<Rating> ratings = null;

        Optional<Book> optionalBook = bookRepo.findById(id);

        if(optionalBook.isEmpty()){
            log.info("There is no such book!");
            throw new NoSuchElementException();
        }

        Book book = optionalBook.get();
        ratings = book.getRatings();
        log.info("Ratings for book: {} has been returned!", book.getIsbn());
        return ratings;
    }

    public void addRating(Rating rating, Long bookId, Cookie[] cookies) throws NoSuchElementException {

        Optional<Book> optionalBook = bookRepo.findById(bookId);

        if (optionalBook.isEmpty()) {
            log.info("There is no such book!");
            throw new NoSuchElementException();
        }
        Book book = optionalBook.get();

        Cookie authorizationCookie = Arrays.stream(cookies).
                filter(cookie -> "authorization".equals(cookie.getName())).findAny().orElse(null);

        String userNickname = jwtUtil.extractUserName(authorizationCookie.getValue());

        String userId = StreamSupport.stream(userRepo.findAll().spliterator(), false).
                filter(user -> userNickname.equals(user.getNickname())).findAny().orElse(null).getId();

        User user = userRepo.findById(userId).get();

        book.addRating(rating);
        user.setUserOnRating(rating);

        log.info("Rating for book: {} has been added!", book.getIsbn());
        bookRepo.save(book);
        userRepo.save(user);
    }

    public void deleteRating(Long id) throws NoSuchElementException {

        Optional<Rating> optionalRating = ratingRepo.findById(id);

        if (optionalRating.isEmpty()) {
            log.info("There is no such rating!");
            throw new NoSuchElementException();
        }

        Rating rating = optionalRating.get();
        rating.getBook().getRatings().remove(rating);
        rating.getUser().getRatings().remove(rating);

        ratingRepo.delete(rating);
        log.info("Ratings for book: {} has been added!", rating.getBook().getIsbn());
    }

}
