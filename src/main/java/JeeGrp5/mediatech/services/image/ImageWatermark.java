package JeeGrp5.mediatech.services.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageWatermark {

    public ImageWatermark() {
    }

    public BufferedImage addWatermarkToImage(String watermarkPath, BufferedImage originalBufferedImage) throws IOException {
        BufferedImage watermark = ImageIO.read(Paths.get(watermarkPath).toFile());

        Graphics g = originalBufferedImage.getGraphics();
        g.drawImage(watermark, 10, 0, null);
        g.dispose();

        return originalBufferedImage;
    }
}
