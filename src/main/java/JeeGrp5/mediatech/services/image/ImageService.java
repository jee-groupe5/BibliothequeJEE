package JeeGrp5.mediatech.services.image;

import JeeGrp5.mediatech.entities.Image;
import JeeGrp5.mediatech.models.ImageFormat;
import ai.djl.ModelException;
import ai.djl.modality.Classifications;
import ai.djl.translate.TranslateException;
import com.twelvemonkeys.net.MIMEUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Value("${mediatech.images.ai.threshold}")
    private float threshold;

    @Value("${mediatech.images.watermark-path}")
    private String watermarkPath;

    private final ObjectDetector objectDetector;
    private final ImageWatermark imageWatermark;

    public ImageService() {
        this.objectDetector = new ObjectDetector();
        this.imageWatermark = new ImageWatermark();
    }

    public byte[] downloadAllFormatInZip(Image image) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            final ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

            for (String format : image.getUrls().keySet()) {
                String url = image.getUrls().get(format);
                URLConnection urlConnection = new URL(url).openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                ZipEntry zipEntry = new ZipEntry(format + "." + MIMEUtil.getExtension(urlConnection.getContentType()));
                zipOutputStream.putNextEntry(zipEntry);

                IOUtils.copy(inputStream, zipOutputStream);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * Downscale an image into multiple sub-formats
     *
     * @param imagePath path of the image to downscale
     */
    public BufferedImage downscale(BufferedImage imageToDownscale, ImageFormat imageFormat) throws IOException {
        BufferedImage resultImage = new BufferedImage(
                imageFormat.getWidth(),
                imageFormat.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics2D = resultImage.createGraphics();
        graphics2D.drawImage(
                imageToDownscale,
                0, 0,
                imageFormat.getWidth(),
                imageFormat.getHeight(),
                null
        );
        graphics2D.dispose();

        return resultImage;
    }

    /**
     * Return a list of objects and their probabilities from an image
     *
     * @param inputStream The image input stream
     * @return A list of objects (name + probability)
     */
    public Classifications detectObjects(InputStream inputStream) throws ModelException, TranslateException, IOException {
        return objectDetector.detectObjects(inputStream, threshold);
    }

    /**
     * Add a watermark to an image
     *
     * @param bufferedImage The buffered image
     * @throws IOException Thrown when image is not found
     */
    public BufferedImage addWatermark(BufferedImage bufferedImage) throws IOException {
        return this.imageWatermark.addWatermarkToImage(watermarkPath, bufferedImage);
    }
}
