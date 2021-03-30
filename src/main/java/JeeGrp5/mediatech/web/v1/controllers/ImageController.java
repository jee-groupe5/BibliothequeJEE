package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.entities.Image;
import JeeGrp5.mediatech.repositories.ImageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller("/images")
public class ImageController {
    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Retrieve 50 images starting from the specified page
     *
     * @return A list of image
     */
    @GetMapping(value = "/{page}")
    @ResponseBody
    public List<Image> get(@PathVariable int page) {
        return imageRepository.findAll(PageRequest.of(page, 50)).getContent();
    }

    /**
     * First step of uploading an image, it checks the validity of image (format, size, etc...)
     *
     * @param file The image to validate
     * @return
     */
    @PostMapping(value = "/images")
    @ResponseBody
    public List<Image> getAll(@RequestParam("file") MultipartFile file) {
        return imageRepository.findAll(PageRequest.of(1, 50)).getContent();
    }

    @GetMapping()
    @ResponseBody
    public void getVersion() {
    }
}
