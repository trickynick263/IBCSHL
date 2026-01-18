package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.UtilityTool;

public class SuperObject {//parent class of all objects in the game
    
    
    public String name;
    public java.awt.image.BufferedImage image;
    public boolean collision = false;
    public int worldX, worldY;//position of the object in the world
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);//default solid area for collision detection
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public UtilityTool uTool = new UtilityTool();


    public void draw(Graphics2D g2, main.GamePanel gp){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;//copy from tilemanager draw method

        //draw object only if it is in the visible area of the screen
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    
    }
}
