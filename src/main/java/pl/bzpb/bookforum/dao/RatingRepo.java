package pl.bzpb.bookforum.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.bzpb.bookforum.dao.entity.Rating;

@Repository
public interface RatingRepo extends CrudRepository<Rating, Long> {
}
