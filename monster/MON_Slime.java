package monster;
import java.awt.Color;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import objects.OBJ_GoldCoin;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;
import objects.OBJ_Rock;

public class MON_Slime extends Entity{
    GamePanel gp;
    
    public MON_Slime(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 6;
        life = maxLife;
        knockBackPower = 4;
        type = type_monster;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attack = 5;
        defense = 0;
        exp = 3;
        projectile= new OBJ_Rock(gp);

        getImage();
    }
    
    public void getImage(){
        up1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/slime_down1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/slime_down2", gp.tileSize, gp.tileSize);

    }

    public void update(){
        super.update();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;
        if(onPath == false && tileDistance < 3){
            int i = new Random().nextInt(100)+1;
            if(i > 10){
                onPath = true;
            }
        }
        if(onPath == true && tileDistance > 15){
            onPath = false;
        }
        
    }
    public void setAction(){
    if(onPath == true){
        //int goalCol = 22;
        //int goalRow = 31;

        int goalCol = (gp.player.worldX+ gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.worldY+ gp.player.solidArea.y) / gp.tileSize;
        searchPath(goalCol, goalRow);
       

        int i = new Random().nextInt(100)+1;
        if(i > 99 && projectile.alive == false && shotAvailableCounter == 30){
        projectile.set(worldX, worldY, true, direction, this);
        for(int ii  = 0;ii < gp.projectile[1].length;ii++){
            if(gp.projectile[gp.currentMap][ii] == null){
                gp.projectile[gp.currentMap][ii] = projectile;
                break;
            }
        }
        shotAvailableCounter = 0;
}
    }
    else{
            if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;//random number from 1 to 100
            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50 ){
                direction = "down";
            }
            if(i > 50 && i <=75){
                direction = "left";
            }
            if(i > 75 && i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
        else{
            actionLockCounter++;
        }
    }


    
}


public void damageReaction(){
    actionLockCounter = 0;
    onPath = true;
}

public void checkDrop(){
    int i = new Random().nextInt(100)+1;
    if(i < 50){
        
        dropItem(new OBJ_GoldCoin(gp));
    }
    if(i >= 50 && i < 75){
        dropItem(new OBJ_Heart(gp));
    }
    if(i >= 75 && i < 100){
        dropItem(new OBJ_ManaCrystal(gp));
    }
}

public boolean hasSufficientMana(Entity user){
        boolean hasMana = false;
        if(user.ammo >= useCost){
            hasMana = true;
        }
        return hasMana;
    }

    public void subtractMana(Entity user){
        user.ammo -= useCost;
    }


    public Color getParticleColor(){
        Color color = new Color(128,128,128 );
        return color;//color of the particle
    }

    public int getParticleSize(){
        int size = 6;//size of the particle which is 6 pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;//how fast the particle moves
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;//how long the particle lasts
    }


}
