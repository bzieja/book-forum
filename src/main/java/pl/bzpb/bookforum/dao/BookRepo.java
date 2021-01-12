package pl.bzpb.bookforum.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.bzpb.bookforum.dao.entity.Book;

@Repository
public interface BookRepo extends CrudRepository<Book, Long> {
}
