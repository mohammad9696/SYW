package Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ShowImage {
    private static final Logger logger = LoggerFactory.getLogger(ShowImage.class);
    public static void LaunchImage (String imageUrl) {
        logger.info("Displaying image from url {}", imageUrl);
        EventQueue.invokeLater(new Runnable() {
                                   public void run() {
                                       ImageFrame frame = new ImageFrame(imageUrl);
                                       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                       frame.setVisible(true);

                                   }
                               }
        );

    }
}

class ImageFrame extends JFrame {
    public ImageFrame(String imageUrl) {
        setTitle("Imagem de Produto");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        ImageComponent component = new ImageComponent(imageUrl);
        add(component);
        getContentPane().validate();
        getContentPane().repaint();
    }

    public static final int DEFAULT_WIDTH = 1000;
    public static final int DEFAULT_HEIGHT = 1000;
}

class ImageComponent extends JComponent {
    private static final long serialVersionUID = 1L;
    private Image image;

    public ImageComponent(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        if (image == null) return;
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);

        g.drawImage(image, 0, 0, this);

        for (int i = 0; i * imageWidth <= getWidth(); i++)
            for (int j = 0; j * imageHeight <= getHeight(); j++)
                if (i + j > 0) g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);


    }
}