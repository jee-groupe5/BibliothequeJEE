package JeeGrp5.mediatech.web.v1.dtos;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGetDto)) return false;
        UserGetDto that = (UserGetDto) o;
        return firstname.equals(that.firstname) && lastname.equals(that.lastname) && login.equals(that.login) && profile.equals(that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, login, profile);
    }
}
