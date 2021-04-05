package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.web.v1.dtos.ImageUploadDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String addedImageId;

    @Test
    public void shouldDownscaleImage() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                getClass().getClassLoader().getResource("test_car.jfif").openStream()
        );

        ImageUploadDto imageUploadDto = objectMapper.
                readValue(mockMvc.perform(multipart("/images").file(mockMultipartFile))
                .andDo(print())
                .andReturn().getResponse().getContentAsByteArray(), ImageUploadDto.class);
        addedImageId = imageUploadDto.getId();

        mockMvc.perform(get("/images/" + addedImageId + "?format=DVD"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }
}