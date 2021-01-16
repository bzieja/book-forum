package pl.bzpb.bookforum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.entity.Book;

import java.util.Optional;

@Service
public class BookService {

    BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void addBook(Book book) {    //sygnatura

        Optional<Book> optionalBook = bookRepo.findById(book.getId());

        if (optionalBook.isPresent()) {
            //wyrzucic wyjatek throw BookAlreadyExist
        }

        bookRepo.save(book);
    }
}
