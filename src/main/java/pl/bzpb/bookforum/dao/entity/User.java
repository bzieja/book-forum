package pl.bzpb.bookforum.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @JsonIgnore
    private String mail;

    private String nickname;

    @JsonIgnore
    private String password;
    private String roles;
    private boolean isActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    //@JoinColumn(nullable = true)
    private List<Rating> ratings = new ArrayList<>();

    public User() {
    }

    public User(String mail, String nickname, String password) {
        this.mail = mail;
        this.nickname = nickname;
        this.password = password;
    }

    public void setUserOnRating(Rating rating) {
        //this.ratings.add(rating);
        rating.setUser(this);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getId() {
        return mail;
    }

    public void setId(String id) {
        this.mail = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
