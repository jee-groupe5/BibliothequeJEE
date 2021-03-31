package JeeGrp5.mediatech.services.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WatermarkService {

    public static void addWatermark(String imagePath, String watermarkPath) {
        File origFile = new File(imagePath);
        ImageIcon image = new ImageIcon(origFile.getPath());

        // create BufferedImage object of same width and height as of original image
        BufferedImage bufferedImage = new BufferedImage(image.getIconWidth(),
                image.getIconHeight(), BufferedImage.TYPE_INT_RGB);

        // create graphics object and add original image to it
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image.getImage(), 0, 0, null);
        graphics.drawImage()
        graphics.dispose();

        File newFile = new File("C:/WatermarkedImage.jpg");
        try {
            ImageIO.write(bufferedImage, "jpg", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(newFile.getPath() + " created successfully!");
    }
}
