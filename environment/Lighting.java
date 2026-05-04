package environment;

import main.GamePanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Shape;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp,int circleSize){
        //Create a Buffered Image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        

        //get the center x and y of the light circle
        int centerX = gp.player.screenX + (gp.tileSize) / 2;
        int centerY = gp.player.screenY + (gp.tileSize) / 2;


        //Creat a gridation effect within the light circle
        Color color[] = new Color[10];
        float fraction[] = new float[10];
        
        

        color[0] = new Color(0,0,0,0.0f);
        color[1] = new Color(0,0,0,0.4f);
        color[2] = new Color(0,0,0,0.5f);
        color[3] = new Color(0,0,0,0.6f);
        color[4] = new Color(0,0,0,0.7f);
        color[5] = new Color(0,0,0,0.75f);
        color[6] = new Color(0,0,0,0.8f);
        color[7] = new Color(0,0,0,0.85f);
        color[8] = new Color(0,0,0,0.92f);
        color[9] = new Color(0,0,0,1f);

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.7f;
        fraction[5] = 0.75f;
        fraction[6] = 0.8f;
        fraction[7] = 0.85f;
        fraction[8] = 0.92f;
        fraction[9] = 1f;

        //create a gradation paint setting for the light circle
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY, (circleSize/2),fraction,color);

        //set gradient data on g2
        g2.setPaint(gPaint);

        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        g2.dispose();
    }

    public void draw(Graphics2D g2){
        g2.drawImage(darknessFilter,0,0,null);
    }

}
