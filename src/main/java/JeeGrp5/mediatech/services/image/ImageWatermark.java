package JeeGrp5.mediatech.services.image;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWatermark {

    public ImageWatermark() throws IOException {

    }

    public void addWatermarkToImage(String watermarkPath, String imagePath) throws IOException {
        BufferedImage watermark = ImageIO.read(new File(watermarkPath));
        File imageFile = new File(imagePath);
        BufferedImage image = ImageIO.read(imageFile);
        Graphics g = image.getGraphics();
        g.drawImage(watermark, 0, 0, watermark.getWidth(), watermark.getHeight(), null);
        ImageIO.write(image, FilenameUtils.getExtension(imagePath), imageFile);
    }
}
