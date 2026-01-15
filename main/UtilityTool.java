package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
    
    //utility tool class to help with various tasks in the game
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());//creates a blank canvas with the desired width and height
        Graphics2D g2d = scaledImage.createGraphics();//creates a graphics2D object to draw the image
        g2d.drawImage(original, 0, 0, width, height, null);//draws the original image onto the blank canvas with the desired width and height
        g2d.dispose();//disposes of the graphics2D object to free up resources
        return scaledImage;//returns the scaled image
    }

}
