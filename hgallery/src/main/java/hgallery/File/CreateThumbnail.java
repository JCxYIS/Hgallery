package hgallery.File;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
//w  w  w. j a  va  2  s.c o  m
// http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Create_thumbnail_views_of_images.htm

import javax.imageio.ImageIO;

import hgallery.Debug;

public class CreateThumbnail 
{
    public static void CreateThumbnail(String filepath, String outputPath) throws Exception 
    {
        File file = new File(filepath);
        BufferedImage img = ImageIO.read(file);
        BufferedImage thumb = new BufferedImage(400, 600,
            BufferedImage.TYPE_INT_RGB);
    
        Graphics2D g2d = (Graphics2D) thumb.getGraphics();
        g2d.drawImage(img, 0, 0, thumb.getWidth() - 1, thumb.getHeight() - 1, 0, 0,
            img.getWidth() - 1, img.getHeight() - 1, null);
        g2d.dispose();

        //Debug.Log(outputPath);
        File output = new File(outputPath);
        output.mkdirs();
        ImageIO.write(thumb, "JPG", output);
      }
}