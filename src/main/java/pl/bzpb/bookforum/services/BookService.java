package pl.bzpb.bookforum.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.dao.entity.Rating;
import pl.bzpb.bookforum.services.exceptions.BookAlreadyExist;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void addBook(Book book) throws BookAlreadyExist {
        Optional<Book> optionalBook = bookRepo.findById(book.getIsbn());

        if (optionalBook.isPresent()) {
            log.info("The book is already in database!");
            throw new BookAlreadyExist();
        }
        bookRepo.save(book);
        log.info("Book: {} has been added", book.getIsbn());
    }

    public List<Book> getAllBooks () {
        log.info("List of books has been returned");
        return (List<Book>) bookRepo.findAll();
    }

    public void deleteBook(Long id) throws Exception {
        Optional<Book> optionalBook = bookRepo.findById(id);

        if(optionalBook.isEmpty()){
            log.info("There is no such book!");
            throw new NoSuchElementException();
        }

        Book book = optionalBook.get();
        bookRepo.delete(book);
        log.info("Book: {} has been deleted", book.getIsbn());

    }

    public void editBook(Book bookToUpdate, Long id) throws NoSuchElementException {
        Optional<Book> optionalBook = bookRepo.findById(id);

        if(optionalBook.isEmpty()){
            log.info("There is no such book!");
            throw new NoSuchElementException();
        }

        Book book = optionalBook.get();

        if(!bookToUpdate.getAuthor().equals("")){
            book.setAuthor(bookToUpdate.getAuthor());
        }
        if(!bookToUpdate.getTitle().equals("")){
            book.setTitle(bookToUpdate.getTitle());
        }
        bookRepo.save(book);
        log.info("Book: {} has been changed", book.getIsbn());
    }
}