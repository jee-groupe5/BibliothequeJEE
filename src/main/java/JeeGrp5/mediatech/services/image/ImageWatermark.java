package JeeGrp5.mediatech.services.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageWatermark {

    public ImageWatermark() {
    }

    public void addWatermarkToImage(String watermarkPath, String imagePath) throws IOException {
        BufferedImage watermark = ImageIO.read(Paths.get(watermarkPath).toFile());

        File imageFile = Paths.get(imagePath).toFile();
        BufferedImage image = ImageIO.read(imageFile);

        Graphics g = image.getGraphics();
        g.drawImage(watermark, 10, 0, null);

        ImageIO.write(image, "png", new File(imagePath));
    }
}
