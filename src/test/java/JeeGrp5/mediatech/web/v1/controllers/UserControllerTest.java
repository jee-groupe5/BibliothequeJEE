package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.web.v1.dtos.UserGetDto;
import JeeGrp5.mediatech.web.v1.dtos.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldLogin() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserGetDto userGetDto = new UserGetDto(
                "Oualid",
                "Hassan",
                "oualid.hassan.pro@gmail.com",
                "Gestionnaire"
        );

        UserLoginDto userCredentials = new UserLoginDto("oualid.hassan.pro@gmail.com", "123456");
        this.mockMvc.perform(post("/users/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userCredentials)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userGetDto)));
    }

    @Test
    public void shouldStayLoggedIn() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserGetDto userGetDto = new UserGetDto(
                "Oualid",
                "Hassan",
                "oualid.hassan.pro@gmail.com",
                "Gestionnaire"
        );

        UserLoginDto userCredentials = new UserLoginDto("oualid.hassan.pro@gmail.com", "123456");
        this.mockMvc.perform(post("/users/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userCredentials)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userGetDto)));

        this.mockMvc.perform(get("/users/information"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userGetDto)));
    }

    @Test
    public void shouldNotLoginWithBadCredentials() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserLoginDto userCredentials = new UserLoginDto("oualid.hassan.pro@gmail.com", "123");
        this.mockMvc.perform(post("/users/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userCredentials)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}