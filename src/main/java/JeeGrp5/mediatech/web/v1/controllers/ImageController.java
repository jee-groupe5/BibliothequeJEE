package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.entities.Image;
import JeeGrp5.mediatech.entities.Person;
import JeeGrp5.mediatech.models.ImageObject;
import JeeGrp5.mediatech.repositories.ImageRepository;
import JeeGrp5.mediatech.web.v1.dtos.ImageMetadataDto;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("images")
public class ImageController {
    private final static Logger log = LoggerFactory.getLogger(ImageController.class);
    private final ImageRepository imageRepository;

    @Value("${mediatech.images.folder-path}")
    private String folderPath;

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
    public List<Image> getAll(@PathVariable int page) {
        return imageRepository.findAll(PageRequest.of(page, 50)).getContent();
    }

    /**
     * Upload an image
     *
     * @param sourceImage The image to validate
     * @return Metadata, containing the presence (or not) of persons, objects (with probability) and all dimensions
     * of the image formats
     */
    @PostMapping("/")
    @ResponseBody
    public ImageMetadataDto upload(@RequestParam("file") MultipartFile sourceImage) {
        Image image = new Image();
        imageRepository.save(image);

        String path = folderPath + image.getId() + "/" + image.getId() + "/" + sourceImage.getOriginalFilename();
        File file = new File(path);

        try {
            FileUtils.copyInputStreamToFile(sourceImage.getInputStream(), file);
            return new ImageMetadataDto(new Person[]{}, new ImageObject[]{}, new HashMap<>());
        } catch (Exception e) {
            imageRepository.delete(image);

            String messageError = "Une exception à eu lieu lors d'une validation d'une image";
            log.error("Error cause: " + e);
            throw new Error(messageError);
        }
    }
}
