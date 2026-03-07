package entity;
import main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.KeyHandler;
import main.UtilityTool;
import objects.OBJ_Boots;
import objects.OBJ_Key;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;


public class Player extends Entity {
    //PLAYER CLASS INHERITS FROM ENTITY CLASS
    
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    //those are 
    //2 variables to fix the player in the center of the screen
    
    //TEST
    int spriteChecker = 0;
    
    
    

    

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();                                                                                           
        solidArea = new Rectangle();//if we want to make a rectangle that is the size of a tile ->args(0, 0, 48, 48)
        solidArea.x = 8;
        solidArea.y = 16;//we can also set the values one by one
        
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        
        solidArea.width = 32;
        solidArea.height = 32;


        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);//this will center the player on the screen
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        
        
        //ATTACK AREA DETERMINES ATTACK RANGE AND AREA OF EFFECT
        attackArea.width = 36;
        attackArea.height = 36;
    }

    public void setItems(){
        inventory.add(currentShield);
        inventory.add(currentWeapon);
      
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 3;//starting position of player in the world
        speed = 4;
        direction = "down";
        
        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
        level = 1;
        strength = 1;
        dexterity = 1;
        nextLevelExp = 5;
        exp = 0;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();

        
        
        
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;//updates attack area when swapping equipment
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage(){
       
        up1 = setup("/playerimage/player up 1", gp.tileSize, gp.tileSize);
        up2 = setup("/playerimage/player up 2", gp.tileSize, gp.tileSize);
        down1 = setup("/playerimage/player down 1", gp.tileSize, gp.tileSize);
        down2 = setup("/playerimage/player down 2", gp.tileSize, gp.tileSize);
        left1 = setup("/playerimage/player left 1", gp.tileSize, gp.tileSize);
        left2 = setup("/playerimage/player left 2", gp.tileSize, gp.tileSize);
        right1 = setup("/playerimage/player right 1", gp.tileSize, gp.tileSize);
        right2 = setup("/playerimage/player right 2", gp.tileSize, gp.tileSize);



    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/playerimage/player attack up 1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/playerimage/player attack up 2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/playerimage/player attack down 1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/playerimage/player attack down 2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/playerimage/player attack left 1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/playerimage/player attack left 2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/playerimage/player attack right 1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/playerimage/player attack right 2", gp.tileSize*2, gp.tileSize);
        //we will call this method in the constructor so that the attack images are loaded when the game starts
        // we add this in a different method because when we want to change what item or weapon the player
        //has, we just swap methods in order to change what weapon the player wants to use and the corresponding attack images will be loaded
    }



    public BufferedImage setup(String imageName, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            //image = ImageIO.read(getClass().getResourceAsStream("/res" + imageName + ".png")); //school pc
            image = ImageIO.read(new File("res" + imageName + ".png")); //home pc
            image = uTool.scaleImage(image, width, height);
            
        } catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    
    public void update() {

        if(attacking == true){
            attacking();
            return;//this will stop the player from moving when attacking
        }

        

        if(keyH.upPressed == true || keyH.downPressed == true || 
            keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
        
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

        //CHECK NPC Collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);


        //CHECK EVENT
        gp.eHandler.checkEvent();

        
        
        //CHECK MONSTER COLLISION
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        if(invincible == false){
            contactMonster(monsterIndex);
        }

        if(keyH.enterPressed == true && attackCanceled == false){
            gp.playSE(7);
            attacking = true;
            spriteCounter = 0;
        }
        attackCanceled = false;
        
        //IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(collisionOn == false && keyH.enterPressed == false){
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
        
        spriteCounter++;
        if(attacking == false){
            spriteChecker = 12;
        }
        else{
            spriteChecker = 24;
        }
            if(spriteCounter > spriteChecker){//changes sprite every 12 frames
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
        
       
        //Needs to be outside key if statement
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking(){
        spriteNum = 1;
        spriteCounter++;
        if(spriteCounter <= 10){
            spriteNum = 1;//shows first attacking image for the first 5 frames
        }
        if(spriteCounter > 10 && spriteCounter <=  30){
            spriteNum = 2;//shows second attacking image for the next 20 frames
            //Save current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //Adjust player's worldX/Y for the attackArea
            switch(direction){
                case "up":
                    worldY -= attackArea.height;//moves the area were checking upwards so we can check for the sword hitting the enemy
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //this gets the monster that hit the player and if collision is detected then
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);
            //After checking collision, restore original worldX/Y and solidArea
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 30){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int index){
        if(index != 999){
            String text;
            if(inventory.size() < maxInventorySize){
                inventory.add(gp.obj[index]);
                gp.playSE(1);
                text = "Got a " + gp.obj[index].name + "!";
           }
           else{
            text = "You cannot carry anymore items!";
           }
           gp.ui.addMessage(text);
           gp.obj[index] = null;
        }
    }

    public void contactMonster(int i){
        if(i!=999){
            int damage = gp.monster[i].attack - defense;
            if(damage < 0){
                damage = 0;//in case monsters defense is higher than player's attack, we don't want to heal the monster by doing negative damage, so we set it to 0 instead
            }
            life-=damage;
            gp.playSE(6);
            invincible = true;
        }
    }

    public void damageMonster(int i){
        if(i!=999){
            if(gp.monster[i].invincible == false){
                gp.playSE(5);
                int damage = attack - gp.monster[i].defense;
                if(damage < 0){
                    damage = 0;//in case monsters defense is higher than player's attack, we don't want to heal the monster by doing negative damage, so we set it to 0 instead
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage("You Hit the Monster for " + damage + " Damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();//sets the direction of the monster to move away from the player
            }
            if(gp.monster[i].life <= 0 && gp.monster[i].alive == true){
                gp.monster[i].dying = true;
                gp.ui.addMessage("You Killed the "  + gp.monster[i].name + "!");
                gp.ui.addMessage("Exp + "  + gp.monster[i].exp + "!");
                exp+= gp.monster[i].exp;
                checkLevelUp();
            }
        }
    }

    public void checkLevelUp(){
        if(exp>= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp + 10;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(4);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are now level " + level + " now!\n" + "You feel way Stronger!";
        }
    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){
                //later
            }
        }
    }


    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            
        } 
    }


    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);(our rectangle we will no longer use is here for testing)
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;
        /*We are going to create integer variables in order to adjust the position of the player when drawn on the
         the right spot not matter how the player image is depending on its size, like 32x16 or 16x16 */
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch(direction){
        case "up":
            if(attacking == false){
                if(spriteNum == 1){image = up1;}
                if(spriteNum == 2){image = up2;}
            }
            if(attacking == true){
                tempScreenY = screenY - gp.tileSize;//we adjust the y position of the player when attacking up because the attack image is 2 tiles high
                if(spriteNum == 1){image = attackUp1;}
                if(spriteNum == 2){image = attackUp2;}
            }
            break;
        case "down":
            if(attacking == false){
                if(spriteNum == 1){image = down1;}
                if(spriteNum == 2){image = down2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackDown1;}
                if(spriteNum == 2){image = attackDown2;}
            }
            break;
        case "left":
            if(attacking == false){
                if(spriteNum == 1){image = left1;}
                if(spriteNum == 2){image = left2;}
            }
            if(attacking == true){
                tempScreenX = screenX - 23;
                if(spriteNum == 1){image = attackLeft1;}
                if(spriteNum == 2){image = attackLeft2;}
            }
            break;
        case "right":
            if(attacking == false){
                if(spriteNum == 1){image = right1;}
                if(spriteNum == 2){image = right2;}
            }
            if(attacking == true){
                tempScreenX = screenX - 23;
                if(spriteNum == 1){image = attackRight1;}
                if(spriteNum == 2){image = attackRight2;}
            }
            break;
        }
        
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));//sets the opacity of the player
        }

        
        
        g2.drawImage(image, tempScreenX, tempScreenY, null);//draws the image at the x and y position with the tile size width and height
        //the image above is drawn at a certain x and y position with an image corresponding to direction


        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    
    }
}
