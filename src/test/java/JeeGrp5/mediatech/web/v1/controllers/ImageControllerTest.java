package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.web.v1.dtos.UserGetDto;
import JeeGrp5.mediatech.web.v1.dtos.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired
    private static MockMvc mockMvc;
    private static Cookie[] cookies;

    @BeforeAll
    public static void login() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserLoginDto userCredentials = new UserLoginDto("oualid.hassan.pro@gmail.com", "123456");
        cookies = mockMvc.perform(
                post("/users/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userCredentials)))
                .andReturn().getResponse().getCookies();
    }

    @Test
    public void shouldCreateImage() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserGetDto userGetDto = new UserGetDto(
                "Oualid",
                "Hassan",
                "oualid.hassan.pro@gmail.com",
                "Gestionnaire"
        );

        mockMvc.perform(
                get("/users/information")
                        .cookie(cookies))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userGetDto)));
    }
}