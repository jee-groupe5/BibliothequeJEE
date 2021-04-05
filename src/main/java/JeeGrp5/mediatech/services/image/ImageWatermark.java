package JeeGrp5.mediatech.services.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageWatermark {

    public ImageWatermark() {
    }

    public BufferedImage addWatermarkToImage(String watermarkPath, BufferedImage originalBufferedImage) throws IOException {
        BufferedImage watermark = ImageIO.read(new URL(watermarkPath).openStream());

        Graphics g = originalBufferedImage.getGraphics();
        g.drawImage(watermark, 10, 0, null);
        g.dispose();

        return originalBufferedImage;
    }
}
