package pl.bzpb.bookforum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.bzpb.bookforum.dao.UserRepo;
import pl.bzpb.bookforum.dao.config.MyUserDetails;
import pl.bzpb.bookforum.dao.entity.AuthenticationRequest;
import pl.bzpb.bookforum.dao.entity.User;
import pl.bzpb.bookforum.services.config.MyUserDetailsService;
import pl.bzpb.bookforum.services.exceptions.UserAlreadyRegistered;
import pl.bzpb.bookforum.util.JwtUtil;

import java.util.Optional;

@Service
public class UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private MyUserDetailsService myUserDetailsServicel;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsServicel) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsServicel = myUserDetailsServicel;
    }

    public void register(User user) throws UserAlreadyRegistered{

        Optional<User> userOptional = userRepo.findById(user.getMail());

        if (userOptional.isPresent()) {
            throw new UserAlreadyRegistered();
        }

        //user.setRoles("ROLE_USER,ROLE_ADMIN");
        user.setRoles("ROLE_USER");
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
    }

    public String login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));

        UserDetails userDetails = myUserDetailsServicel.loadUserByUsername(authenticationRequest.getUsername());
        return jwtUtil.generateToken(userDetails);
    }

}
