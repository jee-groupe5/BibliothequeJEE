package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.entities.User;
import JeeGrp5.mediatech.repositories.UserRepository;
import JeeGrp5.mediatech.web.v1.dtos.UserGetDto;
import JeeGrp5.mediatech.web.v1.dtos.UserLoginDto;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;

@Controller("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns current user's information
     * @param session User's session
     * @return User's information
     */
    @RequestMapping(
            value = "/users/information",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public UserGetDto login(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return new UserGetDto(user.getFirstname(), user.getLastname(), user.getLogin(), user.getProfile());
    }

    @RequestMapping(
            value = "/users/login",
            method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public UserGetDto login(HttpSession session, @RequestBody UserLoginDto userLoginDto) {
        User user = userRepository.findByLogin(userLoginDto.getLogin());

        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong login or password");
        }

        session.setAttribute("user", user);
        return new UserGetDto(user.getFirstname(), user.getLastname(), user.getLogin(), user.getProfile());
    }

    @RequestMapping(
            value = "/new/user",
            method = RequestMethod.PUT,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public void login(@RequestBody String response) throws JSONException {

        JSONArray array = new JSONArray(response);
        JSONObject object = array.getJSONObject(0);

        User user = new User();
        user.setFirstname(object.getString("firstname"));
        user.setLastname(object.getString("lastname"));
        user.setLogin(object.getString("login"));
        user.setPassword(object.getString("password"));
        user.setProfile(object.getString("profile"));

        try {
            User verification = userRepository.findByLogin(user.getLogin());
        }
        catch (Error error){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The login is already used");
        }

        userRepository.insert(user);

    }
}
