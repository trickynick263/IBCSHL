package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {//THIS CLASS WILL BE THE BASE CLASS FOR ALL ENTITIES IN THE GAME LIKE PLAYER, NPCS, MONSTERS, ETC
    //this means it basically will be a superclass and other classes
    //will inherit and take attributes and methods from this class
    public int worldX, worldY;//we will use 2 different types of x and y, screen and world, they both indicate position but
                                //world x and y indicate position in the whole game world while screen x and y indicate position on the screen
    public int speed;//speed of entity

    public GamePanel gp;
    public int actionLockCounter = 0;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;//images for different directions of entity
    public String direction = "down";//direction entity is facing
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
    attackLeft1, attackLeft2, attackRight1, attackRight2;//attack images for different directions
    public boolean attacking = false;//flag to check if entity is attacking

    //ATTACK
    public Rectangle attackArea = new Rectangle(0,0,0,0);//area of attack for collision detection
    //The parameters of the rectangle will be set based on what attack the player is doing, if they 
    //are sweeping it might have a wider range while a sword chop might not

    //Transfer from superobject
    public String name;
    public java.awt.image.BufferedImage image, image2, image3;//so we can read 3 images
    public boolean collision = false;
    public int spriteCounter = 0;//counts how many frames have passed to switch between sprite images
    public int spriteNum = 1;//which sprite image to use
    public Rectangle solidArea = new Rectangle(0,0,48,48);//default solid area for collision detection
    public int solidAreaDefaultX, solidAreaDefaultY;//to store default x and y of solid area for resetting after collision adjustments
    public boolean collisionOn = false;//flag to check if collision is on or off
    //DIALOGUE
    String[] dialogue = new String[20];
    int dialogueIndex = 0;

    //CHARACTER STATUS
    public int maxLife;
    public int life;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int type; //0 is player, 1 is npc, 2 is monster
    



    
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }

    

    public void update(){
            setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true){
            if(gp.player.invincible == false){
                //we can give some damage
                gp.player.life -=1;
                gp.player.invincible = true;
            }
        }


        //copied from player class
           if(collisionOn == false){
            switch(direction){
                case "up":
                    worldY -= speed;//upper left corner is 0,0 so to go up we decrease y value
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        

        //SPRITE ANIMATION
        if(collisionOn == false){
        spriteCounter++;
        if(spriteCounter > 12){//changes sprite every 12 frames
            if(spriteNum == 1){
                spriteNum = 2;//changes sprite images to swap between them
            }//remember this gets called 60 times per second and the counter is increased
            //a total of 60 times per second and which switch between images very often
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;//this line right here resets the counter
            //  so we can count to 12 again
        }
        }
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 30){
                invincible = false;
                invincibleCounter = 0;
            }
        }


    }
/*The reason we create many new draw and update methods in a superclass is because the 
methods and attributes get passed down to things like the npcs, player, and anything else that fits
the requirements of an entity */
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;//copy from tilemanager draw method

        //draw object only if it is in the visible area of the screen
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

        switch(direction){//these are the buffered images we loaded earlier
        case "up"://based on direction we pick a differnt image to draw
            if(spriteNum == 1){image = up1;}
            if(spriteNum == 2){image = up2;}
            break;
        case "down":
            if(spriteNum == 1){image = down1;}
            if(spriteNum == 2){image = down2;}
            break;
        case "left":
            if(spriteNum == 1){image = left1;}
            if(spriteNum == 2){image = left2;}
            break;
        case "right":
            if(spriteNum == 1){image = right1;}
            if(spriteNum == 2){image = right2;}
            break;
        }  

        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));//sets the opacity of the player
        }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));//sets the opacity of the player
    }

    public void speak(){
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;

        if(dialogue[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        switch(gp.player.direction){
        case "up":
        direction = "down";
        break;
        case "down":
        direction = "up";
        break;
        case"right":
        direction = "left";
        break;
        case"left":
        direction = "right";
        break;
        }
    }


    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            //image = ImageIO.read(getClass().getResourceAsStream("/res" + imagePath + ".png")); //school pc
            image = ImageIO.read(new File("res" + imagePath + ".png")); //home pc
            image = uTool.scaleImage(image, width, height);
            
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }





}
