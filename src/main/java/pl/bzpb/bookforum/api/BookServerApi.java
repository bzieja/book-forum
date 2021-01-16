package pl.bzpb.bookforum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.services.BookService;

@RestController
@RequestMapping("/api/book")
public class BookServerApi {

    BookService bookService;

    @Autowired
    public BookServerApi(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    ResponseEntity<?> addBookApi(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    ResponseEntity<?> getBooks() {
    }

    @DeleteMapping
    ResponseEntity<?> deleteBook() {

    }




}
