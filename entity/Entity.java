package entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Entity {//THIS CLASS WILL BE THE BASE CLASS FOR ALL ENTITIES IN THE GAME LIKE PLAYER, NPCS, MONSTERS, ETC
    //this means it basically will be a superclass and other classes
    //will inherit and take attributes and methods from this class
    public int worldX, worldY;//we will use 2 different types of x and y, screen and world, they both indicate position but
                                //world x and y indicate position in the whole game world while screen x and y indicate position on the screen
    public int speed;//speed of entity

    public GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;//images for different directions of entity
    public String direction;//direction entity is facing
    
    
    
    public int spriteCounter = 0;//counts how many frames have passed to switch between sprite images
    public int spriteNum = 1;//which sprite image to use

    public Rectangle solidArea = new Rectangle(0,0,48,48);//default solid area for collision detection
    
    public int solidAreaDefaultX, solidAreaDefaultY;//to store default x and y of solid area for resetting after collision adjustments
    public boolean collisionOn = false;//flag to check if collision is on or off
    



    
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;//copy from tilemanager draw method
        //draw object only if it is in the visible area of the screen
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            BufferedImage image = null;
            switch(direction){//these are the buffered images we loaded earlier
        case "up"://based on direction we pick a differnt image to draw
            if(spriteNum == 1){
                image = up1;
            }
            if(spriteNum == 2){
                image = up2;
            }
            break;
        case "down":
            if(spriteNum == 1){
                image = down1;
            }
            if(spriteNum == 2){
                image = down2;
            }
            break;
        case "left":
            if(spriteNum == 1){
                image = left1;
            }
            if(spriteNum == 2){
                image = left2;
            }
            break;
        case "right":
            if(spriteNum == 1){
                image = right1;
            }
            if(spriteNum == 2){
                image = right2;
            }
            break;//                                                    image observer,js type null
        }//                                                            ^^^^
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);//draws the image at the x and y position with the tile size width and height
        //the image above is drawn at a certain x and y position with an image corresponding to direction
        }
        
    }



}
