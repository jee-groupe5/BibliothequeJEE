package JeeGrp5.mediatech.web.v1.controllers;

import JeeGrp5.mediatech.entities.Image;
import JeeGrp5.mediatech.exceptions.ImageNotFoundException;
import JeeGrp5.mediatech.models.ImageFormat;
import JeeGrp5.mediatech.repositories.ImageRepository;
import JeeGrp5.mediatech.services.image.ImageService;
import JeeGrp5.mediatech.web.v1.dtos.ImagePatchDto;
import JeeGrp5.mediatech.web.v1.dtos.ImageUploadDto;
import ai.djl.modality.Classifications.Classification;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.*;

@Controller
@RequestMapping(path = "images")
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
    public ResponseEntity<?> download(
            @PathVariable String id,
            @RequestParam(defaultValue = "HD") String format) {
        Optional<Image> optionalImage = this.imageRepository.findById(id);
        ImageFormat imageFormat;
        try {
            imageFormat = ImageFormat.valueOf(format);
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (optionalImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Image image = optionalImage.get();
        try {
            // Download all formats
            if (imageFormat == ImageFormat.ALL) {
                return ResponseEntity
                        .ok()
                        .header("Content-Type", "application/zip")
                        .body(this.imageService.downloadAllFormatInZip(image));
            } else {
                // Download a specific format
                String imagePath = image.getUrls().get(imageFormat.name().toLowerCase(Locale.ROOT));
                URL imageUrl = new URL(imagePath);
                URLConnection urlConnection = imageUrl.openConnection();
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.valueOf(urlConnection.getContentType()))
                        .body(StreamUtils.copyToByteArray(imageUrl.openStream()));
            }
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

        // Generate an id
        imageRepository.save(image);

        String hdPath = Path.of(
                folderPath + "/" +
                        image.getId() + "/" +
                        sourceImage.getOriginalFilename()
        ).toString();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("hd", "file:///" + hdPath);
        image.setUrls(hashMap);

        File file = new File(hdPath);

        try {
            FileUtils.copyInputStreamToFile(sourceImage.getInputStream(), file);
            Classification[] classifications = this.imageService
                    .detectObjects(file.getAbsolutePath())
                    .items()
                    .toArray(new Classification[0]);
            String[] items = Arrays.stream(classifications)
                    .map(Classification::getClassName)
                    .toArray(String[]::new);
            image.setObjects(items);
            this.imageService.addWatermark(file.getAbsolutePath());

            imageRepository.save(image);
            return new ImageUploadDto(items, image.getId());
        } catch (Exception e) {
            imageRepository.delete(image);

            String messageError = "Une exception à eu lieu lors d'une validation d'une image";
            log.error("Error cause: " + e);
            throw new Error(messageError);
        }
    }

    /**
     * Edit an image
     *
     * @param id The image to edit
     * @return The edited image
     */
    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Image> upload(@PathVariable("id") String id, @RequestBody ImagePatchDto imagePatchDto) {
        try {
            Image image = this.imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(imagePatchDto.getKey(), Image.class);
            propertyDescriptor.getWriteMethod().invoke(image, imagePatchDto.getValue());
            return ResponseEntity.ok(this.imageRepository.save(image));
        } catch (ImageNotFoundException imageNotFoundException) {
            return ResponseEntity.notFound().build();
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
