package pl.bzpb.bookforum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.dao.entity.Rating;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void addBook(Book book) {
        Optional<Book> optionalBook = bookRepo.findById(book.getIsbn());

        if (optionalBook.isPresent()) {
            //wyrzucic wyjatek throw BookAlreadyExist
        }
        bookRepo.save(book);
    }

    public List<Book> getAllBooks () throws NoSuchElementException{
        try{
            return (List<Book>) bookRepo.findAll();
        } catch (Exception e){
            throw  new NoSuchElementException();
        }
    }

    public void deleteBook(Long id) throws Exception {
        try{
            Book book = bookRepo.findById(id).get();
            bookRepo.delete(book);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public void editBook(Book bookedit, Long id) throws Exception {
        try{
            Book book = bookRepo.findById(id).get();
            if(!bookedit.getAuthor().equals("")){
                book.setAuthor(bookedit.getAuthor());
            }
            if(!bookedit.getTitle().equals("")){
                book.setTitle(bookedit.getTitle());
            }
            bookRepo.save(book);
        } catch (Exception e){
            throw new Exception();
        }
    }
}