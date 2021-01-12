package pl.bzpb.bookforum.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.bzpb.bookforum.dao.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, String> {
    public Optional<User> findByNickname(String nickname);

}
