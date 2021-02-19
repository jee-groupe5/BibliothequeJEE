package JeeGrp5.mediatech.web.v1.dtos;

public class UserGetDto {
    private String firstname;
    private String lastname;
    private String login;
    private String profile;

    public UserGetDto() {
    }

    public UserGetDto(String firstname, String lastname, String login, String profile) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.profile = profile;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
