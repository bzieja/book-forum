package pl.bzpb.bookforum.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bzpb.bookforum.dao.BookRepo;
import pl.bzpb.bookforum.dao.UserRepo;
import pl.bzpb.bookforum.dao.entity.requests.AuthenticationRequest;
import pl.bzpb.bookforum.dao.entity.Rating;
import pl.bzpb.bookforum.dao.entity.User;
import pl.bzpb.bookforum.dao.entity.requests.RegistrationRequest;
import pl.bzpb.bookforum.services.config.MyUserDetailsService;
import pl.bzpb.bookforum.services.exceptions.UserAlreadyRegistered;
import pl.bzpb.bookforum.util.JwtUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private UserRepo userRepo;
    private BookRepo bookRepo;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private MyUserDetailsService myUserDetailsService;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, BookRepo bookRepo) {
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
    }

    public void register(RegistrationRequest registrationRequest) throws UserAlreadyRegistered{

        if (StreamSupport.stream(userRepo.findAll().spliterator(), false).
                anyMatch(user -> registrationRequest.getMail().equals(user.getMail()))) {
            log.info("User with this mail is already registered!");
            throw new UserAlreadyRegistered();
        } else if (StreamSupport.stream(userRepo.findAll().spliterator(), false).
                anyMatch(user -> registrationRequest.getNickname().equals(user.getNickname()))) {
            log.info("User with this nickname is already registered!");
            throw new UserAlreadyRegistered();
        }
        User user = new User();
        //user.setRoles("ROLE_USER,ROLE_ADMIN");
        user.setMail(registrationRequest.getMail());
        user.setNickname(registrationRequest.getNickname());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRoles("ROLE_USER");
        user.setActive(true);
        log.info("New user: {} (nickname) has been registered!", user.getNickname());
        userRepo.save(user);
    }

    public String login(AuthenticationRequest authenticationRequest) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getNickname(),
                authenticationRequest.getPassword()
        ));


        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getNickname());

        log.info("User with nickname: {} has been logged in!", userDetails.getUsername());
        return jwtUtil.generateToken(userDetails);
    }

    public List<Rating> getUserRatings(String userNickname) throws NoSuchElementException {
        List<Rating> ratings = null;

        User optionalUser = StreamSupport.stream(userRepo.findAll().spliterator(), false).
                filter(user -> userNickname.equals(user.getNickname())).findAny().orElse(null);

        if (optionalUser == null) {
            log.info("There is no user with nickname: {} in database", userNickname);
            throw new NoSuchElementException();
        }

        User user = userRepo.findById(optionalUser.getId()).get();

        ratings = user.getRatings();
        log.info("Ratings of user: {} has been returned!", user.getNickname());
        return ratings;
    }

}
