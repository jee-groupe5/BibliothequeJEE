package JeeGrp5.mediatech.services.image;

import ai.djl.ModelException;
import ai.djl.modality.Classifications;
import ai.djl.translate.TranslateException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class ImageService {
    private ObjectDetector objectDetector;

    public ImageService() {
        this.objectDetector = new ObjectDetector();
    }

    /**
     * Return a list of objects and their probabilities from an image
     *
     * @param imagePath The image path
     * @return A list of objects (name + probability)
     */
    public Classifications detectObjects(String imagePath) throws ModelException, TranslateException, IOException {
        return objectDetector.detectObjects(imagePath);
    }

    public void addWatermark(String imagePath, String watermarkPath) {
        WatermarkService.addWatermark(imagePath, watermarkPath);
    }
}
