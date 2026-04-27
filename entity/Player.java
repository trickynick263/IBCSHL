package entity;
import main.GamePanel;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.KeyHandler;
import main.UtilityTool;
import objects.OBJ_Axe;
import objects.OBJ_BlueEnergyOrb;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;


public class Player extends Entity {
    //PLAYER CLASS INHERITS FROM ENTITY CLASS
    
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    
    //those are 
    //2 variables to fix the player in the center of the screen
    
    //TEST
    int spriteChecker = 0;

    //TEST FOR JUMP ABILITY
    public int jumpCounter = 0;
    
    
    

    

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
        inventory.clear();
        inventory.add(currentShield);
        inventory.add(currentWeapon);
        inventory.add(new OBJ_Axe(gp));
      
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 40;
        worldY = gp.tileSize * 40;
        gp.currentMap = 0;

        speed = 4;
        direction = "down";
        
        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
        level = 1;
        strength = 1;
        dexterity = 1;
        nextLevelExp = 5;
        maxMana = 4;
        mana = maxMana;
        exp = 0;
        coin = 500;
        ammo = 10;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
        projectile = new OBJ_BlueEnergyOrb(gp);

        
        
        
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
        if(this.currentWeapon.type == type_sword){
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
        if(this.currentWeapon.type == type_axe){
        attackUp1 = setup("/playerimage/player axe up 1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/playerimage/player axe up 2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/playerimage/player axe down 1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/playerimage/player axe down 2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/playerimage/player axe left 1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/playerimage/player axe left 2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/playerimage/player axe right 1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/playerimage/player axe right 2", gp.tileSize*2, gp.tileSize);
        //we will call this method in the constructor so that the attack images are loaded when the game starts
        // we add this in a different method because when we want to change what item or weapon the player
        //has, we just swap methods in order to change what weapon the player wants to use and the corresponding attack images will be loaded
        }
    }



