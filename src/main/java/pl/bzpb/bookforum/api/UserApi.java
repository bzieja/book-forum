package pl.bzpb.bookforum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bzpb.bookforum.dao.entity.AuthenticationRequest;
import pl.bzpb.bookforum.dao.entity.Book;
import pl.bzpb.bookforum.dao.entity.Rating;
import pl.bzpb.bookforum.dao.entity.User;
import pl.bzpb.bookforum.services.UserService;
import pl.bzpb.bookforum.services.exceptions.UserAlreadyRegistered;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {

        try {
            userService.register(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserAlreadyRegistered e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {

        Cookie cookie = new Cookie("authorization", userService.login(authenticationRequest));
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ratings/{nickname}")
    ResponseEntity<List<Rating>> getBooks(@PathVariable String nickname) {
        try{
            List <Rating> ratings = userService.getUserRatings(nickname);
            return new ResponseEntity<>(ratings, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
