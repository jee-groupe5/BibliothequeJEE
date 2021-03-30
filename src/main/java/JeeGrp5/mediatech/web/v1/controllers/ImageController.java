package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.entities.Image;
import JeeGrp5.mediatech.repositories.ImageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller("/images")
public class ImageController {
    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping(value = "/images")
    @ResponseBody
    public List<Image> get() {
        return imageRepository.findAll(PageRequest.of(1, 50)).getContent();
    }

    /**
     * First step of creating an image, it checks the validity of image (format, size, etc...)
     *
     * @param file The image
     * @return
     */
    @GetMapping(value = "/images")
    @ResponseBody
    public List<Image> createImage(@RequestParam("file") MultipartFile file) {
        return imageRepository.findAll(PageRequest.of(1, 50)).getContent();
    }

    @GetMapping()
    @ResponseBody
    public void getVersion() {
    }
}
