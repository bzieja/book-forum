package pl.bzpb.bookforum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bzpb.bookforum.dao.entity.AuthenticationRequest;
import pl.bzpb.bookforum.dao.entity.User;
import pl.bzpb.bookforum.services.UserService;
import pl.bzpb.bookforum.services.exceptions.UserAlreadyRegistered;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserServiceApi {

    UserService userService;

    @Autowired
    public UserServiceApi(UserService userService) {
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

        return new ResponseEntity<>(userService.login(authenticationRequest), HttpStatus.OK);
    }

    @GetMapping("/test1")
    public ResponseEntity<String> test1() {
        return new ResponseEntity<>("test1", HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        return new ResponseEntity<>("test2", HttpStatus.OK);
    }


}