    public BufferedImage setup(String imageName, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            //image = ImageIO.read(new File("res" + imageName + ".png")); //home pc
            image = ImageIO.read(getClass().getResourceAsStream("/res" + imageName + ".png")); //school pc
            image = uTool.scaleImage(image, width, height);
        } catch(IOException e){e.printStackTrace();}
        return image;
    }
    
    public void update() {

        if(attacking == true){attacking();return;}

        if(keyH.upPressed == true || keyH.downPressed == true || 
            keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
        
        if(keyH.upPressed == true){
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
        collisionOn = false;
        gp.cChecker.checkTile(this);
        int objIndex = gp.cChecker.checkObject(this,true);
        pickUpObject(objIndex);
        

        //CHECK NPC Collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        //CHECK EVENT
        gp.eHandler.checkEvent();

        //CHECK INTERACTIVE TILE COLLISION
        int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

        
        
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
                    worldY -= speed;
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
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
            && shotAvailableCounter == 30 && projectile.hasSufficientMana(this) == true){
            projectile.set(worldX,worldY,true,direction,this);
            projectile.subtractMana(this);
            for(int i = 0; i < gp.projectile[1].length;i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(9);
        }
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
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
            if(mana < maxMana){
                mana++;
            }
            manaRegenCounter = 0;
        }
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
            gp.gameState  = gp.gameOverState;
            gp.stopMusic();
            gp.ui.commandNum = -1;
            gp.playSE(12);
        }


    }

    public void attacking(){
        spriteNum = 1;
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <=  25){
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //Adjust player's worldX/Y for the attackArea
            switch(direction){
                case "up":
                    worldY -= attackArea.height;
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
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex,attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
            damageProjectile(projectileIndex);

            //After checking collision, restore original worldX/Y and solidArea
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damageProjectile(int i ){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    public void setDefaultPositions(){
        worldX = gp.tileSize * 50;
        worldY = gp.tileSize * 50;
        direction = "down";
    }

    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void damageInteractiveTile(int index){
        if(index != 999 && gp.iTile[gp.currentMap][index].destructible == true && gp.iTile[gp.currentMap][index].invincible == false && gp.iTile[gp.currentMap][index].isCorrectItem(this) == true){
            gp.iTile[gp.currentMap][index].playSE();
            gp.iTile[gp.currentMap][index].life--;
            gp.iTile[gp.currentMap][index].invincible = true;

            //generating particles
            generateParticle(gp.iTile[gp.currentMap][index],gp.iTile[gp.currentMap][index] );

            if(gp.iTile[gp.currentMap][index].life <= 0){
                gp.iTile[gp.currentMap][index] = gp.iTile[gp.currentMap][index].getDestroyedForm();//if the tile is destructible and we hit it, we set it to null so it disappears   
            }
        }
    }

    public void pickUpObject(int index){
        //PICK ONLY OBJECTS
        if(index != 999 && gp.obj[gp.currentMap][index].type == type_pickupOnly){
            if(gp.obj[gp.currentMap][index].type == type_pickupOnly){
                gp.obj[gp.currentMap][index].use(this);
                gp.obj[gp.currentMap][index] = null;
            }

        }    //INVENTORY ITEMS
        else{
            if(index != 999){
                    String text;
                    if(inventory.size() < maxInventorySize){
                        inventory.add(gp.obj[gp.currentMap][index]);
                        gp.playSE(1);
                        text = "Got a " + gp.obj[gp.currentMap][index].name + "!";
                }
                else{
                    text = "You cannot carry anymore items!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][index] = null;
            }
        }
    }

    public void contactMonster(int i){
        if(i!=999){
            if(invincible == false && gp.monster[gp.currentMap][i].dying == false){
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 0){
                    damage = 0;//in case monsters defense is higher than player's attack, we don't want to heal the monster by doing negative damage, so we set it to 0 instead
                }
                life-=damage;
                gp.playSE(6);
                invincible = true;
            }
        }
    }

    public void damageMonster(int i,int attack){
        if(i!=999){
            if(gp.monster[gp.currentMap][i].invincible == false){
                gp.playSE(5);
                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage = 0;//in case monsters defense is higher than player's attack, we don't want to heal the monster by doing negative damage, so we set it to 0 instead
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage("You Hit the Monster for " + damage + " Damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();//sets the direction of the monster to move away from the player
            }
            if(gp.monster[gp.currentMap][i].life <= 0 && gp.monster[gp.currentMap][i].alive == true){
                gp.monster[gp.currentMap][i].dying = true;
                gp.ui.addMessage("You Killed the "  + gp.monster[gp.currentMap][i].name + "!");
                gp.ui.addMessage("Exp + "  + gp.monster[gp.currentMap][i].exp + "!");
                exp+= gp.monster[gp.currentMap][i].exp;
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
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }


    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
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
            if(attacking == true && this.currentWeapon.type == type_sword){
                tempScreenY = screenY - gp.tileSize;//we adjust the y position of the player when attacking up because the attack image is 2 tiles high
                if(spriteNum == 1){image = attackUp1;}
                if(spriteNum == 2){image = attackUp2;}
            }
            if(attacking == true && this.currentWeapon.type == type_axe){
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
            if(attacking == true && this.currentWeapon.type == type_sword){
                tempScreenX = screenX - 23;
                if(spriteNum == 1){image = attackLeft1;}
                if(spriteNum == 2){image = attackLeft2;}
            }
            if(attacking == true && this.currentWeapon.type == type_axe){
                tempScreenX = screenX - 48;
                if(spriteNum == 1){image = attackLeft1;}
                if(spriteNum == 2){image = attackLeft2;}
            }
            break;
        case "right":
            if(attacking == false){
                if(spriteNum == 1){image = right1;}
                if(spriteNum == 2){image = right2;}
            }
            if(attacking == true && this.currentWeapon.type == type_sword){
                tempScreenX = screenX - 23;
                if(spriteNum == 1){image = attackRight1;}
                if(spriteNum == 2){image = attackRight2;}
            }
            if(attacking == true && this.currentWeapon.type == type_axe){
                tempScreenX = screenX + 10;
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
