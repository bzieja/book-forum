package pl.bzpb.bookforum.dao.entity.requests;

public class RegistrationRequest {
    String mail;
    String nickname;
    String password;

    public RegistrationRequest(String mail, String nickname, String password) {
        this.mail = mail;
        this.nickname = nickname;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
