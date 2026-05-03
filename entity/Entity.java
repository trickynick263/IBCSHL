package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {//THIS CLASS WILL BE THE BASE CLASS FOR ALL ENTITIES IN THE GAME LIKE PLAYER, NPCS, MONSTERS, ETC
    public int worldX, worldY;
    public int speed;

    public GamePanel gp;
    public int actionLockCounter = 0;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;//images for different directions of entity
    public String direction = "down";
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
    attackLeft1, attackLeft2, attackRight1, attackRight2;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public boolean onPath = false;
    public boolean knockBack = false;
    
    public int defaultSpeed;
    public int knockBackCounter = 0;
    public int maxMana;
    public int mana;
    public Projectile projectile;
    public int useCost;
    public int level;
    public int strength;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public Entity currentWeapon;
    public Entity currentShield;
    public int dexterity;
    public int coin;
    public String description = "";
    public int ammo;
    public int knockBackPower = 0;
    public int manaRegenCounter = 0;
    public int shotAvailableCounter = 0;
    //ITEM ATTRIBUTES
    public int attackValue;//this will be used for the price of the item in shops and also for how much exp a monster gives when defeated
    public int defenseValue;
    public int price;
    public boolean stackable = false;
    public int amount = 1;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;


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
    public int value;
    public int life;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;


    
    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){

    }

    public void damageReaction(){
        
    }

    public void interact(){

    }

    public int getLeftX(){
        return worldX + solidArea.x;
    }

    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY(){
        return worldY + solidArea.y;
    }

    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol(){
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow(){
        return (worldY + solidArea.y) / gp.tileSize;
    }

    public int getDetected(Entity user, Entity[][] target, String targetName){
        int index = 999;
        //check surrounding tiles for objects
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch(user.direction){
            case "up" : nextWorldY = user.getTopY() - 1; break;
            case "down" : nextWorldY = user.getBottomY() + 1; break;
            case "left" : nextWorldX = user.getLeftX() - 1; break;
            case "right" : nextWorldX = user.getRightX() + 1; break;
        }
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;
        
        for(int i = 0; i < target[1].length;i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col 
                  && target[gp.currentMap][i].getRow() == row){
                    if(target[gp.currentMap][i].name.equals(targetName)){
                        index = i;
                        break;
                    }
                }
            }   
        }

        return index;

    }

    

    public void update(){
        if(knockBack == true){
            checkCollision();
            if(collisionOn == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collisionOn == false){
                switch(gp.player.direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 35){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            if(knockBackCounter == 30){
                speed = speed/2;
            }
            if(knockBackCounter == 24){
                speed = speed/2;
            }
            if(knockBackCounter == 18){
                speed = speed/2;
            }
        }else{
            setAction();
            checkCollision();
            if(collisionOn == false){
                switch(direction){
                    case "up":worldY -= speed;break;
                    case "down":worldY += speed;break;
                    case "left":worldX -= speed;break;
                    case "right":worldX += speed;break;
                }
            }
        }

        

        //SPRITE ANIMATION
        
        spriteCounter++;
        if(spriteCounter > 30){
            if(spriteNum == 1){spriteNum = 2;}
            else if(spriteNum == 2){spriteNum = 1;}
            spriteCounter = 0;
        }
        
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 30){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        if(this.mana < this.maxMana){
            manaRegenCounter++;
        }
        if(manaRegenCounter > 300){
            if(mana < maxMana){mana++;}
            manaRegenCounter = 0;
        }
        if(life > maxLife){life = maxLife;}
        if(life < 0){life = 0;}
        if(mana > maxMana){mana = maxMana;}
    }

    public void checkDrop(){

    }

    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer == true){
            damagePlayer(attack);
        }
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX+solidArea.x) / gp.tileSize;
        int startRow = (worldY+solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if(gp.pFinder.search() ==true){
            //next Worldx and worldy
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            //Entity's solid area position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                //left or right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){
                //direction is either up or left, we need to check which one is closer
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX){
                //up or right
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX){
                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX){
                //down or right
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }
            /* disabled because as soon as we talk to the npc, path is finished and stops 
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }
            */
        }
    }

    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }
    public void damagePlayer(int attack){
        
            if(gp.player.invincible == false){
                //we can give some damage
                gp.playSE(6);
                int damage = attack - gp.player.defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invincible = true;
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

        //MONSTER HP BAR
        if(type == 2 && hpBarOn == true){
        
        double oneScale = (double)gp.tileSize/maxLife;//this is how much pixels each hp is worth
        double hpBarValue = oneScale*life;//this is how long the hp bar should
        g2.setColor(new Color(0,0,0));
        g2.fillRect(screenX-3, screenY - 18, gp.tileSize+6, 16);
        g2.setColor(new Color(35,35,35));
        g2.fillRect(screenX-2, screenY - 17, gp.tileSize+4, 14);
        //LIFE BAR
        g2.setColor(new Color(255,0,30));
        g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
        hpBarCounter++;
        if(hpBarCounter > 600){
            hpBarCounter = 0;
            hpBarOn = false;
        }
    }   

        
        if(invincible == true){
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2, 0.4f);
        }
        if(dying == true){
            dyingAnimation(g2);
        }

            g2.drawImage(image, screenX, screenY, null);
        

        changeAlpha(g2, 1f);//resets alpha value back to normal after drawing entity        
        }
    }

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 9;//variable to control the speed of the animation, the higher the number the slower the animation
        alive = false;
        if(dyingCounter<=i){
            changeAlpha(g2, 0.0f);
}
        if(dyingCounter>i && dyingCounter<=i*2){
            changeAlpha(g2, 1.0f);
        }
        if(dyingCounter>i*2 && dyingCounter<=i*3){
            changeAlpha(g2, 0.0f);
        }
        if(dyingCounter>i*3 && dyingCounter<=i*4){
            changeAlpha(g2, 1.0f);
        }
        if(dyingCounter>i*4 && dyingCounter<=i*5){
            changeAlpha(g2, 0.0f);
        }
        if(dyingCounter>i*5 && dyingCounter<=i*6){
            changeAlpha(g2, 1.0f);
        }
        if(dyingCounter>i*6 && dyingCounter<=i*7){
            changeAlpha(g2, 0.0f);
        }
        if(dyingCounter > i*8){
            alive = false;
            dying = false;
        }
    }
    


    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
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
            //image = ImageIO.read(new File("res" + imagePath + ".png")); //home pc
            image = ImageIO.read(getClass().getResourceAsStream("/res" + imagePath + ".png")); //school pc
            image = uTool.scaleImage(image, width, height);
            
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public boolean use(Entity entity) {
        return false;
    }
    //METHODS FOR PARTICLE EFFECTS and inside Interactive Tiles class
     public Color getParticleColor(){
        Color color = null;
        return color;//color of the particle
    }

    public int getParticleSize(){
        int size = 0;//size of the particle which is 6 pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed = 0;//how fast the particle moves
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;//how long the particle lasts
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -1, -1);
        gp.particleList.add(p1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, -1, 1);
        gp.particleList.add(p2);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, 1, -1);
        gp.particleList.add(p3);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 1, 1);
        gp.particleList.add(p4);

        
    }





}
