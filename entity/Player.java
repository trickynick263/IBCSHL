package entity;
import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.KeyHandler;
import main.UtilityTool;


public class Player extends Entity {
    //PLAYER CLASS INHERITS FROM ENTITY CLASS
    
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    //those are 
    //2 variables to fix the player in the center of the screen
    
    
    
    
    

    

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        //                                                                                            x,y,width,height
        solidArea = new Rectangle();//if we want to make a rectangle that is the size of a tile ->args(0, 0, 48, 48)
        solidArea.x = 8;
        solidArea.y = 16;//we can also set the values one by one
        
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        //
        
        solidArea.width = 32;
        solidArea.height = 32;
        /*for using the rectangle for collision detection, imagine that the rectangle is one tile,
        0,0 is the top left corner and if we increase the x we go to the left and start the collision there
        and if we increase y we go down and start the collision there as well, in this scenario by making the 
        width 32, we leave off another 8 pixels to make a low height, centered rctangle with a width smaller than 
        a tile by 16 pixels, 8 from the left and 8 from the right
        */


        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);//this will center the player on the screen
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 3;//starting position of player in the world
        speed = 4;
        direction = "down";
        
    }
    public void getPlayerImage(){
       
        up1 = setup("running up");
        up2 = setup("looking up");
        down1 = setup("running down");
        down2 = setup("looking down");
        left1 = setup("running left");
        left2 = setup("looking left");
        right1 = setup("running right");
        right2 = setup("looking right");

    }
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    
    public void update(){
        
        
        if(keyH.upPressed == true || keyH.downPressed == true || 
            keyH.leftPressed == true || keyH.rightPressed == true){
        
        if(keyH.upPressed == true){//we will now set the player's direction corresponding to directuion headed
            direction = "up";
        }
        else if(keyH.downPressed == true){
            direction = "down";
        }
        else if(keyH.leftPressed == true){
            direction = "left";
        }
        else if(keyH.rightPressed == true){
            direction = "right";
        }
        
        //CHECK TILE COLLISION
        collisionOn = false;//resets collision flag for next check
        gp.cChecker.checkTile(this);//checks tile collision, we pass in the player object
        int objIndex = gp.cChecker.checkObject(this,true);
        pickUpObject(objIndex);




        //IF COLLISION IS FALSE, PLAYER CAN MOVE
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





        }//this if statement will fix the bug where if no keys are pressed, the sprite will not keep moving
        else{
            spriteNum = 1;//if no keys are pressed, set sprite to first image
        }
        
        
        
        
    }

    public void pickUpObject(int index){
        if(index != 999){
           
        }
    }

    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);(our rectangle we will no longer use is here for testing)
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;
        /* CASE AND SWITCH EXPLAINED
        since i only did csa i didnt know what these meant.when using switch it is basically
        saying check these possibilities for a variable such as the one below which is direction
        if the variable is this >"in case"<, do this which is exactly what the program does below */
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
        g2.drawImage(image, screenX, screenY, null);//draws the image at the x and y position with the tile size width and height
        //the image above is drawn at a certain x and y position with an image corresponding to direction

    }
}
