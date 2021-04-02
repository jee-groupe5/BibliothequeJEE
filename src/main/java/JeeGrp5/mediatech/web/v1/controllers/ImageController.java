package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.entities.Image;
import JeeGrp5.mediatech.repositories.ImageRepository;
import JeeGrp5.mediatech.services.image.ImageService;
import JeeGrp5.mediatech.web.v1.dtos.ImageUploadDto;
import ai.djl.modality.Classifications;
import ai.djl.modality.Classifications.Classification;
import jdk.jfr.ContentType;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("images")
public class ImageController {
    private final static Logger log = LoggerFactory.getLogger(ImageController.class);
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Value("${mediatech.images.folder-path}")
    private String folderPath;

    public ImageController(ImageRepository imageRepository, ImageService imageService) {
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    /**
     * Retrieve 50 images starting from the specified page
     *
     * @return A list of image
     */
    @GetMapping(value = "/pages/{page}")
    @ResponseBody
    public List<Image> getAll(@PathVariable int page) {
        return imageRepository.findAll(PageRequest.of(page, 50)).getContent();
    }

    /**
     * Download an image
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getOne(@PathVariable String id) {
        Optional<Image> optionalImage = this.imageRepository.findById(id);

        if (optionalImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Image image = optionalImage.get();
        String imagePath = image.getUrls().get("hd");
        try {
            URL hdImageUrl = new URL(imagePath);
            URLConnection urlConnection = hdImageUrl.openConnection();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(urlConnection.getContentType()))
                    .body(StreamUtils.copyToByteArray(hdImageUrl.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public ImageUploadDto upload(@RequestParam("file") MultipartFile sourceImage) {
        Image image = new Image();

        String hdPath = folderPath + "/" + image.getId() + "/" + sourceImage.getOriginalFilename();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("hd", hdPath);
        image.setUrls(hashMap);

        imageRepository.save(image);

        File file = new File(hdPath);

        try {
            FileUtils.copyInputStreamToFile(sourceImage.getInputStream(), file);
            Classifications classifications = this.imageService.detectObjects(file.getAbsolutePath());
//            this.imageService.addWatermark(file.getAbsolutePath());

            return new ImageUploadDto(classifications.items().toArray(new Classification[0]), new HashMap<>());
        } catch (Exception e) {
            imageRepository.delete(image);

            String messageError = "Une exception à eu lieu lors d'une validation d'une image";
            log.error("Error cause: " + e);
            throw new Error(messageError);
        }
    }
}
