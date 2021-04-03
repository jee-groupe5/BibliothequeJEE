package JeeGrp5.mediatech.web.v1.dtos;

public class UserLoginDto {
    String login;
    String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
