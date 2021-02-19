package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.entities.User;
import JeeGrp5.mediatech.repositories.UserRepository;
import JeeGrp5.mediatech.web.v1.dtos.UserGetDto;
import JeeGrp5.mediatech.web.v1.dtos.UserLoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Controller("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
