package JeeGrp5.mediatech.services.image;

import ai.djl.ModelException;
import ai.djl.modality.Classifications;
import ai.djl.translate.TranslateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageService {
    @Value("${mediatech.images.ai.threshold}")
    private float threshold;

    @Value("${mediatech.images.watermark-path}")
    private String watermarkPath;

    private final ObjectDetector objectDetector;
    private final ImageWatermark imageWatermark;

    public ImageService() throws IOException {
        this.objectDetector = new ObjectDetector();
        this.imageWatermark = new ImageWatermark();
    }

    /**
     * Return a list of objects and their probabilities from an image
     *
     * @param imagePath The image path
     * @return A list of objects (name + probability)
     */
    public Classifications detectObjects(String imagePath) throws ModelException, TranslateException, IOException {
        return objectDetector.detectObjects(imagePath, threshold);
    }

    /**
     * Add a watermark to an image
     *
     * @param imagePath The path to image
     * @throws IOException Thrown when image is not found
     */
    public void addWatermark(String imagePath) throws IOException {
        this.imageWatermark.addWatermarkToImage(watermarkPath, imagePath);
    }
}
