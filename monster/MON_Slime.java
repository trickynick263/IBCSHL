package monster;
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
        speed = 1;
        maxLife = 4;
        life = maxLife;
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
    public void setAction(){
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
int i = new Random().nextInt(100)+1;
if(i > 99 && projectile.alive == false && shotAvailableCounter == 30){
    projectile.set(worldX, worldY, true, direction, this);
    gp.projectileList.add(projectile);
    shotAvailableCounter = 0;
}
    
}


public void damageReaction(){
    actionLockCounter = 0;
    direction = gp.player.direction;
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


}
